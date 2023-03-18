package com.anec.timecalc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anec.timecalc.settings.SettingsActivity
import com.anec.timecalc.timepickers.FirstTimePickerFragment
import com.anec.timecalc.timepickers.SecondTimePickerFragment

class MainActivity : AppCompatActivity() {
    private val tm = TimeCalculator()

    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context: Context = MainActivity.applicationContext()

        val textView1: TextView = findViewById(R.id.textView1)
        val textView2: TextView = findViewById(R.id.textView2)
        val textViewResult: TextView = findViewById(R.id.textViewResult)

        val buttonCalculate: Button = findViewById(R.id.buttonCalculate)
        buttonCalculate.setOnClickListener {
            val timeNotSelectedString =
                applicationContext.resources.getString(R.string.time_not_selected)
            if (textView1.text == timeNotSelectedString ||
                textView2.text == timeNotSelectedString
            ) {
                Toast.makeText(applicationContext, timeNotSelectedString, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val firstTime: String = textView1.text.toString()
            val secondTime: String = textView2.text.toString()

            val result: IntArray = tm.getTimeInterval(firstTime, secondTime)
            val hours: Int = result[0]
            val minutes: Int = result[1]

            val hourWord: String = tm.generateWord(hours, tm.getHourWords())
            val minuteWord: String = tm.generateWord(minutes, tm.getMinuteWords())

            var resultText = "${applicationContext.resources.getString(R.string.result)}: "
            resultText += if (hours != 0 && minutes != 0) {
                "$hours $hourWord, $minutes $minuteWord"
            } else {
                (if (hours == 0) "" else ("$hours $hourWord").toString() + " ") +
                        if (minutes == 0 && hours != 0) "" else "$minutes $minuteWord"
            }
            textViewResult.text = resultText
        }
    }

    fun showFirstTimePickerDialog(view: View) {
        FirstTimePickerFragment().show(supportFragmentManager, "timePicker")
    }

    fun showSecondTimePickerDialog(view: View) {
        SecondTimePickerFragment().show(supportFragmentManager, "timePicker")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
