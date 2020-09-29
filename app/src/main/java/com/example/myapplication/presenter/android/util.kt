package com.example.myapplication.presenter.android

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.app.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_first.*


fun NewsFragment.initRecyclerView() {
    news_list.apply {
        adapter = listAdapter
        val llm = LinearLayoutManager(requireContext())
        layoutManager = llm
        addOnScrollListener(object : EndlessRecyclerViewScrollListener(llm) {

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                mainViewModel.getNextPage(page)
            }


        })
    }
}

fun Boolean?.toVisibility(): Int = if (this == true) View.VISIBLE else View.GONE
