package com.example.weekflex.Fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.weekflex.Data.RoutineItem
import com.example.weekflex.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RoutineModifyBottomDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {
    private lateinit var dialogView: View
    private lateinit var taskNameTextView: TextView
    private lateinit var taskModifyTextView: TextView
    private lateinit var taskModifyImageView: ImageView
    private lateinit var taskDeleteTextView: TextView
    private lateinit var taskDeleteImageView: ImageView
    private lateinit var cancelImageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.routine_modify_bottom_dialog_layout, container, false)
        dialogView = view

        taskNameTextView = dialogView.findViewById(R.id.task_name_textView)
        taskNameTextView.text = routineItem.routineItemTitle

        val onModify = { Toast.makeText(context, "ModifyMessage", Toast.LENGTH_SHORT).show() }
        taskModifyTextView = dialogView.findViewById(R.id.task_modify_textview)
        taskModifyTextView.setOnClickListener {onModify.invoke()}
        taskModifyImageView = dialogView.findViewById(R.id.task_modify_Imageview)
        taskModifyImageView.setOnClickListener {onModify.invoke()}

        val onDelete = { Toast.makeText(context, "DeleteMessage", Toast.LENGTH_SHORT).show() }

        taskDeleteTextView = dialogView.findViewById(R.id.task_delete_textview)
        taskDeleteTextView.setOnClickListener {onDelete.invoke()}
        taskDeleteImageView = dialogView.findViewById(R.id.task_delete_textview)
        taskDeleteImageView.setOnClickListener {onDelete.invoke()}

        cancelImageView = dialogView.findViewById(R.id.task_modify_dialog_close_imageView)
        with(cancelImageView){ setOnClickListener{
            dialog?.also { getBottomSheetDialog(it).state = BottomSheetBehavior.STATE_HIDDEN }
        }}

        return view
    }

    override fun onClick(view: View) {
        when (view.getId()) {
            taskModifyTextView.id -> Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show()
            taskDeleteTextView.id -> Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show()
            cancelImageView.id -> Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show()
        }
        dismiss()
    }

    companion object {
        val instance: RoutineModifyBottomDialogFragment
            get() = RoutineModifyBottomDialogFragment()
        val TAG = "bottomSheet"

        lateinit var routineItem: RoutineItem

        fun showTask(fragmentManager : FragmentManager, passedRoutineItem : RoutineItem){
            routineItem = passedRoutineItem
            instance.show(fragmentManager, TAG)
        }
    }

    override fun onActivityCreated(arg0: Bundle?) { super.onActivityCreated(arg0)
        dialog?.window?.setWindowAnimations(R.style.DialogAnimation)
    }

    fun getBottomSheetDialog(dialog: DialogInterface) : BottomSheetBehavior<FrameLayout>{
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

        // Do something with your dialog like setContentView() or whatever
        Log.d("Dismiss", dialog.dismissWithAnimation.toString())
        dialog.dismissWithAnimation = true
        return dialog
    }
}