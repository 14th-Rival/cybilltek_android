package com.dev_enzo.cybilltekapplication.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dev_enzo.data.network.HttpClient
import com.dev_enzo.data.network.HttpUtil
import com.dev_enzo.data.network.QueryResult
import com.dev_enzo.data.repository.DatabaseUtil
import com.dev_enzo.data.repository.UserInfoRepository
import com.dev_enzo.domain.GlobalConstants.Companion.GC_PAGE_SIZE
import com.dev_enzo.domain.GlobalConstants.Companion.TAG_USER_INFO
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_CELL
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_DOB
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_EMAIL
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_GENDER
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_ID
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_LOC
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_LOGIN
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_NAME
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_NAT
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_PHONE
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_PIC
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_REG
import com.dev_enzo.data.bean.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoViewModel : ViewModel() {
    private var mCurUserInfo: ArrayList<UserInfo> = ArrayList()
    // Database
    private val userInfoDao = DatabaseUtil.getInstance().userInfoDao()
    private var userInfoRepository = UserInfoRepository(userInfoDao, mCurUserInfo)
    // Live Data
    private var mUserInfo: MutableLiveData<List<UserInfo>> = MutableLiveData<List<UserInfo>>()
    val getListUserInfo: LiveData<List<UserInfo>>
        get() { return mUserInfo }
    val getListUserInfoOffLineAll: LiveData<List<UserInfo>>
        get() { return userInfoRepository.mUserInfoList }
    val getListUserInfoOffLinePaged: Flow<PagingData<UserInfo>> =
        userInfoRepository.getPaginatedUserInfo().cachedIn(viewModelScope)

    /**
     * Send a get request from the api and save the response to the arraylist
     * @param page current page
     * @param count page size
     * @param seed set of users
     */
    fun getUserInfo(page: Int, count: Int = GC_PAGE_SIZE, seed: String? = "abc") {
        if (HttpUtil.getInstance().isOnline()) {
            HttpClient.getInstance().reqUserPagination(page, count, seed).enqueue(object : Callback<QueryResult>{
                override fun onResponse(p0: Call<QueryResult>, p1: Response<QueryResult>) {
                    if (p1.isSuccessful) {
                        if (mCurUserInfo.size > 0) {
                            mCurUserInfo.clear()
                        }
                        val data = HttpUtil.getInstance().gsonToJson(p1.body()!!.results)
                        val curArr = JSONArray(data)
                        for (i in 0 until curArr.length()) {
                            val curInfo: JSONObject = curArr.get(i) as JSONObject
                            mCurUserInfo.add(
                                UserInfo(
                                curInfo.getString(USER_INFO_GENDER),
                                curInfo.getString(USER_INFO_NAME),
                                curInfo.getString(USER_INFO_LOC),
                                curInfo.getString(USER_INFO_EMAIL),
                                curInfo.getString(USER_INFO_LOGIN),
                                curInfo.getString(USER_INFO_DOB),
                                curInfo.getString(USER_INFO_REG),
                                curInfo.getString(USER_INFO_PHONE),
                                curInfo.getString(USER_INFO_CELL),
                                curInfo.getString(USER_INFO_ID),
                                curInfo.getString(USER_INFO_PIC),
                                curInfo.getString(USER_INFO_NAT)
                            )
                            )
                        }
                        mUserInfo.postValue(mCurUserInfo)
                        Log.i(TAG_USER_INFO, "Connection Successful")
                    }
                }
                override fun onFailure(p0: Call<QueryResult>, p1: Throwable) {
                    Log.e(TAG_USER_INFO, p1.message!!)
                }
            })
        } else {
            Log.i(TAG_USER_INFO, "Device offline switching to Database mode")
        }
    }

    /**
     * This will delete the cache data and will be
     * replaced by a new set of data from the api response
     */
    fun refreshUserInfo() {
        viewModelScope.launch {
            if (mCurUserInfo.size > 0) {
                mCurUserInfo.clear()
            }
            userInfoRepository.refreshUserInfo()
        }
    }

    /**
     * This will add a new set of data to the
     * current existing from the api response
     */
    fun addUserInfos() {
        viewModelScope.launch {
            userInfoRepository.addUserInfo()
        }
    }
}