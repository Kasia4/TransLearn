package com.example.translearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.translearn.db.TransTextData
import com.example.translearn.viewmodel.ChoiceQuizViewModel
import kotlinx.android.synthetic.main.choice_quiz_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class ChoiceQuizFragment : Fragment() {
    private val args: ChoiceQuizFragmentArgs by navArgs()
    private lateinit var viewModel: ChoiceQuizViewModel
    private val questionCount = 5
    private var correctAnswer: String? = null
//    val langCode = args.learnLangCode
//    val langName = args.learnLangName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.choice_quiz_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quiz_language.text = args.learnLangName
        viewModel = ViewModelProviders.of(this).get(ChoiceQuizViewModel::class.java)
        viewModel.translatedTexts.observe(viewLifecycleOwner, Observer {
            generateQuestion(it)
        })
        val questNr = "Question ${viewModel.question_number.value}/5"
        question_number_text_view.text= questNr
        viewModel.question_number.observe(viewLifecycleOwner, Observer {
            val questionNr = "Question ${it}/5"
            question_number_text_view.text= questionNr
        })
        val scoreStr = "Score: ${viewModel.score}"
        score_text_view.text=scoreStr
        viewModel.score.observe(viewLifecycleOwner, Observer {
            val scoreStr = "Score: $it"
            score_text_view.text=scoreStr
        })
        if (viewModel.question_number.value!! >= questionCount) {
            viewModel.reset_question_number()
            viewModel.reset_score()
        }
        confirm_button.setOnClickListener {
            val id = answers_button_group.checkedRadioButtonId
            val radioButton =view.findViewById<RadioButton>(id)
            val chosenAnswer = radioButton?.text
            if (chosenAnswer == correctAnswer) {
                Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show()
                viewModel.increment_score()
            } else {
                Toast.makeText(context, "Wrong :(", Toast.LENGTH_SHORT).show()
            }
            if(viewModel.question_number.value!! >= questionCount) {
                val action = ChoiceQuizFragmentDirections.actionChoiceQuizFragmentToEndQuizFragment(viewModel.score.value!!)
                this.findNavController().navigate(action)
            }
            answers_button_group.clearCheck()
            viewModel.increment_question_number()
            viewModel.onResume(args.learnLangCode)
        }
    }

    private fun generateQuestion(it: List<TransTextData>) {
        when {
            it.size < 3 -> {
                Toast.makeText(context, "Not enough transleted test, 3 required", Toast.LENGTH_LONG).show()
            }
            else -> {
                val askedText = it[0].text
                correctAnswer = it[0].meaning
                question_text.text=askedText
                val shuffled = it.shuffled()
                val answer1 = shuffled[0].meaning
                val answer2 = shuffled[1].meaning
                val answer3 = shuffled[2].meaning
                answer1_radio_button.text=answer1
                answer2_radio_button.text=answer2
                answer3_radio_button.text=answer3
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(args.learnLangCode)
    }

}
