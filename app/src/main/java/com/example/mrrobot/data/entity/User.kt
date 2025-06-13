package com.example.mrrobot.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val password: String,
    val name: String = "",
    val createdAt: Long = System.currentTimeMillis()
) 