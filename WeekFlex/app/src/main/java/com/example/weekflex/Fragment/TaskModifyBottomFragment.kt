package com.example.weekflex.Fragment

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.example.weekflex.Data.Category
import com.example.weekflex.Data.Task
import com.example.weekflex.Data.categoryToStarImage
import com.example.weekflex.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryView(
    val star: ImageView,
    val name: TextView,
    val category: Category
) {
    init {
        categoryToStarImage[category.categoryId]?.let { star.setImageResource(it) }
        name.text = category.categoryName
    }
}

class DaySelectView(
    val layout: ConstraintLayout,
    private val background: ImageView,
    private val dayText: TextView,
    private var backingSelected: Boolean
) {
    companion object {
        lateinit var selectedPair: Triple<Drawable, Float, ColorStateList>
        lateinit var unselectedPair: Triple<Drawable, Float, ColorStateList>
    }
    var selected: Boolean
        set(value) {
            backingSelected = value
            refresh()
        } get() = backingSelected

    init {
        refresh()
    }

    private fun refresh() {
        val (drawable, textSize, color) = (if (selected) selectedPair else unselectedPair)
//        background.setImageDrawable(imageView.drawable)
//        Log.d("DaySelectViewRefresh", "${dayText.textSize} <- ${textSize}")
        background.setImageDrawable(drawable)
        // 사이즈 정상적으로 안바뀜. sp dp 문제때문임. https://stackoverflow.com/questions/20364993/setting-textsize-programmatically
//        dayText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        dayText.setTextColor(color)
    }
}

class TaskModifyBottomFragment : BottomSheetDialogFragment(), View.OnClickListener {
    private lateinit var taskName: TextView
    private lateinit var categoryView: CategoryView
    private lateinit var categorySelectBtn: ImageView
    private lateinit var daySelectViewList: List<DaySelectView>
    private lateinit var timeSettingSwitch: Switch
    private lateinit var taskStartTimeTextView: TextView
    private lateinit var taskEndTimeTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val category = Category(0, "임시", 1, emptyList())

        val view = inflater.inflate(R.layout.task_modify_bottom_fragment, container, false)

        taskName = view.findViewById(R.id.task_name_textView)
        taskName.text = task.routineItemTitle
        categoryView = CategoryView(
                view.findViewById(R.id.task_category_star_imageView),
                view.findViewById(R.id.task_category_name_textView),
                category)

        categorySelectBtn = view.findViewById(R.id.task_category_select_btn)
        categorySelectBtn.setOnClickListener { _ -> Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show() }

        // String으로 가져오기 실패. 하드코딩한다.
//        val viewId = resources.getIdentifier("task_modify_button_day${index}", "ConstraintLayout", null)
        val viewIdList = listOf(
                R.id.task_modify_button_day0,
                R.id.task_modify_button_day1,
                R.id.task_modify_button_day2,
                R.id.task_modify_button_day3,
                R.id.task_modify_button_day4,
                R.id.task_modify_button_day5,
                R.id.task_modify_button_day6
        )
        val selectList = (0..1).map { index ->
            Log.d("task_modify_button_day$index", ConstraintLayout::class::simpleName.toString())
            val viewGroup = view.findViewById<ConstraintLayout>(viewIdList[index])
            var imageView: ImageView? = null
            var textView: TextView? = null
            for (i in 0 until viewGroup.childCount) {
                val view: View = viewGroup.getChildAt(i)
                when (view) {
                    is ImageView -> imageView = view
                    is TextView -> textView = view
                }
            }

            Log.d("TextView", textView?.text.toString())
            return@map Triple(imageView!!.drawable, textView!!.textSize, textView!!.textColors)
        }

        DaySelectView.unselectedPair = selectList[0]
        DaySelectView.selectedPair = selectList[1]

        daySelectViewList = (0..6).map { index ->
            val viewId = resources.getIdentifier("task_modify_button_day$index", ConstraintLayout::class::simpleName.name, null)
            val viewGroup = view.findViewById<ConstraintLayout>(viewIdList[index])
            var imageView: ImageView? = null
            var textView: TextView? = null
            for (i in 0 until viewGroup.childCount) {
                val view: View = viewGroup.getChildAt(i)
                when (view) {
                    is ImageView -> imageView = view
                    is TextView -> textView = view
                }
            }
            val dayView = DaySelectView(viewGroup, imageView!!, textView!!, false)
            dayView.layout.setOnClickListener {
                Toast.makeText(context, "인덱스 $index", Toast.LENGTH_SHORT).show()
                dayView.selected = !dayView.selected
            }

            return@map dayView
        }

        timeSettingSwitch = view.findViewById(R.id.time_setting_switch)
//        timeSettingSwitch.setOnClickListener { _ -> timeSettingSwitch.isChecked = !timeSettingSwitch.isChecked }

        taskStartTimeTextView = view.findViewById(R.id.task_startTime_textView)
        taskStartTimeTextView.text = task.startTime
        taskEndTimeTextView = view.findViewById(R.id.task_endTime_textView)
        taskEndTimeTextView.text = task.endTime

//        val onModify = { fragmentManager?.let { showTask(it, task) } }

        return view
    }

    override fun onClick(view: View) {
//        when (view.getId()) {
//            taskModifyTextView.id -> Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show()
//            taskDeleteTextView.id -> Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show()
//            cancelImageView.id -> Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show()
//        }
        dismiss()
    }

    companion object {
        val instance: TaskModifyBottomFragment
            get() = TaskModifyBottomFragment()
        val TAG = "TaskModifyBottomFragment"

        lateinit var task: Task

        fun showTask(fragmentManager: FragmentManager, passedTask: Task) {
            task = passedTask
            instance.show(fragmentManager, TAG)
        }
    }

    override fun onActivityCreated(arg0: Bundle?) { super.onActivityCreated(arg0)
        dialog?.window?.setWindowAnimations(R.style.DialogAnimation)
    }

    fun getBottomSheetDialog(dialog: DialogInterface): BottomSheetBehavior<FrameLayout> {
        val d = dialog as BottomSheetDialog

        val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?

        return BottomSheetBehavior.from(bottomSheet!!)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialog ->
            getBottomSheetDialog(dialog).also {
                it.peekHeight = 0
                it.state = BottomSheetBehavior.STATE_COLLAPSED
                it.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        Log.d("Dismiss", dialog.dismissWithAnimation.toString())
        dialog.dismissWithAnimation = true
        return dialog
    }
}
