package com.example.translearn.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TranslatedText::class, Score::class, Notification::class], version=1)
abstract class Database: RoomDatabase() {
    abstract fun translatedTextDao(): TranslatedTextDao
    abstract fun scoreDao(): ScoreDao
    abstract fun notificationDao(): NotificationDao
}