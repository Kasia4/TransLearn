package com.example.translearn.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.translearn.db.NotificationData
import com.example.translearn.db.Repository
import java.util.*

class NotificationVievModel : ViewModel() {
    private val repository: Repository = Repository

    private val _notification = MutableLiveData<List<NotificationData>>()
    val notification: LiveData<List<NotificationData>>
        get() = _notification

    fun addOrUpdateNotification(hour: Int, minute: Int) {
        repository.addNotification(UUID.randomUUID().toString(), hour, minute)
        _notification.value=repository.fetchNotification()
    }

    fun deleteNotification() {
        repository.deleteNotification()
        _notification.value=repository.fetchNotification()
    }

    fun onResume() {
        _notification.value=repository.fetchNotification()
    }
}