package com.ct.aac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AACHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aac_home)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_aac_bind -> startActivity(Intent(this, BindActivity::class.java))
            R.id.btn_aac_lifecycle -> startActivity(Intent(this, LifecycleActivity::class.java))
            R.id.btn_aac_viewModel -> startActivity(Intent(this, ViewModelActivity::class.java))
            R.id.btn_aac_paging -> startActivity(Intent(this, PagingActivity::class.java))
            R.id.btn_aac_room -> startActivity(Intent(this, RoomActivity::class.java))
            R.id.btn_aac_synthesize -> startActivity(Intent(this, SynthesizeActivity::class.java))
        }

    }
}
