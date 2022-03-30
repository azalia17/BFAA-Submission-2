package com.azalia.bfaa_submission_2.data

import com.azalia.bfaa_submission_2.model.SearchResponse
import com.azalia.bfaa_submission_2.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun searchUsers (
        @Query("q")
        query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser (
        @Path("username")
        username: String
    ): Call<User>

    @GET("users/{username}/followers")
    fun getUserFollowers (
        @Path("username")
        username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getUserFollowing (
        @Path("username")
        username: String
    ): Call<List<User>>
}