package cn.logcode.calendar.date

import cn.logcode.calendar.Utils
import java.util.*

/**
 * Create by caostgrace on 2019-08-12 15:32
 *
 * @author caostgrace
 * @project_name: CalendarLibrary
 * @package: cn.logcode.calendar.date
 * @class_name: Day
 * @github: https://github.com/CaostGrace
 * @remark:
 */
data class Day(val week:WeekEnum,val day:Int){
    lateinit var year:Year
    lateinit var month:Month
    companion object {
        fun toDay(day: Int,month: Int,calendar: Calendar):Day{
            var week = when(Utils.getWeekOfMonth(calendar[Calendar.YEAR],month,day)){
                1->WeekEnum.Monday
                2->WeekEnum.Tuesday
                3->WeekEnum.Wednesday
                4->WeekEnum.Thursday
                5->WeekEnum.Friday
                6->WeekEnum.Saturday
                7->WeekEnum.Sunday
                else->throw IllegalStateException("日期错误")
            }

            return Day(week,day)
        }
    }
}