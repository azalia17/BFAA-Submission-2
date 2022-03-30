package com.azalia.bfaa_submission_2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azalia.bfaa_submission_2.data.ApiService
import com.azalia.bfaa_submission_2.data.Resource
import com.azalia.bfaa_submission_2.data.RetrofitService
import com.azalia.bfaa_submission_2.model.SearchResponse
import com.azalia.bfaa_submission_2.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val retrofit: ApiService = RetrofitService.create()
    private val listUser = MutableLiveData<Resource<List<User>>>()

    fun searchUser(query: String): LiveData<Resource<List<User>>> {
        listUser.postValue(Resource.Loading())
        retrofit.searchUsers(query).enqueue(object  : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val list = response.body()?.items
                if (list.isNullOrEmpty())
                    listUser.postValue(Resource.Failure(null))
                else
                    listUser.postValue(Resource.Success(list))
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                listUser.postValue(Resource.Failure(t.message))
            }
        })
        return listUser
    }
}