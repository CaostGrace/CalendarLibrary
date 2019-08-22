package cn.logcode.calendar.view

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import cn.logcode.calendar.CalendarInstance
import cn.logcode.calendar.R
import cn.logcode.calendar.Utils
import cn.logcode.calendar.date.*
import java.util.*

/**
 * calendar view
 *
 * @author hdl
 */
class CalendarView : LinearLayout {


    private var calendarInstance: CalendarInstance = CalendarInstance.init(Date())

    private lateinit var rootCalendarView: View

    private lateinit var monthText: TextView

    private lateinit var leftButton: ImageView
    private lateinit var rightButton: ImageView

    private lateinit var monthOfDayRecycler: RecyclerView

    private lateinit var monthOfWeekOne: TextView
    private lateinit var monthOfWeekTwo: TextView
    private lateinit var monthOfWeekThree: TextView
    private lateinit var monthOfWeekFour: TextView
    private lateinit var monthOfWeekFive: TextView
    private lateinit var monthOfWeekSix: TextView
    private lateinit var monthOfWeekSeven: TextView


    private var canChoseWeek: MutableSet<WeekEnum> = mutableSetOf()

    /**
     * 当前选择的时间
     */
    private lateinit var currentChoseDate: ChoiceDate


    /**
     * 选择时间回调
     */
    var choseDateListener: ((Year, Month, Day) -> Unit)? = null

    constructor(context: Context) : super(context) {
        createView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        createView()
    }

    private fun createView() {
        rootCalendarView = LayoutInflater.from(context).inflate(R.layout.calendar_picker_layout, this, true)

        monthText = findViewById(R.id.monthText)
        leftButton = findViewById(R.id.leftButton)
        rightButton = findViewById(R.id.rightButton)
        monthOfDayRecycler = findViewById(R.id.month_of_day_recycler)
        monthOfWeekOne = findViewById(R.id.month_of_week_one)
        monthOfWeekTwo = findViewById(R.id.month_of_week_two)
        monthOfWeekThree = findViewById(R.id.month_of_week_three)
        monthOfWeekFour = findViewById(R.id.month_of_week_four)
        monthOfWeekFive = findViewById(R.id.month_of_week_five)
        monthOfWeekSix = findViewById(R.id.month_of_week_six)
        monthOfWeekSeven = findViewById(R.id.month_of_week_seven)

        addWeek(WeekEnum.Wednesday)
        addWeek(WeekEnum.Friday)
        addWeek(WeekEnum.Monday)
        addWeek(WeekEnum.Saturday)
        addWeek(WeekEnum.Sunday)
        addWeek(WeekEnum.Thursday)
        addWeek(WeekEnum.Tuesday)

        currentChoseDate = calendarInstance.currentChoiceDate

        initView()

        listener()


    }

    private fun listener() {
        /**
         * 时间改变监听
         */
        calendarInstance.dateChangeListener = {
            initView()
        }

        /**
         * 上一月
         */
        leftButton.setOnClickListener {
            calendarInstance.lastMonth()
        }

        /**
         * 下一月
         */
        rightButton.setOnClickListener {
            calendarInstance.nextMonth()
        }

    }


    /**
     * 初始化view
     */
    private fun initView() {
        monthText.text = getCurrentYear() + "/" + getCurrentMonth() + "月"
        monthOfWeekOne.text = WeekEnum.Monday.weekName
        monthOfWeekTwo.text = WeekEnum.Tuesday.weekName
        monthOfWeekThree.text = WeekEnum.Wednesday.weekName
        monthOfWeekFour.text = WeekEnum.Thursday.weekName
        monthOfWeekFive.text = WeekEnum.Friday.weekName
        monthOfWeekSix.text = WeekEnum.Saturday.weekName
        monthOfWeekSeven.text = WeekEnum.Sunday.weekName

        leftButton.visibility = View.GONE
        rightButton.visibility = View.GONE

        if (calendarInstance.isCanNext()) {
            rightButton.visibility = View.VISIBLE
        }
        if (calendarInstance.isCanLast()) {
            leftButton.visibility = View.VISIBLE
        }

        if(::currentChoseDate.isInitialized){
            while (!canChoseWeek.contains(currentChoseDate.day.week)) {
                currentChoseDate = currentChoseDate.nextDay()
            }
        }

        monthOfDayRecycler.adapter = MonthDayAdapter(context, calendarInstance.currentChoiceDate.month.days)
        monthOfDayRecycler.layoutManager = GridLayoutManager(context, 7, GridLayoutManager.VERTICAL, false)

    }

    /**
     * 设置开始时间
     */
    fun setStartDate(date: Date) {
        calendarInstance.setStartDateInterval(ChoiceDate.instance(Utils.getCalendar(date)))
        currentChoseDate = calendarInstance.currentChoiceDate
        initView()
    }

    /**
     * 设置结束时间
     */
    fun setEndDate(date: Date) {
        calendarInstance.setEndDateInterval(ChoiceDate.instance(Utils.getCalendar(date)))
        currentChoseDate = calendarInstance.currentChoiceDate
        initView()
    }

    /**
     * 设置开始结束时间
     */
    fun setStartAndEndDate(start: Date, end: Date) {
        calendarInstance.setDateInterval(start, end)
        currentChoseDate = calendarInstance.currentChoiceDate
        initView()
    }

    /**
     * 增加可选周
     */
    fun addWeek(week: WeekEnum) {
        if (!canChoseWeek.contains(week)) {
            canChoseWeek.add(week)
            initView()
        }
    }

    /**
     * 移除可选周
     */
    fun removeWeek(week: WeekEnum) {
        if (canChoseWeek.contains(week)) {
            canChoseWeek.remove(week)
            initView()
        }
    }

    fun getCurrentYear(): String {
        return "" + calendarInstance.currentChoiceDate.year.year
    }

    fun getCurrentMonth(): String {
        return "" + calendarInstance.currentChoiceDate.month.month
    }

    fun getCurrentDay(): String {
        return "" + calendarInstance.currentChoiceDate.day.day
    }


    inner class MonthDayAdapter(private val context: Context, private val days: MutableList<Day>) : RecyclerView.Adapter<MonthDayAdapter.Holder>() {

        /**
         * 跳过几个日期
         * 作用，一个月有时候不是周日开头
         */
        private var jumpCount = 0

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
            var view = LayoutInflater.from(context).inflate(R.layout.calendar_day_of_the_month_layout, null, false)
            return Holder(view)
        }

        override fun getItemCount(): Int {
            jumpCount = when (days[0].week) {
                WeekEnum.Monday -> 0
                WeekEnum.Tuesday -> 1
                WeekEnum.Wednesday -> 2
                WeekEnum.Thursday -> 3
                WeekEnum.Friday -> 4
                WeekEnum.Saturday -> 5
                WeekEnum.Sunday -> 6
            }
            return days.size + jumpCount
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {

            /**
             * 日期能否选择
             */
            var isCanChoice = true

            reset(holder)
            if (position < jumpCount) {
                return
            }

            var day = days[position - jumpCount]

            var dayChoiceDate = ChoiceDate(day.year, day.month, day)

            holder.textView.text = day.day.toString()

            /**
             * 日期不能选
             */
            if (!calendarInstance.isCanChoice(dayChoiceDate)) {
                isCanChoice = false
                holder.textView.setTextColor(ContextCompat.getColor(context, R.color.month_day_text_color_no_choice))
            }

            /**
             * 星期不能选
             */
            if (!canChoseWeek.contains(day.week)) {
                isCanChoice = false
                holder.textView.setTextColor(ContextCompat.getColor(context, R.color.month_day_text_color_no_choice))
            }

            /**
             * 是否是今天
             */
            if (dayChoiceDate.isToday()) {
                holder.dayOfTheMonthBackground.setBackgroundResource(R.drawable.ring)
            }

            /**
             * 是否选中
             */
            if (dayChoiceDate == currentChoseDate) {

                isCanChoice = false
                holder.dayOfTheMonthBackground.setBackgroundResource(R.drawable.circle_ring)
                holder.textView.setTextColor(ContextCompat.getColor(context, R.color.roboto_calendar_selected_day_font))
            }


            holder.view.isClickable = true

            holder.view.setOnClickListener {
                Log.d("isCanChoice", isCanChoice.toString())
                if (!isCanChoice) {
                    return@setOnClickListener
                }
                currentChoseDate = dayChoiceDate
//                initView()
                if (choseDateListener != null) {
                    choseDateListener?.let { it1 -> it1(dayChoiceDate.year, dayChoiceDate.month, dayChoiceDate.day) }
                }
                notifyDataSetChanged()
            }

        }


        private fun reset(holder: Holder) {
            holder.dayOfTheMonthBackground.setBackgroundColor(Color.WHITE)
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.month_day_text_color))
            holder.textView.text = ""
        }

        inner class Holder(val view: View) : RecyclerView.ViewHolder(view) {
            var dayOfTheMonthBackground: RelativeLayout = view.findViewById(R.id.dayOfTheMonthBackground)
            var textView: TextView = view.findViewById(R.id.textView)

        }
    }

}
