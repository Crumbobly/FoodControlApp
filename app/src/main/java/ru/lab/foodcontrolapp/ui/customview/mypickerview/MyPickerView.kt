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
class MyPickerView<Type> @JvmOverloads constructor(private val context: Context, attrs: AttributeSet? = null) :
    RecyclerView(context, attrs) {

    private var pickerViewAdapter: MyPickerViewAdapter<Type>
    private val snapHelper: SnapHelper = LinearSnapHelper()
    private lateinit var myScrollListener: MyPickerViewScrollListener<Type>
    private val textSize: Float

    /**
     * Устанавливает список объектов (`Type`) в `MyPickerView`.
     *
     * Эта функция принимает список элементов и обновляет адаптер.
     *
     * @param newData Список объектов (`Type`), которые будут установлены в `MyPickerView`.
     */
    fun setData(newData: List<Type>) {
        pickerViewAdapter.setData(newData)
    }


    init {

        // Получаем кастомные атрибуты. В данном случае это textSize у MyPickerView
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyPickerView)
        textSize = typedArray.getDimension(R.styleable.MyPickerView_textSize, 24f)
        typedArray.recycle()

        // Штука для вертикального расположения элементов
        this.layoutManager = MyLinerLayout(context)

        // Помощник при прокрутке
        snapHelper.attachToRecyclerView(this)
        // Устанавливает кастомный ScrollListener
        if (!isInEditMode) {
            myScrollListener = MyPickerViewScrollListener(context, snapHelper)
            this.addOnScrollListener(myScrollListener)
        }

        // Полосы сверху и снизу MyPickerView
        val borderDecoration = MyPickerViewBorderItemDecorator(R.color.colorOnBackgroundLight, 3)
        this.addItemDecoration(borderDecoration)

        pickerViewAdapter = MyPickerViewAdapter(this, textSize)
        adapter = pickerViewAdapter

    }

    fun setOnItemSelectedListener(listener: (Type) -> Unit) {
        myScrollListener.setScrollStateChangeCallback(listener)
    }

    /**
     * Устанавливает текущий элемент списка.
     *
     * @param item Элемент, который нужно выбрать.
     */
    fun setCurrentItem(item: Type?) {

        myScrollListener.disableSound()

        if (item != null) {
            this.stopScroll()
            val position = pickerViewAdapter.getPosByItem(item)
            (this.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(position - 1, 0)
        }

        post {
            myScrollListener.enableSound()
        }
    }

    fun setVisualEnabled(enabled: Boolean) {
        isEnabled = enabled
        isClickable = enabled
        isFocusable = enabled
        alpha = if (enabled) 1.0f else 0.5f
    }


}
