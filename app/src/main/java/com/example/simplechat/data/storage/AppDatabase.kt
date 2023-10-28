package com.example.simplechat.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.simplechat.data.models.User
import com.example.simplechat.data.storage.converters.TimestampConverter
import com.example.simplechat.data.storage.dao.UserDao
import kotlinx.coroutines.CoroutineScope

@Database(entities = [User::class], version = 2)
@TypeConverters(TimestampConverter::class)
abstract class AppDatabase :RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(ctx: Context, scope: CoroutineScope): AppDatabase {
            return when (val temp = INSTANCE) {
                null -> synchronized(this) {
                    Room.databaseBuilder(
                        ctx.applicationContext, AppDatabase::class.java,
                        "app_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                else -> temp
            }
        }
    }
}