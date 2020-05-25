package com.example.translearn.learn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.translearn.R
import com.example.translearn.db.TransTextData

class LearnRecyclerViewAdapter(var texts: List<TransTextData>) : RecyclerView.Adapter<LearnRecyclerViewAdapter.ViewHolder>(){
    class ViewHolder(cardView: View) : RecyclerView.ViewHolder(cardView) {
        val notTransText: AppCompatTextView = itemView.findViewById(R.id.not_trans_text)
        val transText: AppCompatTextView = itemView.findViewById(R.id.trans_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val learnCardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.flashcard, parent, false)
        return ViewHolder(learnCardView)
    }

    override fun getItemCount(): Int {
        return texts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        texts[position].apply {
            holder.transText.text = text
            holder.notTransText.text = meaning
        }
    }
}