package com.example.rekapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Wallet::class, Transaction::class], version =  1)
abstract class RekAppDb: RoomDatabase() {
    abstract fun RekAppDao(): RekAppDao

    companion object {
        @Volatile private var instance: RekAppDb ?= null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, RekAppDb::class.java, "rekappdb").build()


        operator fun invoke(context: Context) {
            if(instance != null) {
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}