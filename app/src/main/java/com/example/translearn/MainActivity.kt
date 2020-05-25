package com.example.translearn

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

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
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
}
