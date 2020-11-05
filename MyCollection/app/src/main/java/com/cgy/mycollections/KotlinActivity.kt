package com.cgy.mycollections

import android.os.Bundle
//import android.support.design.widget.Snackbar
//import android.support.v7.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity
import com.cgy.mycollections.databinding.ActivityKotlinBinding
import com.google.android.material.snackbar.Snackbar
//import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : AppCompatActivity() {

    //kotlin-android-extensions 已经deprecated了，所以不再使用其中的生成kotlin布局的方式，改用viewBinding
    private lateinit var binding: ActivityKotlinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKotlinBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_kotlin)
        setSupportActionBar(binding.toolbar)
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}
