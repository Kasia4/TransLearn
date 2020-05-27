package com.example.translearn.viewmodel

import androidx.lifecycle.ViewModel
import com.example.translearn.db.Repository
import java.util.*

class TransTextViewModel: ViewModel() {
    private val repository: Repository = Repository

    fun addText(text: String, meaning: String, dstLang: String) {
        repository.addTranslatedText(UUID.randomUUID().toString(), text, meaning, dstLang)
    }

}