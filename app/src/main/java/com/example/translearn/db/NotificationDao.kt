package com.example.translearn.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotificationDao {

    @get:Query("SELECT * FROM Notification")
    val all: List<Notification?>?

//    @Query("SELECT * FROM Notification")
//    fun find(): List<Notification?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notification: Notification?)

    @Update
    fun update(notification: Notification?)

    @Delete
    fun delete(notification: Notification?)
}