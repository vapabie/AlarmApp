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
    val alarmSound: String
)

@Entity
data class Sound(
    @PrimaryKey(autoGenerate = true)
    val soundId: Int = 0,
    @ColumnInfo(name ="url")
    val soundUrl: String,
    @ColumnInfo(name ="title")
    val soundTitle: String,
    @ColumnInfo(name ="thumbnail")
    val SoundThumbnal: String
)