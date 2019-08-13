package cn.logcode.calendar.test

import cn.logcode.calendar.date.Year
import java.util.*


/**
 * Create by caostgrace on 2019-08-12 15:40
 *
 * @author caostgrace
 * @project_name: CalendarLibrary
 * @package: cn.logcode.calendar.test
 * @class_name: Test
 * @github: https://github.com/CaostGrace
 * @remark:
 */
fun main(args: Array<String>) {
    var calendar = Calendar.getInstance()

    System.out.println(calendar[Calendar.MONTH])

}



fun test1() {
    var calendar = Calendar.getInstance()

    calendar.set(Calendar.MONTH, 2)
    calendar.set(Calendar.YEAR, 2008)

    var year = Year.toYear(calendar)

    System.out.println(year.year)
    System.out.println(year.leapYear)

    var month = year.june

    System.out.println()

    System.out.println(month.month)
    System.out.println(month.monthDayCount)
    System.out.println(month.monthName)

//    System.out.println(month.year)

    System.out.println()

//    System.out.println(month.days)


    System.out.println()

    var days = month.days

    for (day in days) {
        System.out.print("${day.year.year}年")
        System.out.print("-" + day.month.month + "月")
        System.out.print("-" + day.day + "日")
        System.out.print("-" + day.week.weekName)
        System.out.println()
    }
}