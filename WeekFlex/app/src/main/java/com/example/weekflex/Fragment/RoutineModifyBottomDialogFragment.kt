package com.example.weekflex.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.weekflex.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RoutineModifyBottomDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {
    private var msgLo: LinearLayout? = null
    private var emailLo: LinearLayout? = null
    private var cloudLo: LinearLayout? = null
    private var bluetoothLo: LinearLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.routine_modify_bottom_dialog_layout, container, false)
        msgLo = view.findViewById(R.id.msgLo)
        emailLo = view.findViewById(R.id.emailLo)
        cloudLo = view.findViewById(R.id.cloudLo)
        bluetoothLo = view.findViewById(R.id.bluetoothLo)
        msgLo!!.setOnClickListener(this)
        emailLo!!.setOnClickListener(this)
        cloudLo!!.setOnClickListener(this)
        bluetoothLo!!.setOnClickListener(this)
        return view
    }

    override fun onClick(view: View) {
        when (view.getId()) {
            R.id.msgLo -> Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show()
            R.id.emailLo -> Toast.makeText(context, "Email", Toast.LENGTH_SHORT).show()
            R.id.cloudLo -> Toast.makeText(context, "Cloud", Toast.LENGTH_SHORT).show()
            R.id.bluetoothLo -> Toast.makeText(context, "Bluetooth", Toast.LENGTH_SHORT).show()
        }
        dismiss()
    }

    companion object {
        val instance: RoutineModifyBottomDialogFragment
            get() = RoutineModifyBottomDialogFragment()
    }

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
        if(dialog != null){
            dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        }
    }
}