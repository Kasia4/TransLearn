package com.example.translearn.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ScoreDao {
    @get:Query("SELECT * FROM Score")
    val all: List<Score?>?

    @Query("SELECT * FROM Score WHERE name = :name")
    fun find(name: String?): List<Score?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(score: Score?)

    @Update
    fun update(score: Score?)
}