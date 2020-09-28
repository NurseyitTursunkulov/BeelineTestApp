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
import com.google.android.material.snackbar.Snackbar
import io.aikosoft.alaket.util.EventObserver
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(R.layout.fragment_first) {

    val mainViewModel: MainViewModel by sharedViewModel()
    lateinit var listAdapter: NewsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = NewsAdapter()
        initRecyclerView()

            with(mainViewModel){
                showLoadingEvent.observe(viewLifecycleOwner, EventObserver{
                    progressBar.visibility =it.toVisibility()
                })
                showErrorEvent.observe(viewLifecycleOwner, EventObserver{
                    Snackbar.make(progressBar, it.toString(), Snackbar.LENGTH_SHORT).show()
                })
                displayNewsEvent.observe(viewLifecycleOwner, EventObserver{
                    listAdapter.submitList(it)
                })
            }

    }
}