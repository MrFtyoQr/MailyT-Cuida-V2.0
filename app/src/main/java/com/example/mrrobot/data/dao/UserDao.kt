package com.example.mrrobot.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mrrobot.data.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    @Insert
    suspend fun insertUser(user: User)
} 