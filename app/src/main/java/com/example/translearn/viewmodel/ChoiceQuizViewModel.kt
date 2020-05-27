package com.example.translearn.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.translearn.db.Repository
import com.example.translearn.db.TransTextData

class ChoiceQuizViewModel: ViewModel() {
    private val repository: Repository = Repository
    private val _question_number=MutableLiveData<Int>(1)
    val question_number: LiveData<Int>
    get() = _question_number

    private val _score = MutableLiveData<Int>(0)
    val score: LiveData<Int>
        get() = _score

    private val _translatedTexts = MutableLiveData<List<TransTextData>>()
    val translatedTexts: LiveData<List<TransTextData>>
    get() = _translatedTexts

    fun onResume(lang: String) {
        refreshTexts(lang)
    }

    fun increment_question_number() {
        _question_number.value = _question_number.value!! +1
    }

    fun reset_question_number() {
        _question_number.value = 0
    }

    fun increment_score() {
        _score.value = _score.value!! +1
    }

    fun reset_score() {
        _score.value = 0
    }

    private fun refreshTexts(lang: String) {
        _translatedTexts.value = repository.fetchThreeRandom(lang)
    }
}