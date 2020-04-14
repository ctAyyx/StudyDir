package com.ct.study.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ct.study.R
import com.share.dialog.BaseDialogFragment
import com.share.dialog.LoadingDialog

class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)


    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_dialog_loading -> {
               // LoadingDialog(this).show()
                BaseDialogFragment().show(supportFragmentManager, "Loading")
            }
        }
    }
}
