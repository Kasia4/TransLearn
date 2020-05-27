package com.example.translearn.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Score {
    @PrimaryKey
    var id: String
    //TODO change to number of learned phrases
    @ColumnInfo(name = "score")
    var score: Int
    @ColumnInfo(name = "name")
    var name: String

    constructor(scoreData: ScoreData) {
        id = scoreData.id
        score = scoreData.score
        name = scoreData.name
    }

    constructor(id: String, score: Int, name: String) {
        this.id = id
        this.score = score
        this. name = name
    }
}