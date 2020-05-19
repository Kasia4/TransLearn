package com.example.translearn.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TranslatedText {
    @PrimaryKey
    var id: String
    @ColumnInfo(name = "text")
    var text: String
    @ColumnInfo(name = "meaning")
    var meaning: String
    @ColumnInfo(name = "lang")
    var lang: String

    constructor(transTextData: TransTextData) {
        id = transTextData.id
        text = transTextData.text
        meaning = transTextData.meaning
        lang = transTextData.lang
    }

    constructor(id: String, text: String, meaning: String, lang: String) {
        this.id = id
        this.text = text
        this.meaning = meaning
        this.lang = lang
    }
}