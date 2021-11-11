package com.optisolu.vpm.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.optisolu.vpm.R
import com.optisolu.vpm.activities.MainActivity
import com.optisolu.vpm.adapters.FeedsRecyclerAdapter
import com.optisolu.vpm.base.BaseFragment
import com.optisolu.vpm.utils.Constants
import com.optisolu.vpm.view_models.FeedsViewModel
import org.kodein.di.generic.instance

class FeedsFragment : BaseFragment() {

    companion object {
        private const val TAG = "FeedsFragment"
    }

    private val feedsViewModel: FeedsViewModel by instance()

    private lateinit var feedsAdapter: FeedsRecyclerAdapter

    private lateinit var layoutView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feeds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutView = view

        val recyclerView = view.findViewById<RecyclerView>(R.id.feeds_recycler_view)
        feedsAdapter = FeedsRecyclerAdapter(requireActivity() as MainActivity)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = feedsAdapter
        }
        refreshFeeds(Constants.UPDATE)
    }

    internal fun refreshFeeds(action: String) {
        Log.i(TAG, "refreshFeeds: ")
        requireActivity().lifecycleScope.launchWhenStarted {
            feedsViewModel.getAllFeeds().observe(requireActivity(), {
                it?.let { feedsData ->
                    Log.i(TAG, "refreshFeeds: $feedsData")
                    val notFoundText = layoutView.findViewById<AppCompatTextView>(R.id.not_found_text)
                    feedsAdapter.updateData(feedsData)
                    if (feedsData.isEmpty()) {
                        notFoundText.visibility = View.VISIBLE
                    } else {
                        notFoundText.visibility = View.GONE
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
        refreshFeeds(Constants.UPDATE)
    }
}