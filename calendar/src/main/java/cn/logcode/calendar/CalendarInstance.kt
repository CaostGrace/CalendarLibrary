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

    lateinit var calendar: Calendar

    //时间比较严格模式
    var strictMode = false

    /**
     * 当前日期
     */

    lateinit var currentChoiceDate: ChoiceDate

    /**
     * 日期改变的监听
     */
    lateinit var dateChangeListener: (date: ChoiceDate) -> Unit

    /**
     * 日期显示时间段
     */
    lateinit var startDate: ChoiceDate
    lateinit var endDate: ChoiceDate

    companion object {
        fun init(calendar: Calendar): CalendarInstance {
            val calendarInstance = CalendarInstance()
            calendarInstance.calendar = calendar
            calendarInstance.currentChoiceDate = ChoiceDate.instance(calendar)
            calendarInstance.startDate = ChoiceDate(calendarInstance.currentChoiceDate.year, calendarInstance.currentChoiceDate.year.firstMonth, calendarInstance.currentChoiceDate.year.firstMonth.firstDay)
            calendarInstance.endDate = ChoiceDate(calendarInstance.currentChoiceDate.year, calendarInstance.currentChoiceDate.year.lastMonth, calendarInstance.currentChoiceDate.year.lastMonth.lastDay)

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

        if (!isCanChoice(currentChoiceDate)) {
            currentChoiceDate = startDate
        }
        return this
    }

    /**
     * 设置可选的开始结束时间
     */
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

    fun setEndDateInterval(end: ChoiceDate): CalendarInstance{
        return setDateInterval(startDate,end)
    }
    fun setStartDateInterval(start: ChoiceDate): CalendarInstance{
        return setDateInterval(start,endDate)
    }

    /**
     * 未完成，判断日期是否在选择区间内
     */
    fun isCanChoice(choice: ChoiceDate): Boolean {
        var startTime: Long = Utils.getCalendar(startDate).time.time
        var choiceTime: Long = Utils.getCalendar(choice).time.time
        var endTime: Long = Utils.getCalendar(endDate).time.time

        if (strictMode && (choiceTime in (startTime + 1)..(endTime - 1))) {
            return true
        } else if (choiceTime in startTime..endTime) {
            return true
        }
        return false
    }


    /**
     * 下一年
     */
    fun nextYear() {
        if (isCanNext(true)) {
            currentChoiceDate = currentChoiceDate.nextYear()
            dateChangeListener(currentChoiceDate)
        }
    }

    /**
     * 下一月
     */
    fun nextMonth() {
        if (isCanNext()) {
            currentChoiceDate = currentChoiceDate.nextMonth()
            dateChangeListener(currentChoiceDate)
        }
    }

    /**
     * 上一年
     */
    fun lastYear() {
        if (isCanLast(true)) {
            currentChoiceDate = currentChoiceDate.lastYear()
            dateChangeListener(currentChoiceDate)
        }
    }

    /**
     * 上一月
     */
    fun lastMonth() {
        if (isCanLast()) {
            currentChoiceDate = currentChoiceDate.lastMonth()
            dateChangeListener(currentChoiceDate)
        }
    }

    /**
     * 能否往上切换
     */
    fun isCanLast(isYear: Boolean = false): Boolean {
        return when{
            isYear -> startDate.year.year < currentChoiceDate.year.year
            else->when{
                startDate.year.year < currentChoiceDate.year.year->true
                startDate.year.year == currentChoiceDate.year.year && startDate.month.month < currentChoiceDate.month.month->true
                else -> false
            }
        }

    }

    /**
     * 能否往下切换
     */
    fun isCanNext(isYear: Boolean = false): Boolean {
        return when{
            isYear -> endDate.year.year > currentChoiceDate.year.year
            else->when{
                endDate.year.year > currentChoiceDate.year.year->true
                endDate.year.year == currentChoiceDate.year.year && endDate.month.month > currentChoiceDate.month.month->true
                else->false
            }
        }

    }

    /**
     * 是否是当前月
     */
    fun isCurrentMonth():Boolean{
        return false
    }

    /**
     * 是否今天
     */
    fun isCurrentToday():Boolean{
        return false
    }

}