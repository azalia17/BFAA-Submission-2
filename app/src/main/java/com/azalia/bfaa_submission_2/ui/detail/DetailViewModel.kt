package com.azalia.bfaa_submission_2.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azalia.bfaa_submission_2.data.Resource
import com.azalia.bfaa_submission_2.data.RetrofitService
import com.azalia.bfaa_submission_2.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private val retrofit = RetrofitService.create()
    private val user = MutableLiveData<Resource<User>>()

    fun getDetailUser(username: String?): LiveData<Resource<User>> {
        retrofit.getDetailUser(username!!).enqueue(object  : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val result = response.body()
                user.postValue(Resource.Success(result))
            }

            override fun onFailure(call: Call<User>, t: Throwable) {

            }
        })

        return user
    }
}