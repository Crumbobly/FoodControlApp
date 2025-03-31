package ru.lab.foodcontrolapp.data.database

import android.content.Context
import android.content.res.XmlResourceParser
import ru.lab.foodcontrolapp.R
import ru.lab.foodcontrolapp.data.database.entity.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import java.util.Locale

object FakeFoodDatabase {
    private val foods = mutableListOf<Food>()

    fun loadFoodsFromXml(context: Context) {
        if (foods.isNotEmpty()) return

        val locale = Locale.getDefault().language

        val parser: XmlResourceParser = if (locale == "ru"){
            context.resources.getXml(R.xml.food_database_ru)
        } else{
            context.resources.getXml(R.xml.food_database)
        }

        var name = ""
        var type = ""
        var group = ""
        var calories = 0
        var fat = 0f
        var protein = 0f
        var carbs = 0f

        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            when (parser.eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "name" -> name = parser.nextText()
                        "type" -> type = parser.nextText()
                        "group" -> group = parser.nextText()
                        "calories" -> calories = parser.nextText().toInt()
                        "fat" -> fat = parser.nextText().toFloat()
                        "protein" -> protein = parser.nextText().toFloat()
                        "carbs" -> carbs = parser.nextText().toFloat()
                    }
                }
                XmlPullParser.END_TAG -> if (parser.name == "food") {
                    foods.add(Food(name, type, group, calories, fat, protein, carbs))
                }
            }
            parser.next()
        }
    }

    suspend fun searchFood(query: String): List<Food> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            foods.filter { it.name.contains(query, ignoreCase = true) }.take(10)
        }
    }
}
