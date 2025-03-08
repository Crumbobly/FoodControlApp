package ru.lab.foodcontrolapp.ui.customview.mypickerview

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.SnapHelper
import ru.lab.foodcontrolapp.R


/**
 * Подсвечивает переданный `view`, увеличивая его размер и изменяя цвет текста.
 *
 * @param view Виджет, который нужно подсветить (должен быть `TextView`).
 */
fun highlight(context: Context, view: View) {
    view.animate()
        .scaleX(1.2f)
        .scaleY(1.2f)
        .setDuration(150)
        .start()

    val textView = view as TextView
    textView.setTextColor(ContextCompat.getColor(context, R.color.colorOnBackground))
}


/**
 * Сбрасывает подсветку `view`, возвращая его к обычному размеру и цвету.
 *
 * @param view Виджет, который нужно сбросить (должен быть `TextView`).
 */
fun resetHighlight(context: Context, view: View) {
    view.animate()
        .scaleX(1f)
        .scaleY(1f)
        .setDuration(150)
        .start()

    val textView = view as TextView
    textView.setTextColor(ContextCompat.getColor(context, R.color.colorOnBackgroundLight))
}

