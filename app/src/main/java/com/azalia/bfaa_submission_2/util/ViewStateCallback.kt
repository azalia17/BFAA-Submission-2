package com.azalia.bfaa_submission_2.util

import android.view.View

interface ViewStateCallback<T> {

    fun onSuccess(data: T)
    fun onLoading()
    fun onFailed(message: String?)

    val invisible: Int
        get() = View.INVISIBLE

    val visible: Int
        get() = View.VISIBLE
}