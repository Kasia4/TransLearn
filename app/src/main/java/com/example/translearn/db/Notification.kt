package com.example.translearn.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Notification {
    @PrimaryKey
    var id: String
    //TODO change to number of learned phrases
    @ColumnInfo(name = "hour")
    var hour: Int
    @ColumnInfo(name = "minute")
    var minute: Int

    constructor(notificationData: NotificationData) {
        id = notificationData.id
        hour = notificationData.hour
        minute = notificationData.minute
    }

    constructor(id: String, hour: Int, minute:Int) {
        this.id = id
        this.hour = hour
        this.minute = minute
    }
}