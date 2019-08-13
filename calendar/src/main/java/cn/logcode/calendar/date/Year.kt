package cn.logcode.calendar.date

import cn.logcode.calendar.Utils
import java.util.*

/**
 * Create by caostgrace on 2019-08-12 15:31
 *
 * @author caostgrace
 * @project_name: CalendarLibrary
 * @package: cn.logcode.calendar.date
 * @class_name: Year
 * @github: https://github.com/CaostGrace
 * @remark:
 */
data class Year(
        val months:MutableList<Month>,
        //年份
        val year: Int,
        //是否闰年
        val leapYear: Boolean = false
) {
    companion object {
        lateinit var year:Year
        fun toYear(): Year {
            return toYear(Utils.getCalendar())
        }

        fun toYear(calendar: Calendar): Year {
            val yearByInt = calendar[Calendar.YEAR]
            val isLeapYear = Utils.isLeapYear(calendar)
            var months = mutableListOf<Month>()
            for (month in 1..12){
                months.add(Month.toMonth(month, calendar))
            }
            year = Year(months, yearByInt,isLeapYear)
            for(month in months){
                month.year = year
            }
            return year
        }
    }


    val january:Month = months[0]
    val february:Month = months[1]
    val march:Month = months[2]
    val april:Month = months[3]
    val may:Month = months[4]
    val june:Month = months[5]
    val july:Month = months[6]
    val august:Month = months[7]
    val september:Month = months[8]
    val october:Month = months[9]
    val november:Month = months[10]
    val december:Month = months[11]

    val firstMonth:Month = months[0]
    val lastMonth:Month = months[11]

}