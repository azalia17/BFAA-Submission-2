package com.azalia.bfaa_submission_2.ui.follower

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.azalia.bfaa_submission_2.R
import com.azalia.bfaa_submission_2.data.Resource
import com.azalia.bfaa_submission_2.databinding.FollowerFragmentBinding
import com.azalia.bfaa_submission_2.model.User
import com.azalia.bfaa_submission_2.ui.adapter.UserAdapter
import com.azalia.bfaa_submission_2.util.ViewStateCallback

class FollowerFragment : Fragment(), ViewStateCallback<List<User>> {

    private lateinit var followerBinding: FollowerFragmentBinding

    //private val followerBinding: FollowerFragmentBinding by viewBinding()
    private lateinit var viewModel: FollowerViewModel

    private lateinit var userAdapter: UserAdapter
    private var username: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(KEY_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.follower_fragment, container, false)
        followerBinding = FollowerFragmentBinding.inflate(inflater, container, false)
        return followerBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FollowerViewModel::class.java)
        userAdapter = UserAdapter()
        followerBinding.rvListFollow.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getUserFollowers(username.toString()).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Failure -> onFailed(it.message)
                is Resource.Loading -> onLoading()
                is Resource.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        })
    }

    override fun onSuccess(data: List<User>) {
        userAdapter.setAllData(data)
        followerBinding.apply {
            tvMessage.visibility = invisible
            followProgressBar.visibility = invisible
            rvListFollow.visibility = visible
        }
    }

    override fun onLoading() {
        followerBinding.apply {
            tvMessage.visibility = invisible
            followProgressBar.visibility = visible
            rvListFollow.visibility = invisible
        }
    }

    override fun onFailed(message: String?) {
        followerBinding.apply {
            if (message == null) {
                tvMessage.text = resources.getString(R.string.no_follower, username)
                tvMessage.visibility = visible
            } else {
                tvMessage.text = message
                tvMessage.visibility = visible
            }
            followProgressBar.visibility = invisible
            rvListFollow.visibility = invisible
        }
    }

    companion object {
        private const val KEY_BUNDLE = "USERNAME"

        fun getInstance(username: String): Fragment {
            return FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, username)
                }
            }
        }
    }
}


