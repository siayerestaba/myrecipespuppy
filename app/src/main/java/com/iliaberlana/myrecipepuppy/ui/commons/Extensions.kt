package com.iliaberlana.myrecipepuppy.ui.commons

import android.content.Context
import android.support.annotation.LayoutRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.iliaberlana.myrecipepuppy.R

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun Context.toast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

fun String.cleanHexCaracters(): String {
    return this.replace("\r", "").replace("\n", "").replace("\t", "")
}

fun ImageView.loadImage(path: String?) {
    Glide.with(context)
        .load(path)
        .centerCrop()
        .placeholder(R.mipmap.placeholder)
        .into(this)
}

fun ImageView.cleanImage() {
    Glide.with(context).clear(this)
    setImageResource(R.mipmap.placeholder)
}

fun String.logDebug(message: String) {
    Log.d(this, message)
}