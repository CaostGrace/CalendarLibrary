package cn.logcode.calendar

import cn.logcode.calendar.date.*
import java.util.*

/**
 * Create by caostgrace on 2019-08-13 14:12
 *
 * @author caostgrace
 * @project_name: CalendarLibrary
 * @package: cn.logcode.calendar
 * @class_name: CalendarInstance
 * @github: https://github.com/CaostGrace
 * @remark:
 */
class CalendarInstance private constructor() {
    private
    lateinit var calendar: Calendar

    /**
     * 当前选择的日期
     */
    private
    lateinit var choiceDate: ChoiceDate

    /**
     * 日期改变的监听
     */
    lateinit var dateChangeListener: (year: Year, month: Month, day: Day) -> Unit

    /**
     * 日期显示时间段
     */
    private lateinit var startDate: ChoiceDate
    private lateinit var endDate: ChoiceDate

    companion object {
        fun init(calendar: Calendar): CalendarInstance {
            val calendarInstance = CalendarInstance()
            calendarInstance.calendar = calendar
            calendarInstance.choiceDate = ChoiceDate.instance(calendar)

            calendarInstance.startDate = ChoiceDate(calendarInstance.choiceDate.year, calendarInstance.choiceDate.year.firstMonth, calendarInstance.choiceDate.year.firstMonth.firstDay)
            calendarInstance.endDate = ChoiceDate(calendarInstance.choiceDate.year, calendarInstance.choiceDate.year.lastMonth, calendarInstance.choiceDate.year.lastMonth.lastDay)

            return calendarInstance
        }

        fun init(date: Date): CalendarInstance {
            var calendar = Calendar.getInstance()
            calendar.time = date
            return init(calendar)
        }

        fun init(date: String): CalendarInstance {
            return init(date, Utils.datePattern)
        }

        fun init(date: String, pattern: String): CalendarInstance {
            return init(Utils.stringToDate(date, pattern))
        }
    }

    @Throws(IllegalStateException::class)
    fun setDateInterval(start: ChoiceDate, end: ChoiceDate): CalendarInstance {

        when {
            start.year.year > end.year.year -> throw IllegalStateException("开始时间不能大于结束时间")
            start.year.year == end.year.year && start.month.month > end.month.month -> throw IllegalStateException("开始时间不能大于结束时间")
            start.year.year == end.year.year && start.month.month == end.month.month && start.day.day > end.day.day -> throw IllegalStateException("开始时间不能大于结束时间")
        }

        startDate = start
        endDate = end
        return this
    }

    fun setDateInterval(start: Calendar, end: Calendar): CalendarInstance {
        setDateInterval(ChoiceDate.instance(start), ChoiceDate.instance(end))
        return this
    }

    fun setDateInterval(start: Date, end: Date): CalendarInstance {
        var startCalendar = Calendar.getInstance()
        startCalendar.time = start
        var endCalendar = Calendar.getInstance()
        endCalendar.time = end
        setDateInterval(startCalendar, endCalendar)
        return this
    }


    /**
     * 未完成，判断日期是否在选择区间内
     */
    fun isCanChoice(choice: ChoiceDate): Boolean {
        if (choice.year.year > startDate.year.year && choice.year.year < endDate.year.year) {
            return true
        }

        if(choice.year.year == startDate.year.year && choice.year.year < endDate.year.year && choice.month.month > startDate.month.month){
            return true
        }



        return false
    }


}