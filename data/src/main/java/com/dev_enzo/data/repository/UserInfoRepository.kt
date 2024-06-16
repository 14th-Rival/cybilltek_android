package com.dev_enzo.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dev_enzo.data.bean.UserInfo
import kotlinx.coroutines.flow.Flow

class UserInfoRepository(private val userInfoDao: UserInfoDao, private var userInfoList: List<UserInfo>)  {
    /**
     * This variable works as an alternative when the data
     * is not available online, it is used when there's no
     * network connection
     */
    val mUserInfoList: LiveData<List<UserInfo>> = userInfoDao.getAllPersons()

    /**
     * This function is used as an alternative when there's
     * no network connection, it uses pagination library
     */
    fun getPaginatedUserInfo(): Flow<PagingData<UserInfo>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { userInfoDao.getPaginatedUserInfo() }
        ).flow
    }

    /**
     * This function deletes and replace
     * the current existing database
     */
    suspend fun refreshUserInfo() {
        userInfoDao.deleteAll()
        userInfoDao.insertAll(userInfoList)
    }

    /**
     * This function adds a new set of data
     * to the current existing one (database)
     */
    suspend fun addUserInfo() {
        userInfoDao.insertAll(userInfoList)
    }
}