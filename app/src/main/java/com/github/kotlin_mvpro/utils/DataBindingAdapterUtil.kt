package com.github.kotlin_mvpro.utils

import android.databinding.BindingAdapter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.lang.Exception


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





