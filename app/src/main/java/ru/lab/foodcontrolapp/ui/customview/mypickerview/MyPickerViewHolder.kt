package ru.lab.foodcontrolapp.ui.customview.mypickerview

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * ViewHolder для `MyPickerView`, который хранит ссылку на `TextView` внутри `RecyclerView`.
 *
 * @param textView Элемент `TextView`, отображающий значение в списке.
 */
class MyPickerViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)