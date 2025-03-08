package ru.lab.foodcontrolapp.ui.customview.mypickerview

import android.content.Context
import android.media.SoundPool
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import ru.lab.foodcontrolapp.R

/**
 * `RecyclerView.OnScrollListener` для `MyPickerView`, который:
 * - Подсвечивает текущий выбранный элемент.
 * - Уменьшает яркость остальных элементов.
 * - Воспроизводит звук при смене элемента.
 *
 * @param context Контекст для доступа к ресурсам.
 * @param snapHelper `SnapHelper` для определения центрального элемента.
 */
class MyPickerViewScrollListener<Type>(
    private val context: Context,
    private val snapHelper: SnapHelper
) :
    RecyclerView.OnScrollListener() {

    private var scrollStateChangeCallback: ((Type) -> Unit)? = null

    private var lastPosition = RecyclerView.NO_POSITION // Последняя выделенная позиция

    private val soundPool = SoundPool.Builder().setMaxStreams(1).build() // Объект для воспроизведения звуков
    private val soundId: Int = soundPool.load(context, R.raw.scroll_sound, 1) // Загрузка звука
    private var isInitialScroll = true // Флаг для пропуска первого проигрывания звука
    private var soundIsAllowed = true;


    fun setScrollStateChangeCallback(callback: (Type) -> Unit){
        scrollStateChangeCallback = callback
    }


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        val centerView = snapHelper.findSnapView(recyclerView.layoutManager) ?: return
        val position = recyclerView.getChildAdapterPosition(centerView)
        if (position != RecyclerView.NO_POSITION) {
            val adapter = recyclerView.adapter as MyPickerViewAdapter<*>
            val currentItem = adapter.getItemByPos(position) ?: return

            @Suppress("UNCHECKED_CAST")
            scrollStateChangeCallback?.invoke(currentItem as Type)
        }
    }

    /**
     * Вызывается при прокрутке `RecyclerView`.
     * Определяет центральный элемент, подсвечивает его и воспроизводит звук при смене.
     */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        // Определяем центральный элемент (если есть)
        val centerView = snapHelper.findSnapView(recyclerView.layoutManager) ?: return
        val position = recyclerView.getChildAdapterPosition(centerView)

        // Если позиция изменилась, обновляем подсветку и воспроизводим звук
        if (position != lastPosition && position != RecyclerView.NO_POSITION) {
            for (i in 0 until recyclerView.childCount) {
                // Сбрасываем выделение у всех элементов
                resetHighlight(context, recyclerView.getChildAt(i))
            }
            // Подсвечиваем центральный элемент
            highlight(context, centerView)
            lastPosition = position

            // Пропускаем проигрывание звука при первой прокрутке
            // При создании MyPickerView происходит однократная прокрутка, озвучку которой мы избегаем
            if (isInitialScroll) {
                isInitialScroll = false
                return
            }
            // Воспроизводим звук
            if (soundIsAllowed) {
                soundPool.play(soundId, 0.05f, 0.05f, 1, 0, 1f)
            }
        }
    }

    fun disableSound(){
        soundIsAllowed = false
    }

    fun enableSound(){
        soundIsAllowed = true
    }

}
