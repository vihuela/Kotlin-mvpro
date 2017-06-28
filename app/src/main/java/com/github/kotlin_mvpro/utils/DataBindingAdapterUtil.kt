package com.github.kotlin_mvpro.utils

import android.databinding.BindingAdapter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


@BindingAdapter("android:load_image")
fun loadImage(iv: ImageView, url: String?) =
        Glide.with(iv.context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(ColorDrawable(Color.parseColor("#DCDDE1")))
                .crossFade()
                .into(iv)





