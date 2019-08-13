package cn.logcode.calendar.listener

import cn.logcode.calendar.date.Day
import cn.logcode.calendar.date.Month
import cn.logcode.calendar.date.Year

/**
 * Create by caostgrace on 2019-08-13 14:56
 *
 * @author caostgrace
 * @project_name: CalendarLibrary
 * @package: cn.logcode.calendar.listener
 * @class_name: DateChangeListener
 * @github: https://github.com/CaostGrace
 * @remark:
 */
interface DateChangeListener {
    fun change(year: Year,month: Month,day: Day)
}