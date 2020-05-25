package com.example.translearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.menu_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.trans_button).setOnClickListener {
            this.findNavController().navigate(R.id.action_FirstFragment_to_TranslateFragment)
        }

        view.findViewById<Button>(R.id.quizz_button).setOnClickListener {
            this.findNavController().navigate(R.id.action_FirstFragment_to_LearnFragment)
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
