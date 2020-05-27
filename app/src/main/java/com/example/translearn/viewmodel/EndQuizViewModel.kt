package com.example.translearn.viewmodel

import androidx.lifecycle.ViewModel
import com.example.translearn.db.Repository
import java.util.*

class EndQuizViewModel: ViewModel() {
    private val repository: Repository = Repository

    fun addScore(score :Int) {
        repository.addScore(UUID.randomUUID().toString(), "default", score)
    }

}