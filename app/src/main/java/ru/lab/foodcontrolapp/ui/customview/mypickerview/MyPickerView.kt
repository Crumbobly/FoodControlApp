package ru.lab.foodcontrolapp.ui.customview.mypickerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import ru.lab.foodcontrolapp.R


/**
 * Кастомный `RecyclerView` для выбора элементов.
 *
 * Этот класс расширяет `RecyclerView` и позволяет пользователю выбрать один элемент из установленных через
 * [`setData()`][setData] данных.
 *
 * @constructor Создаёт `MyPickerView` с указанным контекстом и атрибутами.
 * @param context Контекст, в котором создаётся `MyPickerView`.
 * @param attrs Набор XML-атрибутов, используемых для настройки `MyPickerView`.
 */
class MyPickerView<Type> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : RecyclerView(context, attrs) {

    private lateinit var pickerViewAdapter: MyPickerViewAdapter<Type>
    private val snapHelper: SnapHelper
    private val textSize: Float

    /**
     * Устанавливает список объектов (`Type`) в `MyPickerView`.
     *
     * Эта функция принимает список элементов и обновляет адаптер.
     * Обновление выполняется в UI-потоке с использованием `post {}`.
     *
     * @param newData Список объектов (`Type`), которые будут установлены в `MyPickerView`.
     */
    fun setData(newData: List<Type>){
        this.post {
            pickerViewAdapter.setData(newData)
        }
    }

    init {

        // Получаем кастомные атрибуты. В данном случае это textSize у MyPickerView
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyPickerView)
        textSize = typedArray.getDimension(R.styleable.MyPickerView_textSize, 24f)
        typedArray.recycle()

        // Штука для вертикального расположения элементов
        this.layoutManager = LinearLayoutManager(context)

        // Помощник при прокрутке
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this)
        // Устанавливает кастомный ScrollListener
        this.addOnScrollListener(MyPickerViewScrollListener(context, snapHelper))

        // Полосы сверху и снизу MyPickerView
        val borderDecoration = MyPickerViewBorderItemDecorator(R.color.colorOnBackgroundLight, 3)
        this.addItemDecoration(borderDecoration)

        // После создания добавляем данные в адаптер.
        // Делаем мы это здесь, т.к. до создания MyPickerView мы не знаем его размеры
        // А его размер нам нужен определения размеров детей
        post {
            pickerViewAdapter = MyPickerViewAdapter(this, textSize)
            adapter = pickerViewAdapter
        }
    }

    /**
     * Получает текущий выбранный элемент `MyPickerView`.
     *
     * Функция определяет текущий элемент, основываясь на `SnapHelper` и `LinearLayoutManager`.
     * Если `snapView` находится в корректной позиции, возвращается соответствующий элемент адаптера.
     *
     * @return Текущий элемент списка (`Type`), или `null`, если элемент не найден.
     */
    fun getCurrentItem(): Type? {
        val layoutManager = this.layoutManager as? LinearLayoutManager ?: return null
        val snapView = snapHelper.findSnapView(layoutManager) ?: return null
        val position = this.getChildAdapterPosition(snapView)

        if (position != NO_POSITION)
            return pickerViewAdapter.getItemByPos(position)
        return null

    }


}
