package com.azalia.bfaa_submission_2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.PixelCopy.request
import androidx.lifecycle.ViewModelProvider
import com.azalia.bfaa_submission_2.data.Resource
import com.azalia.bfaa_submission_2.databinding.ActivityDetailBinding
import com.azalia.bfaa_submission_2.model.User
import com.azalia.bfaa_submission_2.ui.adapter.FollowPagerAdapter
import com.azalia.bfaa_submission_2.util.Constanta.EXTRA_USER
import com.azalia.bfaa_submission_2.util.Constanta.TAB_TITLES
import com.azalia.bfaa_submission_2.util.ViewStateCallback
import com.bumptech.glide.*
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity(), ViewStateCallback<User?> {

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            elevation = 0f
        }

        val username = intent.getStringExtra(EXTRA_USER)

        viewModel.getDetailUser(username).observe(this, {
            when (it) {
                is Resource.Failure -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it.data)
            }
        })

        val pagerAdapter = FollowPagerAdapter(this, username.toString())

        detailBinding.apply {
            viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabs, viewPager) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onSuccess(data: User?) {
        detailBinding.apply {
            tvTotalRepo.text = data?.repository.toString()
            tvTotalFollower.text = data?.follower.toString()
            tvTotalFollowing.text = data?.following.toString()
            tvNameDetail.text = data?.name
            tvUsernameDetail.text = data?.username
            tvCompanyDetail.text = data?.company
            tvLocationDetail.text = data?.location
        }

        Glide.with(this)
            .load(data?.avatar)
            .apply(RequestOptions.circleCropTransform())
            .into(detailBinding.ivAvatar)

        supportActionBar?.title = data?.username

    }

    override fun onLoading() {
        TODO("Not yet implemented")
    }

    override fun onFailed(message: String?) {
        TODO("Not yet implemented")
    }
}