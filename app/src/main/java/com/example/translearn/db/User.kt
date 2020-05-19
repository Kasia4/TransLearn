package com.example.translearn.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User {
    @PrimaryKey
    var id: String
    //TODO change to number of learned phrases
    @ColumnInfo(name = "score")
    var score: Int
    @ColumnInfo(name = "name")
    var name: String

    constructor(userData: UserData) {
        id = userData.id
        score = userData.score
        name = userData.name
    }

    constructor(id: String, score: Int, name: String) {
        this.id = id
        this.score = score
        this. name = name
    }
}