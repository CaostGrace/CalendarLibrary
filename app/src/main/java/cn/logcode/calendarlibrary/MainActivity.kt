package cn.logcode.calendarlibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cn.logcode.calendar.Utils
import cn.logcode.calendar.date.WeekEnum
import cn.logcode.calendar.view.CalendarView
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var calendarView: CalendarView = findViewById(R.id.calendarPicker)

        calendarView.removeWeek(WeekEnum.Monday)
        calendarView.removeWeek(WeekEnum.Tuesday)
        calendarView.removeWeek(WeekEnum.Thursday)

        var calendar = Utils.getCalendar()

        calendar[Calendar.MONTH] = 10
        calendar[Calendar.DAY_OF_MONTH] = 25
        calendarView.setStartDate(calendar.time)


        calendar[Calendar.MONTH] = 11
        calendar[Calendar.DAY_OF_MONTH] = 26
        calendarView.setEndDate(calendar.time)



        calendarView.choseDateListener = { year, month, day ->

            Log.e("year", year.year.toString())
            Log.e("month", month.month.toString())
            Log.e("day", day.day.toString())

            Log.e("week", day.week.weekName)
            Log.e("monthDayCount", month.monthDayCount.toString())
            Log.e("monthName", month.monthName)



        }

    }
}
