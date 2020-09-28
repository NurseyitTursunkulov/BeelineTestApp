package com.example.myapplication.presenter.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.app.EndlessRecyclerViewScrollListener
import com.google.android.material.snackbar.Snackbar
import io.aikosoft.alaket.util.EventObserver
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FirstFragment : Fragment(R.layout.fragment_first) {

    val mainViewModel: MainViewModel by sharedViewModel()
    lateinit var listAdapter: NewsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = NewsAdapter(mainViewModel)
        initRecyclerView()
        refresh_layout.setOnRefreshListener {
            mainViewModel.getNews(shouldUpdate = true)
        }

            with(mainViewModel){
                showLoadingEvent.observe(viewLifecycleOwner, EventObserver{
                    refresh_layout.isRefreshing = it
                })
                showErrorEvent.observe(viewLifecycleOwner, EventObserver{
                    Snackbar.make(progressBar, it.toString(), Snackbar.LENGTH_SHORT).show()
                })
                displayNewsEvent.observe(viewLifecycleOwner, EventObserver{
                    listAdapter.notifyDataSetChanged()/***known bug of rec view : https://stackoverflow.com/questions/35653439/recycler-view-inconsistency-detected-invalid-view-holder-adapter-positionviewh*/
                    listAdapter.submitList(it)
                })

                /*** this is for screen rotation*/
                displayNewsEvent.value?.peekContent()?.let {
                    listAdapter.submitList(it)
                }
                navigateToDetailEvent.observe(viewLifecycleOwner, EventObserver{
                    findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment())
                })
            }

    }
}