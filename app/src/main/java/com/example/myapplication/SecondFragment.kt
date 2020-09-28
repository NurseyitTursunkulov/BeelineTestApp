package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.news_item.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(R.layout.fragment_second) {
    val mainViewModel: MainViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.navigateToDetailEvent.value?.peekContent()?.let {
            Glide.with(imageView2).load(it.urlToImage).
            diskCacheStrategy(DiskCacheStrategy.DATA).
            centerCrop().into( imageView2);

            title.text = it.title
            body.text = it.content
        }
//        view.findViewById<Button>(R.id.button_second).setOnClickListener {
////            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }
}