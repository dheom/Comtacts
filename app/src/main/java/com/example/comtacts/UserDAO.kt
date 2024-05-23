package com.example.comtacts

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao // Data annotation object
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>> //모든걸 가져온다는뜻 entity의 User의 모든 정보를 가져온다는 뜻 //Room

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): Flow<List<User>>

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}
//Symbol을 이용한 complie 해주는것 ksp를 추가한 이유 -> Delete만 적어도 내부 동작을 다해줌