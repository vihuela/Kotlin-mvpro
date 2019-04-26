/*
 * Copyright (C) 2017 Ricky.yao https://github.com/vihuela
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 */

package com.github.kotlin_mvpro.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


//BindingAdapter需要app module执行apt，不能放于lib module
@BindingAdapter("android:load_image")
fun loadImage(iv: ImageView, url: String?) =
        Glide.with(iv.context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(ColorDrawable(Color.parseColor("#DCDDE1")))
                .error(ColorDrawable(Color.parseColor("#DCDDE1")))
                .crossFade()
                .into(iv)

@BindingAdapter("android:setup_toolbar")
fun setupToolbar(toolbar: Toolbar, ctx: AppCompatActivity) {
    ctx.setSupportActionBar(toolbar)
    toolbar.setNavigationOnClickListener { ctx.finish() }
    ctx.supportActionBar?.setDisplayHomeAsUpEnabled(true)
}




