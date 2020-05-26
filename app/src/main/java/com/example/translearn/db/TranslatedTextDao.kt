package com.example.translearn.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TranslatedTextDao {
    @get:Query("SELECT * FROM TranslatedText")
    val all: List<TranslatedText?>?

    @Query("SELECT * FROM TranslatedText WHERE lang = :lang")
    fun findByLang(lang: String?): List<TranslatedText?>?

    @Query("SELECT * FROM TranslatedText WHERE lang = :lang ORDER BY RANDOM() LIMIT 3")
    fun getThreeRandomByLang(lang: String?): List<TranslatedText?>?

    @Query("SELECT * FROM TranslatedText WHERE text = :text AND lang = :lang")
    fun findByText(text: String?, lang: String?): List<TranslatedText?>?

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(vararg translatedTexts: TranslatedText?)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(translatedText: TranslatedText?)

    @Delete
    fun delete(translatedText: TranslatedText?)

    @Update
    fun updateText(vararg translatedText: TranslatedText)
}