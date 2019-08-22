package cn.logcode.calendarlibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import cn.logcode.calendar.date.WeekEnum
import cn.logcode.calendar.view.CalendarViewDialog
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var tmp = CalendarViewDialog
                .instance!!
                .init(this)
                .addWeek(WeekEnum.Monday)
                .setSelectDay(Date().time)
        (findViewById<TextView>(R.id.calendarPicker)).setOnClickListener {

            tmp.setDateChoiceListener { year, month, day ->
                Log.e("year===>", year.year.toString())
                Log.e("month===>", month.month.toString())
                Log.e("day===>", day.day.toString())
            }
                    .show()
        }
    }
}
