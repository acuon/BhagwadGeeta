package com.example.bhagwadgeeta.utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

const val DATE_FORMAT_MONTH_YEAR = "MMMM yyyy"

fun Activity.hideSoftKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.gone() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun ViewGroup.inflater() = LayoutInflater.from(context)

fun RecyclerView.verticalView(context: Context, stackEnd: Boolean = false) {
    layoutManager = LinearLayoutManagerWrapper(context).apply { stackFromEnd = stackEnd }
    layoutManager
}