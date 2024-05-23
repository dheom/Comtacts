package com.example.comtacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User( //data를 위한 class로 copy가 가능한 class
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,//uid를 기본 key로 쓴다 -> unique ID
    @ColumnInfo(name = "lastname") val userName: String?,
    @ColumnInfo(name = "phonenum") val phoneNum: String?,
    @ColumnInfo(name = "email") val email: String?
)