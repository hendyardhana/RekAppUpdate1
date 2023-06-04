package com.example.rekapp.util

import android.content.Context
import androidx.room.Room
import com.example.rekapp.model.RekAppDb
import java.text.NumberFormat
import java.util.*

val DB_NAME = "rekappdb"

fun buildDb(context: Context): RekAppDb = Room.databaseBuilder(context, RekAppDb::class.java, DB_NAME).build()

fun formatUang(uang:Double): String {
    return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(uang).toString()
}