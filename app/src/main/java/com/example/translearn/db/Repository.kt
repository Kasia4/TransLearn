package com.example.translearn.db

import androidx.lifecycle.map
import com.example.translearn.MainActivity
import java.util.*

data class TransTextData (
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    var meaning: String,
    var lang: String
) {
    constructor(translatedText: TranslatedText): this(id = translatedText.id, text = translatedText.text, meaning = translatedText.meaning, lang = translatedText.lang)
}

data class ScoreData (
    val id: String = UUID.randomUUID().toString(),
    var score: Int,
    val name: String
) {
    constructor(score: Score): this(id = score.id, score = score.score, name = score.name)
}

data class NotificationData (
    val id: String = UUID.randomUUID().toString(),
    var hour: Int = 0,
    var minute: Int = 0
) {
    constructor(notification: Notification): this(id = notification.id, hour = notification.hour, minute = notification.minute)
}

object Repository {

//    private var tTextsList  = mutableListOf<TransTextData>()
    private val tTextDao: TranslatedTextDao = MainActivity.appDatabase!!.translatedTextDao()
    private val scoreDao: ScoreDao = MainActivity.appDatabase!!.scoreDao()
    private val notificationDao: NotificationDao = MainActivity.appDatabase!!.notificationDao()

    fun addScore(id: String, name: String, score: Int) {
        scoreDao.insert(Score(ScoreData(id = id, score = score, name = name)))
    }

    fun fetchScores(): List<ScoreData> = scoreDao.all!!.map { ScoreData(it!!) }

    fun addTranslatedText(id: String, text: String, meaning: String, lang: String) {
        val textFromDB = fetchText(text, lang)
        if (textFromDB.isEmpty()) {
            tTextDao.insert(TranslatedText(TransTextData(id = id, text = text, meaning = meaning, lang = lang)))
        }
    }

    fun deleteText(text: String, lang: String) {
        val textFromDB = fetchText(text, lang)
        if (textFromDB.isNotEmpty()) {
            tTextDao.delete(TranslatedText(id = textFromDB[0].id, text = text, meaning = textFromDB[0].meaning, lang = lang))
        }
    }

    fun addNotification(id: String, hour: Int, minute: Int) {
        val notificationFromDB = fetchNotification()
        when {
            notificationFromDB.isEmpty() -> {
                notificationDao.insert(Notification(id, hour, minute))
            }
            else -> {
                notificationDao.update(Notification(notificationFromDB[0].id, hour, minute))
            }
        }
    }

    fun deleteNotification() {
        val notificationFromDB = fetchNotification()
        if (notificationFromDB.isNotEmpty()) {
            notificationDao.delete(Notification(id = notificationFromDB[0].id, hour = notificationFromDB[0].hour, minute = notificationFromDB[0].minute))
        }
    }

    fun fetchAllTexts(): List<TransTextData> = tTextDao.all!!.map { TransTextData(it!!) }

    fun fetchNotification(): List<NotificationData> = notificationDao.all!!.map { NotificationData(it!!) }
    fun fetchLang(lang: String): List<TransTextData> = tTextDao.findByLang(lang)!!.map{ TransTextData(it!!) }
    fun fetchThreeRandom(lang: String): List<TransTextData> = tTextDao.getThreeRandomByLang(lang)!!.map{ TransTextData(it!!) }
    private fun fetchText(text: String, lang: String): List<TransTextData> = tTextDao.findByText(text, lang)!!.map{ TransTextData(it!!) }
}