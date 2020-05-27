package com.example.translearn.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.translearn.db.Repository
import com.example.translearn.db.ScoreData
import com.example.translearn.db.TransTextData

class StatisticsViewModel: ViewModel() {
    private val repository: Repository = Repository
    private val _scores = MutableLiveData<List<ScoreData>>()
    val score: LiveData<List<ScoreData>>
        get()= _scores
    private val _translatedTexts = MutableLiveData<List<TransTextData>>()
    val translatedTexts: LiveData<List<TransTextData>>
        get() = _translatedTexts

    private fun fetchTexts() {
        _translatedTexts.value = repository.fetchAllTexts()
    }
    fun sumScores(): Int{
        var sum = 0
        for (s in _scores.value!!) {
            sum += s.score
        }
        return sum
    }

    fun countTexts(): Int {
        if(_translatedTexts.value.isNullOrEmpty()) {
            return 0
        }else {
            return _translatedTexts.value!!.size
        }
    }

    fun onResume() {
        fetchScores()
        fetchTexts()
    }
    private fun fetchScores() {
        _scores.value=repository.fetchScores()
    }
}