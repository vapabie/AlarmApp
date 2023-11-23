package com.example.alarmapp.data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "time")
    val alarmTime: LocalDateTime,
    @ColumnInfo(name = "message")
    val alarmMessage: String,
    @ColumnInfo(name ="sound")
    val alarmSound: Uri
)