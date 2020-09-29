package com.example.myapplication.presenter.android

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_second.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class NewsDetailFragment : Fragment(R.layout.fragment_second) {
    val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.navigateToDetailEvent.value?.peekContent()?.let {
            Glide.with(imageView2).load(it.urlToImage).
            diskCacheStrategy(DiskCacheStrategy.DATA).
            centerCrop().into(imageView2);

            title.text = it.title
            body.text = it.content
        }
    }
}