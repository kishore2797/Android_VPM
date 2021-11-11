package com.optisolu.vpm.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.optisolu.vpm.R
import com.optisolu.vpm.databinding.ActivityMainBinding
import com.optisolu.vpm.entities.FeedEntity
import com.optisolu.vpm.extensions.showToast
import com.optisolu.vpm.factories.FeedsViewModelFactory
import com.optisolu.vpm.fragments.dialogs.FeedInsertDialogFragment
import com.optisolu.vpm.fragments.FeedsFragment
import com.optisolu.vpm.fragments.VideosFragment
import com.optisolu.vpm.utils.Constants
import com.optisolu.vpm.view_models.FeedsViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivityMainBinding

    override val kodein by kodein()

    private lateinit var feedsViewModel: FeedsViewModel
    private val feedsViewModelFactory: FeedsViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        feedsViewModel =
            ViewModelProvider(this, feedsViewModelFactory)[FeedsViewModel::class.java]

        supportFragmentManager.findFragmentByTag(Constants.FEEDS_FRAGMENT)?.let {
            updateTabs(Constants.FEEDS_FRAGMENT)
        }

        supportFragmentManager.findFragmentByTag(Constants.VIDEOS_FRAGMENT)?.let {
            updateTabs(Constants.VIDEOS_FRAGMENT)
        }

        binding.videosTabButton.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentByTag(Constants.VIDEOS_FRAGMENT)
            if (fragment == null) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_fragment_container,
                        VideosFragment(),
                        Constants.VIDEOS_FRAGMENT
                    )
                    .commit()
                updateTabs(Constants.VIDEOS_FRAGMENT)
            }
        }

        binding.feedsTabButton.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentByTag(Constants.FEEDS_FRAGMENT)
            if (fragment == null) {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.main_fragment_container,
                        FeedsFragment(),
                        Constants.FEEDS_FRAGMENT
                    )
                    .commit()
                updateTabs(Constants.FEEDS_FRAGMENT)
            }
        }

        binding.mainFloatingButton.setOnClickListener {
            val bottomSheetDialog = FeedInsertDialogFragment(null)
            bottomSheetDialog.isCancelable = false
            bottomSheetDialog.show(supportFragmentManager, "FeedInsertDialogFragment")
        }
    }

    internal fun insertNewFeed(feedEntity: FeedEntity) {
        feedsViewModel.insertFeed(feedEntity)
        showToast("New feed has been created successfully!")
    }

    internal fun updateExistingFeed(feedEntity: FeedEntity) {
        feedsViewModel.updateFeed(feedEntity)
        showToast("Feed has been updated successfully!")
    }

    internal fun deleteExistingFeed(feedEntity: FeedEntity) {
        feedsViewModel.deleteFeed(feedEntity)
        showToast("Feed has been deleted successfully!")
        supportFragmentManager.findFragmentByTag("FeedInsertDialogFragment")?.let { dialogFragment ->
            (dialogFragment as BottomSheetDialogFragment).dismiss()
        }
        supportFragmentManager.findFragmentByTag(Constants.FEEDS_FRAGMENT)?.let { fragment ->
            (fragment as FeedsFragment).refreshFeeds(Constants.DELETE)
        }
    }

    private fun updateTabs(tag: String) {
        if (tag == Constants.VIDEOS_FRAGMENT) {
            binding.feedsTabButton.setBackgroundResource(0)
            binding.feedsTabButton.setTextColor(Color.parseColor("#858585"))
            binding.videosTabButton.background =
                ContextCompat.getDrawable(this, R.drawable.secondary_rounded_bg)
            binding.videosTabButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.mainFloatingButton.visibility = View.GONE
        } else if (tag == Constants.FEEDS_FRAGMENT) {
            binding.videosTabButton.setBackgroundResource(0)
            binding.videosTabButton.setTextColor(Color.parseColor("#858585"))
            binding.feedsTabButton.background =
                ContextCompat.getDrawable(this, R.drawable.secondary_rounded_bg)
            binding.feedsTabButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.mainFloatingButton.visibility = View.VISIBLE
        }
    }
}