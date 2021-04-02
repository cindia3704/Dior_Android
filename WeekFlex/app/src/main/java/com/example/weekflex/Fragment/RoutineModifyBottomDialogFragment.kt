package com.example.weekflex.Fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.weekflex.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RoutineModifyBottomDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {
    private lateinit var dialogView: View
    private lateinit var taskNameTextView: TextView
    private lateinit var taskModifyTextView: TextView
    private lateinit var taskDeleteTextView: TextView
    private lateinit var cancelImageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.routine_modify_bottom_dialog_layout, container, false)
        dialogView = view

        taskNameTextView = dialogView.findViewById(R.id.task_name_textView)

        taskModifyTextView = dialogView.findViewById(R.id.task_modify_textview)
        taskDeleteTextView = dialogView.findViewById(R.id.task_delete_textview)
        taskDeleteTextView.setOnClickListener {
            Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show()
        }
        cancelImageView = dialogView.findViewById(R.id.task_modify_dialog_close_imageView)

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
    }

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
        dialog?.window?.setWindowAnimations(R.style.DialogAnimation)
//        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

//        dialog?.window?.setWindowAnimations(R.style.DialogAnimation)

        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            Log.d("하이","좀 자자")
            BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!).peekHeight = 0
            BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!).state = BottomSheetBehavior.STATE_COLLAPSED
            BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
        }

        // Do something with your dialog like setContentView() or whatever
        Log.d("Dismiss", dialog.dismissWithAnimation.toString())
        dialog.dismissWithAnimation = true
        return dialog
    }
}