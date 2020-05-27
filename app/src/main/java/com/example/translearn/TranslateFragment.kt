package com.example.translearn

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProviders
import com.example.translearn.translate.TranslationRequest
import com.example.translearn.translate.TranslationResponseContainer
import com.example.translearn.viewmodel.TransTextViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TranslateFragment : Fragment() {

    private lateinit var viewModel: TransTextViewModel
    val srcLang: String = "pl"
    var dstLang: String? = null
    private val client = OkHttpClient()
    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.translate_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TransTextViewModel::class.java)

        val spinner1 = view.findViewById<Spinner>(R.id.languages1)
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listOf("Polish"))
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner1.adapter = adapter
            }
        spinner1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }
        }

        val langs = MainActivity.languages.map{ language -> language.name}
        val spinner2 = view.findViewById<Spinner>(R.id.languages2)
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, langs)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner2.adapter = adapter
            }
        spinner2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                dstLang = MainActivity.languages[position].code
            }
        }

        view.findViewById<FloatingActionButton>(R.id.trans_action_button).setOnClickListener {
            val input: String? = view.findViewById<AppCompatEditText>(R.id.toTranslate_appCompatEditText).text?.toString()
            if (input.isNullOrEmpty()){
                Toast.makeText(context, "Please provide input text!", Toast.LENGTH_SHORT).show()
            }
            else {
                when {
                    dstLang.isNullOrEmpty() -> {
                        Toast.makeText(context, "Please select destination language", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        translate(input, dstLang!!) { result ->
                            view.findViewById<AppCompatTextView>(R.id.translated_appCompatText).text = result
                            if(!result.isNullOrEmpty()) {
                                viewModel.addText(input, result, dstLang!!)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun translate(textToTrans: String, dstLang: String, completion: (String?) -> Unit) {
        val url = "https://translation.googleapis.com/language/translate/v2?key="+getString(R.string.key)

        val translateRequest = TranslationRequest(textToTrans, srcLang, dstLang)
        val body = RequestBody.create(
            MediaType.parse("application/json"),
            gson.toJson(translateRequest)
        )

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("MY", "failure", e);
                completion("fail")
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body()?.string()
                val transText: String
                val translationResponse = gson.fromJson(result, TranslationResponseContainer::class.java)
                transText = try {
                    translationResponse.data.translations.first().translatedText
                } catch (npe: NullPointerException) {
                    "failed to translate"
                }
                completion(transText)
            }
        })
    }

}


