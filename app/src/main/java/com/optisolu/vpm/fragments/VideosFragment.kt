package com.optisolu.vpm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.optisolu.vpm.R
import com.optisolu.vpm.adapters.VideosRecyclerAdapter
import com.optisolu.vpm.base.BaseFragment
import com.optisolu.vpm.extensions.activityScopedFragmentViewModel
import com.optisolu.vpm.view_models.VideosViewModel
import org.kodein.di.generic.instance


class VideosFragment : BaseFragment() {

    companion object {
        private const val TAG = "VideosFragment"
    }

    private val videosViewModel: VideosViewModel by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_videos, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.videos_recycler_view)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val videosRecyclerAdapter = VideosRecyclerAdapter(requireContext())
        videosViewModel.videosData.observe(this, {
            videosRecyclerAdapter.submitData(lifecycle, it)
        })
        recyclerView.adapter = videosRecyclerAdapter
        return view
    }
}