package com.example.lifestyle.hope.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.example.lifestyle.hope.R

class CautionDialogFragment : DialogFragment(),View.OnClickListener {
    override fun onClick(v: View?) {

    }
    lateinit var dialog_title : TextView
    lateinit var cancel:TextView
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog: AlertDialog.Builder = AlertDialog.Builder(activity)
        dialog.setView(R.layout.dialog_my_custom)
        dialog.show()
        var dialogLogin:Dialog = dialog.create()
        return dialogLogin
    }


}