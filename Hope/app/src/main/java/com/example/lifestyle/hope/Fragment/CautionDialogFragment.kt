package com.example.lifestyle.hope.Fragment

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.example.lifestyle.hope.R

class CautionDialogFragment() : DialogFragment(),View.OnClickListener {

    lateinit var dialog_title : TextView
    lateinit var cancel:TextView
    var title =""
    fun newInstance(title :String):CautionDialogFragment {
        val dialogFragment : CautionDialogFragment = CautionDialogFragment()
        val args  = Bundle()
        args.putString("title",title)
        dialogFragment.arguments =args
        return dialogFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view :View = inflater.inflate(R.layout.dialog_my_custom,container)
        title = arguments!!.getString("title")

        init(view)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.dialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        this.dialog.setCanceledOnTouchOutside(false)
    }
    fun init(v:View){
        dialog_title = v.findViewById(R.id.tv_title_dialog)
        cancel = v.findViewById(R.id.tv_cancle)
        dialog_title.setText(title)
        cancel.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_cancle->{
                this.dismiss()
            }
        }
    }
}