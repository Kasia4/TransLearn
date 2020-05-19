package com.example.translearn.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.translearn.MainActivity
import java.util.*

data class TransTextData (
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    var meaning: String,
    var lang: String
) {
    constructor(translatedText: TranslatedText): this(id = translatedText.id!!, text = translatedText.text!!, meaning = translatedText.meaning!!, lang = translatedText.lang!!)
}

data class UserData (
    val id: String = UUID.randomUUID().toString(),
    var score: Int = 0,
    val name: String
) {
    constructor(user: User): this(id = user.id!!, score = user.score!!, name = user.name!!)
}

object Repository {

//    private var tTextsList  = mutableListOf<TransTextData>()
    private val tTextDao: TranslatedTextDao = MainActivity.appDatabase!!.translatedTextDao()
    private val userDao: UserDao = MainActivity.appDatabase!!.userDao()

    fun addUser(id: String, name: String) {
        userDao.insert(User(UserData(id = id, name = name)))
    }

    fun addTranslatedText(id: String, text: String, meaning: String, lang: String) {
        tTextDao.insert(TranslatedText(TransTextData(id = id, text = text, meaning = meaning, lang = lang)))
    }

    fun fetchLang(lang: String): List<TransTextData> = tTextDao.findByLang(lang)!!.map{ TransTextData(it!!) }
    fun fetchText(text: String): List<TransTextData> = tTextDao.findByText(text)!!.map{ TransTextData(it!!) }

//
//    fun markAsDone(todo: TranslatedText) {
//        tTextDao.updateText(TodoEntity(todo.copy(done = true)))
//    }
}