package com.example.myapplication.presenter.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.app.EndlessRecyclerViewScrollListener
import io.aikosoft.alaket.util.EventObserver
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    val mainViewModel: MainViewModel by sharedViewModel()
    lateinit var listAdapter: NewsAdapter
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = NewsAdapter()
        news_list.apply {
            adapter = listAdapter
            val llm = LinearLayoutManager(requireContext())
            layoutManager = llm
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            addOnScrollListener(object: EndlessRecyclerViewScrollListener(llm){
//                override fun loadMoreItems() {
//                    mainViewModel.getMore(page)
//                }

                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    mainViewModel.getNextPage(page)
                }


            })
        }

            with(mainViewModel){
                showLoadingEvent.observe(viewLifecycleOwner, EventObserver{
                    progressBar.visibility =it.toVisibility()
                })
                showErrorEvent  .observe(viewLifecycleOwner, EventObserver{
                    textview_first.text = it.toString()
                })
                displayNewsEvent.observe(viewLifecycleOwner, EventObserver{
                    listAdapter.submitList(it)
                })
            }

    }
    fun Boolean?.toVisibility(): Int = if (this == true) View.VISIBLE else View.GONE
}