package com.example.comtacts

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}

// entities(데이터항목) user data 를 쓰고 처음쓸때 버전 1로 쓰겠다.
