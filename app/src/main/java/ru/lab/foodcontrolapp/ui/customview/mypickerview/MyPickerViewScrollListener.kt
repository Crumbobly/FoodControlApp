package ru.lab.foodcontrolapp.ui.customview.mypickerview

import android.content.Context
import android.media.SoundPool
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
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
class MyPickerViewScrollListener(private val context: Context, private val snapHelper: SnapHelper) :
    RecyclerView.OnScrollListener() {

    private var lastPosition = RecyclerView.NO_POSITION // Последняя выделенная позиция
    private val soundPool = SoundPool.Builder().setMaxStreams(1).build() // Объект для воспроизведения звуков
    private val soundId: Int = soundPool.load(context, R.raw.scroll_sound, 1) // Загрузка звука
    private var isInitialScroll = true // Флаг для пропуска первого проигрывания звука

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
                resetHighlight(recyclerView.getChildAt(i))
            }

            // Подсвечиваем центральный элемент
            highlight(centerView)
            lastPosition = position

            // Пропускаем проигрывание звука при первой прокрутке
            // При создании MyPickerView происходит однократная прокрутка, озвучку которой мы избегаем
            if (isInitialScroll) {
                isInitialScroll = false
                return
            }
            // Воспроизводим звук
            soundPool.play(soundId, 0.05f, 0.05f, 1, 0, 1f)
        }
    }

    /**
     * Подсвечивает переданный `view`, увеличивая его размер и изменяя цвет текста.
     *
     * @param view Виджет, который нужно подсветить (должен быть `TextView`).
     */
    private fun highlight(view: View) {
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
    private fun resetHighlight(view: View) {
        view.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(150)
            .start()

        val textView = view as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorOnBackgroundLight))
    }
}
