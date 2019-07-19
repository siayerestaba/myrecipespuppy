package com.iliaberlana.myrecipepuppy.ui

interface BaseView {
    fun hideLoading()
    fun showLoading()

    fun showToastMessage(stringId: Int)

    fun showErrorCase(stringId: Int)
    fun hideErrorCase()
}