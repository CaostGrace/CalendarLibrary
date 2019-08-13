package cn.logcode.calendar.date

import cn.logcode.calendar.Utils
import java.lang.IllegalStateException
import java.util.*

/**
 * Create by caostgrace on 2019-08-12 15:31
 *
 * @author caostgrace
 * @project_name: CalendarLibrary
 * @package: cn.logcode.calendar.date
 * @class_name: Month
 * @github: https://github.com/CaostGrace
 * @remark:
 */
data class Month(val days: MutableList<Day>, val monthDayCount: Int, val month: Int) {
    var year: Year? = null
        set(value) {
            field = value
            for (i in days) {
                i.month = this
                if (value != null) i.year = value
            }
        }

    lateinit var monthName: String

    companion object {
        fun toMonth(month: Int, calendar: Calendar): Month {
//            val monthDayCount = Utils.getMonthLastDay(month, calendar)
            val monthDayCount = Utils.getMonthLastDay(calendar[Calendar.YEAR], month)

            val monthName = monthToName(month)
            val days = mutableListOf<Day>()
            for (day in 1..monthDayCount) {
                days.add(Day.toDay(day, month, calendar))
            }
            val yearMonth = Month(days, monthDayCount, month)
            yearMonth.monthName = monthName
            return yearMonth
        }

        /**
         * 数字日期转字符串日期
         */
        private fun monthToName(month: Int): String = when (month) {
            1 -> "一月"
            2 -> "二月"
            3 -> "三月"
            4 -> "四月"
            5 -> "五月"
            6 -> "六月"
            7 -> "七月"
            8 -> "八月"
            9 -> "九月"
            10 -> "十月"
            11 -> "十一月"
            12 -> "十二月"
            else -> throw IllegalStateException("月份错误")
        }

    }

    val firstDay:Day = days[0]
    val lastDay:Day = days[days.lastIndex]


}