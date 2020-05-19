package com.example.translearn.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @get:Query("SELECT * FROM User")
    val all: LiveData<List<User?>>?

    @Query("SELECT * FROM User WHERE name = :name")
    fun find(name: String?): LiveData<List<User?>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User?)

    @Update
    fun update(user: User?)
}