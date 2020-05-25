package com.example.translearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.learn_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO use viewmodelprovider
        viewModel = ViewModelProviders.of(this).get(LearnTextViewModel::class.java)
        viewModel.translatedTexts.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "uuu", Toast.LENGTH_SHORT).show()
            recycleViewAdapter.apply {
                texts = it
                notifyDataSetChanged()
            }
        })
//        add_button.setOnClickListener { viewModel.addToDo() }
//
        to_learn_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recycleViewAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<Button>(R.id.button_second).setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.onResume("pl")
    }
}
