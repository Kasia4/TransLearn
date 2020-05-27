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
import com.example.translearn.notification.AlarmReceiver
import kotlinx.android.synthetic.main.notification_fragment.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment() {
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.notification_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        time_picker.setIs24HourView(true)
        set_notification_button.setOnClickListener {
            val hour: Int = time_picker.hour
            val minutes: Int = time_picker.minute
            val details = "Notification set on: ${hour}:${minutes}"
            notification_details_text.text=details
            time_picker
            setAlarm(hour, minutes)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun setAlarm(hour: Int, minutes: Int) {
        alarmMgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 1, intent, 0)
        }
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
       // alarmMgr?.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 1000 * 60 * 10, alarmIntent)
        alarmMgr?.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent )
    }

}
