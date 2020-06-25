package com.txwstudio.app.roadreport.ui.livecam

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.txwstudio.app.roadreport.R

class LiveCamFragment : Fragment() {

    companion object {
        fun newInstance() = LiveCamFragment()
    }

    private lateinit var viewModel: LiveCamViewModel
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.live_cam_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LiveCamViewModel::class.java)
        // TODO: Use the ViewModel
    }

}