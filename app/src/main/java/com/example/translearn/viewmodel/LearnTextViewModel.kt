package com.example.translearn.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.translearn.db.Repository
import com.example.translearn.db.TransTextData
import java.util.*

class LearnTextViewModel: ViewModel() {
    private val repository: Repository = Repository

    private val _translatedTexts = MutableLiveData<List<TransTextData>>()
    val translatedTexts: LiveData<List<TransTextData>>
        get() = _translatedTexts

    private val lang = "abc"
//    fun addToDo() {
//        repository.addTranslatedText(UUID.randomUUID().toString())
//        refreshTexts(lang)
//    }

    fun onResume(lang: String) {
        refreshTexts(lang)
    }


    private fun refreshTexts(lang: String) {
        _translatedTexts.value = repository.fetchLang(lang)
    }
}