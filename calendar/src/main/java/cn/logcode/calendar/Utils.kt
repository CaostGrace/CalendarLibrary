package cn.logcode.calendar

import cn.logcode.calendar.date.ChoiceDate
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

/**
 * Create by caostgrace on 2019-08-12 14:29
 *
 * @author caostgrace
 * @project_name: CalendarLibrary
 * @package: cn.logcode.calendar
 * @class_name: Utils
 * @github: https://github.com/CaostGrace
 * @remark:
 */

class Utils {
    companion object {

        public
        const val datePattern = "yyyy-MM-dd HH:mm:ss"

        /**
         * 获取日期
         */
        fun getCalendar(): Calendar {
            return Calendar.getInstance()
        }

        /**
         * 获取日期
         */
        fun getCalendar(date: Date): Calendar {
            var calendar = Calendar.getInstance()
            calendar.time = date
            return calendar
        }
        fun getCalendar(choice: ChoiceDate): Calendar {
            var calendar = Calendar.getInstance()
            calendar[Calendar.YEAR] = choice.year.year
            calendar[Calendar.MONTH] = choice.month.month-1
            calendar[Calendar.DAY_OF_MONTH] = choice.day.day

            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            return calendar
        }


        fun getTodayDate(): Date {
            return Date()
        }

        @Throws(NullPointerException::class)
        fun stringToDate(dateStr: String): Date {
            return stringToDate(dateStr, datePattern)
        }

        @Throws(NullPointerException::class)
        fun stringToDate(dateStr: String, pattern: String): Date {
            val sdl = SimpleDateFormat(pattern)
            var date = sdl.parse(dateStr)
            if (date != null) {
                return date
            }
            throw NullPointerException("格式化异常")
        }

        fun dateToString(choice: ChoiceDate): String {
            return dateToString(choice, datePattern)
        }
        fun dateToString(choice: ChoiceDate, pattern: String): String {
            return dateToString(getCalendar(choice).time, pattern)
        }

        fun dateToString(): String {
            return dateToString(getTodayDate(), datePattern)
        }

        fun dateToString(date: Date): String {
            return dateToString(date, datePattern)
        }

        fun dateToString(date: Date, pattern: String): String {
            val sdl = SimpleDateFormat(pattern)
            return sdl.format(date)
        }


        fun getTodayYear(): Int {
            return getCalendar()[Calendar.YEAR]
        }

        fun getDateYear(date: Date): Int {
            return getCalendar(date)[Calendar.YEAR]
        }

        fun getTodayMonth(): Int {
            return getCalendar()[Calendar.MONTH]
        }

        fun getTodayMonth(date: Date): Int {
            return getCalendar(date)[Calendar.MONTH]
        }

        /**
         * 判断是否是闰年
         */
        fun isLeapYear(calendar: Calendar): Boolean {
            var time = calendar.time
            //设置为那一年的3月1日
            calendar.set(calendar[Calendar.YEAR], 2, 1)
            //将日向前减去1
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            //判断是否是2月和29天
            var isLeapYear = calendar[Calendar.MONTH] == 1 && calendar.get(Calendar.DAY_OF_MONTH) == 29
            calendar.time = time
            return isLeapYear
        }

        /**
         * 获取月份最大天数
         * 返回 28、29、30、31
         */
        fun getMonthLastDay(month: Int, calendar: Calendar): Int {
            var time = calendar.time
            calendar.set(Calendar.MONTH,(month-1))
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            var day = calendar[Calendar.DAY_OF_MONTH]
            calendar.time = time
            return day
        }
        fun getMonthLastDay(year: Int,month: Int): Int {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,(month-1))
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
//            var day = calendar[Calendar.DAY_OF_MONTH]
            return calendar[Calendar.DAY_OF_MONTH]
        }

        /**
         * 返回一个月的一天是周几
         */
        fun getWeekOfMonth(year: Int,month: Int,day:Int): Int {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,(month-1))
            calendar.set(Calendar.DAY_OF_MONTH,day)
            var week =  calendar[Calendar.DAY_OF_WEEK]
            week -= 1
            if(week == 0){
                week = 7
            }
            return week
        }
    }
}

