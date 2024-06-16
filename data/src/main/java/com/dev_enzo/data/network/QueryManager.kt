package com.dev_enzo.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This data class will serve as a standard point when receiving the data from the response callback
 * @param results returns the results value from the response callback
 * @param info returns the info value from the response callback
 */
data class QueryResult(val results: Any?, val info: Any?)

interface QueryManager {
    /**
     * Send a get request with multiple users, no pagination
     * @param results number of users
     */
    @GET("?")
    fun reqMultiUser(@Query("results") results: Int?): Call<QueryResult>

    /**
     * Send a get request with current page and page size
     * @param page current query page
     * @param results number of users
     * @param seed set of users
     */
    @GET("?")
    fun pageListUser(
        @Query("page") page: Int?,
        @Query("results") results: Int? = 10,
        @Query("seed") seed: String? = "abc"): Call<QueryResult>
}