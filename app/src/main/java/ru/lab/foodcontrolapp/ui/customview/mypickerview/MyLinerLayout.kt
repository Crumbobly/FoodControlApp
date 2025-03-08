package ru.lab.foodcontrolapp.ui.customview.mypickerview

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager


class MyLinerLayout(context: Context?) : LinearLayoutManager(context) {
    private var isScrollEnabled = true

    fun setScrollEnabled(flag: Boolean) {
        this.isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }
}