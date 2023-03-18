package com.anec.timecalc.timepickers

import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.anec.timecalc.R
import com.anec.timecalc.TimeCalculator

class FirstTimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val textViewResult = activity?.findViewById(R.id.textViewResult) as TextView
        if (textViewResult.text.toString() != "") {
            textViewResult.text = ""
        }

        val minuteString = TimeCalculator.addZeroToMinutesIfMinutesLessThan10(minute)
        val result = "$hourOfDay:$minuteString"
        val textView: TextView = activity?.findViewById(R.id.textView1) as TextView
        textView.text = result
    }
}
