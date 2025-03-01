package ru.lab.foodcontrolapp.ui.customview.mypickerview

import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

/**
 * Декоратор для `RecyclerView`, который добавляет полупрозрачные границы сверху и снизу списка.
 *
 * @param _color Цвет границ.
 * @param _height Высота границ в пикселях.
 */
class MyPickerViewBorderItemDecorator(private val _color: Int, private val _height: Int) :
    RecyclerView.ItemDecoration() {

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val paint = Paint().apply {
            this.color = _color
            this.style = Paint.Style.FILL
            this.alpha = 64
        }

        val left = parent.paddingLeft.toFloat()
        val right = parent.width - parent.paddingRight.toFloat()

        canvas.drawRect(left, 0f, right, _height.toFloat(), paint)

        val bottom = parent.height.toFloat()
        canvas.drawRect(left, bottom - _height, right, bottom, paint)
    }
}
