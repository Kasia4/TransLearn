package com.example.translearn

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.translearn.notification.AlarmReceiver
import com.example.translearn.viewmodel.NotificationVievModel
import com.example.translearn.viewmodel.TransTextViewModel
import kotlinx.android.synthetic.main.notification_fragment.*
import java.util.*
import kotlin.random.Random as KotlinRandomRandom

/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {
    private lateinit var viewModel: NotificationVievModel
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private var intentNr: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.notification_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotificationVievModel::class.java)
        viewModel.notification.observe(viewLifecycleOwner, Observer {
            val details: String
            if (it.isNullOrEmpty()) {
                details = "Notification not set"
            } else if (it[0].minute < 10) {
                details = "Notification set on: ${it[0].hour}:0${it[0].minute}"
            } else {
                details = "Notification set on: ${it[0].hour}:${it[0].minute}"
            }
            notification_details_text.text=details
        })
        time_picker.setIs24HourView(true)
        set_notification_button.setOnClickListener {
            val hour: Int = time_picker.hour
            val minutes: Int = time_picker.minute
            viewModel.addOrUpdateNotification(hour, minutes)
            setAlarm(hour, minutes)
        }
        cancel_notification_button.setOnClickListener {
            cancelAlarm()
            viewModel.deleteNotification()
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun setAlarm(hour: Int, minutes: Int) {
        alarmMgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, intentNr, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        alarmMgr?.cancel(alarmIntent)
        alarmIntent.cancel()
        intentNr = KotlinRandomRandom(System.nanoTime()).nextInt(10000)
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, intentNr, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
        val dayInMillis :Long = 1000 * 60 * 60 * 24
        alarmMgr?.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, dayInMillis, alarmIntent)
    }

    private fun cancelAlarm() {
        alarmMgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, intentNr, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        alarmMgr?.cancel(alarmIntent)
        alarmIntent.cancel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}
