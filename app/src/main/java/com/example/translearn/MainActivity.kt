package com.example.translearn

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.room.Room
import com.example.translearn.db.Database
import com.example.translearn.translate.Language

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        var appDatabase: Database? = null
        var notificationManager: NotificationManager? = null
        var languages: Array<Language> = arrayOf(
            Language("English", "en"), Language("Polish", "pl"),
            Language("Czech", "cs"), Language("Danish", "da"),
            Language("Dutch", "nl"), Language("Estonian", "et"),
            Language("Finnish", "fi"), Language("French", "fr"),
            Language("German", "de"), Language("Greek", "el"),
            Language("Irish", "ga"), Language("Italian", "it"),
            Language("Latvian", "lv"), Language("Lithuanian", "lt"),
            Language("Norwegian", "no"), Language("Portuguese", "pt"),
            Language("Romanian", "ro"), Language("Russian", "ru"),
            Language("Serbian", "sr"), Language("Slovak", "sk"),
            Language("Slovenian", "sl"), Language("Spanish", "es"),
            Language("Swedish", "sv"), Language("Turkish", "tr"),
            Language("Ukrainian", "uk"), Language("Bulgarian", "bg")
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        appDatabase = Room.databaseBuilder(
            applicationContext,
            Database::class.java, "wpam-db"
        ).allowMainThreadQueries().build()
        createNotificationChannel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = "For TransLearn notifications purpose"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val CHANNEL_ID = "channel_id"
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager!!.createNotificationChannel(channel)
        }
    }
}
