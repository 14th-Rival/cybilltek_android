package com.dev_enzo.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev_enzo.data.bean.UserInfo

@Database(entities = [UserInfo::class], version = 1)
abstract class DatabaseUtil : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao

    companion object {
        @Volatile
        private var instance: DatabaseUtil? = null

        /**
         * This function initializes the Room Database instance
         * @param context get the context from an Activity
         */
        fun getInstance(context: Context? = null): DatabaseUtil {
            if (this.instance == null) {
                this.instance = Room.databaseBuilder(
                    context!!.applicationContext,
                    DatabaseUtil::class.java,
                    "user_info_db"
                )
                    .build()
            }
            return this.instance!!
        }
    }
}

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM user_info")
    fun getPaginatedUserInfo(): PagingSource<Int, UserInfo>

    @Query("SELECT * FROM user_info")
    fun getAllPersons(): LiveData<List<UserInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userInfo: List<UserInfo>)

    @Query("DELETE FROM user_info")
    suspend fun deleteAll()
}