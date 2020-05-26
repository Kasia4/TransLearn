package com.example.translearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.translearn.learn.LearnRecyclerViewAdapter
import com.example.translearn.viewmodel.LearnTextViewModel
import kotlinx.android.synthetic.main.learn_fragment.*

/**
 * A simple [Fragment] subclass.
 * Use the [LearnFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LearnFragment : Fragment() {
    private lateinit var viewModel: LearnTextViewModel
    private val recycleViewAdapter: LearnRecyclerViewAdapter = LearnRecyclerViewAdapter(listOf())
    private var lang: String = "en"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.learn_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val langs = MainActivity.languages.map{ language -> language.name}
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, langs)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                choose_lang_spinner.adapter = adapter
            }
        choose_lang_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                lang = MainActivity.languages[position].code
                viewModel.onResume(lang)
            }
        }
        // TODO use viewmodelprovider
        viewModel = ViewModelProviders.of(this).get(LearnTextViewModel::class.java)
        viewModel.translatedTexts.observe(viewLifecycleOwner, Observer {
            recycleViewAdapter.apply {
                texts = it
                notifyDataSetChanged()
            }
        })
        to_learn_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recycleViewAdapter
        }

        take_a_quiz_button.setOnClickListener{
            Toast.makeText(context, "will be implemented", Toast.LENGTH_SHORT).show()
            this.findNavController().navigate(R.id.action_LearnFragment_to_ChoiceQuizFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(lang)
    }
}
