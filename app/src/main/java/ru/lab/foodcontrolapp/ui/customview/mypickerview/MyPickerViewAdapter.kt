package ru.lab.foodcontrolapp.ui.customview.mypickerview

import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Адаптер для `MyPickerView`, используемый для отображения списка элементов с возможностью выбора одного из них.
 *
 * @param recyclerView Родительский RecyclerView.
 * @param textSize Размер шрифта для текста в элементах списка.
 */
class MyPickerViewAdapter<Type>(
    private val recyclerView: RecyclerView,
    private val textSize: Float

) : RecyclerView.Adapter<MyPickerViewHolder>() {

    // Список данных с дополнительными пустыми элементами для эффекта центрирования
    private var data: List<Type?> = listOf()

    // Количество видимых элементов на экране
    private val visibleItemCount = 3

    /**
     * Получает элемент по указанной позиции.
     *
     * @param pos Индекс элемента в списке.
     * @return Элемент `Type`, либо `null`, если индекс выходит за границы списка.
     */
    fun getItemByPos(pos: Int): Type?{
        return data.getOrNull(pos)
    }

    /**
     * Устанавливает новые данные в адаптер, добавляя фиктивные элементы в начале и конце списка.
     * @param newData Новый список элементов.
     */
    fun setData(newData: List<Type>){
        val fake: List<Type?> = List(visibleItemCount / 2) { null }
        data = fake + newData + fake
        notifyDataSetChanged()
    }

    /**
     * Создаёт новый `ViewHolder` для отображения элемента списка.
     *
     * @param parent Родительская ViewGroup.
     * @param viewType Тип элемента (не используется в данном случае).
     * @return Новый `MyPickerViewHolder`.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPickerViewHolder {

        // Создаём textView для отображения одного элемента
        val textView = TextView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER
            textSize = this.textSize
        }

        // Устанавливаем ему высоту
        if (recyclerView.height > 0 ) {
            textView.layoutParams.height = recyclerView.height / visibleItemCount
            textView.requestLayout()
        }

        return MyPickerViewHolder(textView)
    }

    /**
     * Возвращает количество элементов в списке.
     * @return Количество элементов.
     */
    override fun getItemCount(): Int {
        return data.size
    }

    /**
     * Привязывает данные к `ViewHolder`.
     *
     * @param holder Экземпляр `MyPickerViewHolder`.
     * @param position Позиция элемента в списке.
     */
    override fun onBindViewHolder(holder: MyPickerViewHolder, position: Int) {

        // Получаем элемент
        val value = data[position]

        // Если элемент 'фейковый', то делаем его невидимым
        if (value == null) {
            holder.itemView.alpha = 0f
            holder.textView.text = ""
        }
        // Иначе устанавливаем в качестве текста строковое значение ('Type') объекта
        else {
            holder.itemView.alpha = 1f
            holder.textView.text = "$value"
        }

        // Меняем размер шрифта в соответствие с настройками родительского MyPickerView
        holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

    }



}