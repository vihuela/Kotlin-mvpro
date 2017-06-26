package com.github.kotlin_mvpro.ui

import android.os.Bundle
import android.widget.TextView
import com.github.kotlin_mvpro.R
import com.github.library.base.BaseActivity
import com.github.kotlin_mvpro.databinding.ActivityMainBinding
import com.github.kotlin_mvpro.ui.presenter.MainActivityPresenter
import com.github.kotlin_mvpro.ui.view.IMainActivity
import org.jetbrains.anko.findOptional

class MainActivity : BaseActivity<MainActivityPresenter, ActivityMainBinding>(), IMainActivity {
    override fun setText(tv: String) {
        println()
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findOptional<TextView>(R.id.tv)?.setOnClickListener {
        }
    }
}
