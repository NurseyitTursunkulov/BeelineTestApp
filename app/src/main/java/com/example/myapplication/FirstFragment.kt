package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import io.aikosoft.alaket.util.EventObserver
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        with(mainViewModel){
            showLoadingEvent.observe(viewLifecycleOwner, EventObserver{
                progressBar.visibility =it.toVisibility()
            })
            showErrorEvent  .observe(viewLifecycleOwner, EventObserver{
                textview_first.text = it.toString()
            })
            displayNewsEvent.observe(viewLifecycleOwner, EventObserver{
                textview_first.text = it.toString()
            })
        }
    }
    fun Boolean?.toVisibility(): Int = if (this == true) View.VISIBLE else View.GONE
}