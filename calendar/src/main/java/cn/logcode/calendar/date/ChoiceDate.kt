package cn.logcode.calendar.date

import cn.logcode.calendar.Utils
import java.util.*

/**
 * Create by caostgrace on 2019-08-13 14:09
 *
 * @author caostgrace
 * @project_name: CalendarLibrary
 * @package: cn.logcode.calendar.date
 * @class_name: ChoiceDate
 * @github: https://github.com/CaostGrace
 * @remark: 选择的日期
 */
data class ChoiceDate(val year: Year, val month: Month, val day: Day){

    companion object {
        fun instance(calendar: Calendar):ChoiceDate{
            val currentYear = Year.toYear(calendar)
            val currentMonth = Month.toMonth((calendar[Calendar.MONTH] + 1), calendar)
            val currentDay = Day.toDay(calendar[Calendar.DAY_OF_MONTH], currentMonth.month, calendar)
            return ChoiceDate(currentYear,currentMonth,currentDay)
        }
    }

    fun nextYear():ChoiceDate{
        var calendar = Utils.getCalendar(this)
        calendar[Calendar.YEAR] = calendar[Calendar.YEAR] + 1
        calendar[Calendar.MONTH] = 0
        calendar[Calendar.DAY_OF_MONTH] = 1
        return instance(calendar)
    }

    fun nextMonth():ChoiceDate{
        var calendar = Utils.getCalendar(this)
        calendar[Calendar.MONTH] = calendar[Calendar.MONTH] + 1
        calendar[Calendar.DAY_OF_MONTH] = 1
        return instance(calendar)
    }

    fun nextDay():ChoiceDate{
        var calendar = Utils.getCalendar(this)
        calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH] + 1
        return instance(calendar)
    }


    fun lastYear():ChoiceDate{
        var calendar = Utils.getCalendar(this)
        calendar[Calendar.YEAR] = calendar[Calendar.YEAR] - 1
        calendar[Calendar.MONTH] = 11
        calendar[Calendar.DAY_OF_MONTH] = 1
        return instance(calendar)
    }

    fun lastMonth():ChoiceDate{
        var calendar = Utils.getCalendar(this)
        calendar[Calendar.MONTH] = calendar[Calendar.MONTH] - 1
        calendar[Calendar.DAY_OF_MONTH] = 1
        return instance(calendar)
    }

    fun lastDay():ChoiceDate{
        var calendar = Utils.getCalendar(this)
        calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH] - 1
        return instance(calendar)
    }

}