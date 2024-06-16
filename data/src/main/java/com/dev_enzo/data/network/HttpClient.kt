package com.dev_enzo.data.network

import com.dev_enzo.domain.GlobalConstants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClient {

    companion object {
        private var instance: HttpClient? = null

        fun getInstance(): HttpClient {
            if (this.instance == null) {
                this.instance = HttpClient()
            }
            return this.instance!!
        }
    }

    /**
     * This function serves as the building block
     * for making a HTTP request for the API
     */
    private fun buildHttpRequest(): QueryManager {
        val retroFit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retroFit.create(QueryManager::class.java)
    }

    /**
     * This function sends an API request to get multiple user accounts
     * @param userCount you can input any amount ex: 5000
     */
    fun reqMultiplUser(userCount: Int): Call<QueryResult> {
        return buildHttpRequest().reqMultiUser(userCount)
    }

    /**
     * This function send an API request to get a paginated set of user accounts
     * @param page current page
     * @param usercount count of user according to page
     * @param seed set of users
     */
    fun reqUserPagination(page: Int, usercount: Int, seed: String? = "abc"): Call<QueryResult> {
        return buildHttpRequest().pageListUser(page, usercount, seed)
    }
}


