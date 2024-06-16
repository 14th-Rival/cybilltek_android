package com.dev_enzo.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson

class HttpUtil(private val context: Context?) {

    companion object {
        private var instance: HttpUtil? = null

        fun getInstance(context: Context? = null): HttpUtil {
            if (instance == null) {
                instance = HttpUtil(context)
            }
            return instance!!
        }
    }

    /**
     * This function serves as a converter of POJO
     * data into JSON Element
     * @param obj pojo data to convert into JSON
     */
    fun gsonToJson(obj: Any?): String? {
        return Gson().toJson(obj)
    }

    /**
     * This function works as a checker whether there's
     * a network connectivity or none
     */
    fun isOnline(): Boolean {
        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}