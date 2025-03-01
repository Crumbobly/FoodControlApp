package ru.lab.foodcontrolapp.data.database

import android.content.Context
import android.util.Log
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.lab.foodcontrolapp.data.database.dao.UserDao
import ru.lab.foodcontrolapp.data.database.entity.User
import java.util.concurrent.Executors

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @OptIn(DelicateCoroutinesApi::class)
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "food_control_db")
                        .fallbackToDestructiveMigration()
                        .addCallback(object: Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                GlobalScope.launch(Dispatchers.IO) {
                                    getInstance(context).getUserDao().insertUser(User())
                                }
                            }
                        })
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}