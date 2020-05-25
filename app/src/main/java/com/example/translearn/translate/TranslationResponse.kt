package com.example.translearn.translate

class TranslationResponse (
    val translations: ArrayList<SingleTranslationResponse>
)
class TranslationResponseContainer(
    val data: TranslationResponse
)

class SingleTranslationResponse(
    val translatedText: String
)