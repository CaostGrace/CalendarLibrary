package cn.logcode.calendar.test

import cn.logcode.calendar.CalendarInstance
import cn.logcode.calendar.Utils
import cn.logcode.calendar.date.ChoiceDate
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
    var temp =  CalendarInstance.init(Utils.getCalendar())

    temp.dateChangeListener = {
        System.out.println("时间改变："+Utils.dateToString(Utils.getCalendar(it).time))
    }

    var end = Utils.getCalendar()
    end[Calendar.YEAR] = 2020
    temp.setEndDateInterval(ChoiceDate.instance(end))
//
    end[Calendar.YEAR] = 2018
    temp.setStartDateInterval(ChoiceDate.instance(end))



    System.out.println()

    System.out.println(Utils.dateToString(temp.startDate))
    System.out.println(Utils.dateToString(temp.currentChoiceDate))
    System.out.println(Utils.dateToString(temp.endDate))


    temp.lastYear()
    temp.nextMonth()
    temp.nextYear()
    temp.nextYear()


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