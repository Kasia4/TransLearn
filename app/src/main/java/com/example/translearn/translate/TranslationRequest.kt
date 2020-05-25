package com.example.translearn.translate

import com.example.translearn.R
import com.google.gson.annotations.SerializedName

class TranslationRequest (
    @SerializedName("q") val text: String,
    @SerializedName("source") val sourceLang: String,
    @SerializedName("target") var targetLang: String = "en",
    val format: String = "text"
)