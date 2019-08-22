package cn.logcode.calendar.view


import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View


import cn.logcode.calendar.R
import cn.logcode.calendar.date.Day
import cn.logcode.calendar.date.Month
import cn.logcode.calendar.date.WeekEnum
import cn.logcode.calendar.date.Year
import java.util.*

/**
 * Created by HDL on 2018/3/6.
 *
 * @author HDL
 */

class CalendarViewDialog private constructor() {
    private var calendarView: CalendarView? = null
    private var dialog: Dialog? = null
    private var context: Context? = null

    /**
     * 显示
     *
     * @param context
     * @return
     */
    fun init(context: Context): CalendarViewDialog {
        if (dialog == null || calendarView == null || this.context !== context) {
            this.context = context
            dialog = Dialog(context, R.style.DialogTheme)
            val view = View.inflate(context, R.layout.dialog_calendar, null)
            view.setOnTouchListener { v, event -> true }
            calendarView = view.findViewById(R.id.robotoCalendarPicker)

            dialog!!.setContentView(view)

            calendarView!!.removeWeek(WeekEnum.Wednesday)
            calendarView!!.removeWeek(WeekEnum.Friday)
            calendarView!!.removeWeek(WeekEnum.Monday)
            calendarView!!.removeWeek(WeekEnum.Saturday)
            calendarView!!.removeWeek(WeekEnum.Sunday)
            calendarView!!.removeWeek(WeekEnum.Thursday)
            calendarView!!.removeWeek(WeekEnum.Tuesday)

            val dm = context.resources.displayMetrics
            val displayWidth = dm.widthPixels
            val displayHeight = dm.heightPixels
            val p = dialog!!.window!!.attributes  //获取对话框当前的参数值
            p.width = (displayWidth * 0.9).toInt()    //宽度设置为屏幕的0.55
            p.height = (displayHeight * 0.7).toInt()    //高度设置为屏幕的0.28
            dialog!!.window!!.attributes = p     //设置生效
        }
        return this
    }


    fun setSelectDay(time:Long):CalendarViewDialog{
        calendarView!!.setSelectedDay(time)
        return this
    }

    /**
     * 设置开始时间
     */
    fun setStartDate(date: Date) :CalendarViewDialog{
        calendarView!!.setStartDate(date)
        return this
    }

    /**
     * 设置结束时间
     */
    fun setEndDate(date: Date):CalendarViewDialog {
        calendarView!!.setEndDate(date)
        return this
    }

    fun setDateChoiceListener(listener:((Year, Month, Day) -> Unit)):CalendarViewDialog{
        calendarView!!.choseDateListener = listener
        return this
    }

    /**
     * 增加可选周
     */
    fun addWeek(week: WeekEnum) :CalendarViewDialog{
        calendarView!!.addWeek(week)
        return this
    }

    /**
     * 移除可选周
     */
    fun removeWeek(week: WeekEnum):CalendarViewDialog {
        calendarView!!.removeWeek(week)
        return this
    }

    fun show() {
        if (dialog != null) {
            dialog!!.show()
        }
    }

    fun close() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    companion object {
        private var mCalendarViewDialog: CalendarViewDialog? = null
        val instance: CalendarViewDialog?
            get() {
                if (mCalendarViewDialog == null) {
                    synchronized(CalendarViewDialog::class.java) {
                        if (mCalendarViewDialog == null) {
                            mCalendarViewDialog = CalendarViewDialog()
                        }
                    }
                }
                return mCalendarViewDialog
            }
    }
}
