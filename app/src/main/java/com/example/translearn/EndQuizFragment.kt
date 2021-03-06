package com.example.translearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.translearn.viewmodel.EndQuizViewModel
import com.example.translearn.viewmodel.TransTextViewModel
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.end_quiz_fragment.*
import kotlin.concurrent.fixedRateTimer

/**
 * A simple [Fragment] subclass.
 */
class EndQuizFragment : Fragment() {
    private val args: EndQuizFragmentArgs by navArgs()
    private lateinit var viewModel: EndQuizViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.end_quiz_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EndQuizViewModel::class.java)
        viewModel.addScore(args.score)
        val scoreStr = "Score: ${args.score}"
        end_score_view.text=scoreStr
        back_button.setOnClickListener {
            findNavController().navigate(R.id.action_EndQuizFragment_to_FirstFragment)
        }
    }

}
