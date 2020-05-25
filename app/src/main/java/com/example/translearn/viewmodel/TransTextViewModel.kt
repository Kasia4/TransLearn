package com.example.translearn.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.translearn.db.Repository
import com.example.translearn.db.TransTextData
import java.util.*

class TransTextViewModel: ViewModel() {
    private val repository: Repository = Repository

    fun addText(text: String, meaning: String, dstLang: String) {
        repository.addTranslatedText(UUID.randomUUID().toString(), text, meaning, dstLang)
    }

}