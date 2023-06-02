package com.example.rekapp.util

import android.content.Context
import androidx.room.Room
import com.example.rekapp.model.RekAppDb

val DB_NAME = "rekappdb"

fun buildDb(context: Context): RekAppDb = Room.databaseBuilder(context, RekAppDb::class.java, DB_NAME).build()