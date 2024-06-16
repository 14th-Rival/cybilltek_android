package com.dev_enzo.cybilltekapplication

import android.os.Bundle
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dev_enzo.cybilltekapplication.adapters.UserInfoAdapter
import com.dev_enzo.cybilltekapplication.base.BaseActivity
import com.dev_enzo.cybilltekapplication.viewmodels.UserInfoViewModel
import com.dev_enzo.data.network.HttpUtil
import com.dev_enzo.domain.GlobalConstants.Companion.GC_PAGE_SIZE
import com.dev_enzo.domain.GlobalConstants.Companion.TAG_MAIN_ACTIVITY
import com.dev_enzo.data.bean.UserInfo


class MainActivity : BaseActivity() {

    private val userInfoViewModel: UserInfoViewModel by lazy {
        ViewModelProvider(this)[UserInfoViewModel::class.java]
    }

    private lateinit var srlUserInfo: SwipeRefreshLayout
    private lateinit var recUserInfo: RecyclerView
    private lateinit var userInfoAdapter: UserInfoAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var initPage = 1
    private var curPage = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initSwipeRefreshLayout()
        initRecyclerView()
        observeUserInfoData()
        if (HttpUtil.getInstance().isOnline()) {
            loadUserInfoData(initPage)
        }
    }

    /**
     * Start the instance of the swipeRefreshLayout
     */
    private fun initSwipeRefreshLayout() {
        srlUserInfo = findViewById(R.id.srl_user_info)
        srlUserInfo.setOnRefreshListener {
            userInfoViewModel.refreshUserInfo()
            if (HttpUtil.getInstance().isOnline()) {
                initPage = 1
                loadUserInfoData(initPage)
            } else {
                if (srlUserInfo.isRefreshing) {
                    srlUserInfo.isRefreshing = false
                }
            }
            initRecyclerView()
        }
    }

    /**
     * Start the instance of the RecyclerView and
     * its other incorporated elements like LayoutManager and Adapter
     */
    private fun initRecyclerView() {
        recUserInfo = findViewById(R.id.rec_user_info)
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recUserInfo.layoutManager = layoutManager
        userInfoAdapter = UserInfoAdapter()
        recUserInfo.adapter = userInfoAdapter

        recUserInfo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= GC_PAGE_SIZE
                ) {
                    if (initPage != curPage) {
                        if (HttpUtil.getInstance().isOnline()) {
                            isLoading = true
                            loadUserInfoData(initPage)
                        } else {
                            Log.i(TAG_MAIN_ACTIVITY, "Device is offline")
                        }
                    }
                }
            }
        })
    }

    /**
     * This function tells the viewModel to load a
     * new set of data from the api response
     */
    private fun loadUserInfoData(page: Int) {
        userInfoViewModel.getUserInfo(page)
        curPage = page
        initPage += 1
        if (srlUserInfo.isRefreshing) {
            srlUserInfo.isRefreshing = false
        }
    }

    /**
     * This function serves as the observers if there are
     * changes with the current existing data and will update the UI later
     * if there's any
     */
    private fun observeUserInfoData() {
        userInfoViewModel.getListUserInfo.observe(this) { data ->
            if (HttpUtil.getInstance().isOnline()) {
                Log.i(TAG_MAIN_ACTIVITY, "Device is online")
                userInfoAdapter.addItems(data as List<UserInfo>)
                userInfoViewModel.addUserInfos()
                isLoading = false
            }
        }
        userInfoViewModel.getListUserInfoOffLineAll.observe(this) { data ->
            if (!HttpUtil.getInstance().isOnline()) {
                Log.i(TAG_MAIN_ACTIVITY, "Device is offline")
                val curUserInfoList = (data as List<UserInfo>)
                val curListSize = curUserInfoList.size
                curPage = (curListSize / GC_PAGE_SIZE)
                initPage = (curPage+1)
                isLoading = false
                userInfoAdapter.addItems(curUserInfoList)
            }
        }
    }
}