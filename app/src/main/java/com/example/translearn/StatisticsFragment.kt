package com.example.translearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.translearn.viewmodel.ChoiceQuizViewModel
import com.example.translearn.viewmodel.StatisticsViewModel
import kotlinx.android.synthetic.main.statistics_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class StatisticsFragment : Fragment() {
    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.statistics_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StatisticsViewModel::class.java)
        viewModel.translatedTexts.observe(viewLifecycleOwner, Observer {
            val addedText = "Translated words: ${viewModel.countTexts()}"
            added_view.text=addedText
        })
        viewModel.score.observe(viewLifecycleOwner, Observer {
            val scoreText = "Already guessed in quiz: ${viewModel.sumScores()}"
            scored_view.text=scoreText
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

}
