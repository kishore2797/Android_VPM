package com.optisolu.vpm.adapters

import android.view.View
import com.optisolu.vpm.R
import com.optisolu.vpm.activities.MainActivity
import com.optisolu.vpm.base.BaseRvAdapter
import com.optisolu.vpm.databinding.FeedsRecyclerLayoutItemBinding
import com.optisolu.vpm.entities.FeedEntity
import com.optisolu.vpm.extensions.formatTo
import com.optisolu.vpm.extensions.toDate
import com.optisolu.vpm.extensions.toDateString
import com.optisolu.vpm.fragments.dialogs.FeedInsertDialogFragment

class FeedsRecyclerAdapter(private val activity: MainActivity): BaseRvAdapter<FeedEntity, FeedsRecyclerLayoutItemBinding>() {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.feeds_recycler_layout_item
    }

    override fun bind(binding: FeedsRecyclerLayoutItemBinding, item: FeedEntity) {
        binding.roomNameTextView.text = item.name
        item.isLive?.let {
            if (it) {
                binding.liveTextView.visibility = View.VISIBLE
            } else {
                binding.liveTextView.visibility = View.GONE
            }
        }
        binding.roomCreatedTextView.text = item.createdDateTime?.toDateString()
        binding.feedLayout.setOnClickListener {
            val bottomSheetDialog = FeedInsertDialogFragment(item)
            bottomSheetDialog.isCancelable = false
            bottomSheetDialog.show(activity.supportFragmentManager, "FeedInsertDialogFragment")
        }
    }
}