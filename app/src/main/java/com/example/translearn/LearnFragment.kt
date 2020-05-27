package com.example.translearn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.translearn.learn.LearnRecyclerViewAdapter
import com.example.translearn.translate.Language
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
    private var lang = Language("English", "en")
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
                lang = MainActivity.languages[position]
                viewModel.onResume(lang.code)
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

        val itemTouchHelperCall = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val tt = recycleViewAdapter.getTextAt(viewHolder.adapterPosition)
                viewModel.deleteText(text = tt.text, lang = tt.lang)
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCall)
        itemTouchHelper.attachToRecyclerView(to_learn_list)

        take_a_quiz_button.setOnClickListener{
            val action = LearnFragmentDirections.actionLearnFragmentToChoiceQuizFragment(this.lang.code, this.lang.name)
            this.findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(lang.code)
    }
}
