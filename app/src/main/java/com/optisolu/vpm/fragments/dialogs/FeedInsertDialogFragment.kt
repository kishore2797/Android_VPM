package com.optisolu.vpm.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.optisolu.vpm.R
import com.optisolu.vpm.activities.MainActivity
import com.optisolu.vpm.entities.FeedEntity
import com.optisolu.vpm.extensions.showToast
import java.text.DateFormat
import java.util.*

class FeedInsertDialogFragment(private val feedEntity: FeedEntity?) : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.RoundedSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_feed_insert_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val roomEditText = view.findViewById<EditText>(R.id.room_name_edit_text)
        val isLiveCheckBox = view.findViewById<CheckBox>(R.id.room_live_check_box)
        val deleteFeedBtn = view.findViewById<TextView>(R.id.feed_delete_btn)
        val createFeedBtn = view.findViewById<TextView>(R.id.feed_insert_create)

        deleteFeedBtn.visibility = View.GONE

        if (feedEntity != null) {
            roomEditText.setText(feedEntity.name)
            feedEntity.isLive?.let {
                isLiveCheckBox.isChecked = it
            }
            deleteFeedBtn.visibility = View.VISIBLE

            deleteFeedBtn.setOnClickListener {
                (requireActivity() as MainActivity).deleteExistingFeed(feedEntity)
            }
            createFeedBtn.text = "Update"
        } else {
            createFeedBtn.text = "Create"
        }

        view.findViewById<TextView>(R.id.feed_insert_cancel).setOnClickListener {
            dismiss()
        }

        createFeedBtn.setOnClickListener {
            val textString = roomEditText.text.toString()
            if (textString.isEmpty() || textString.isBlank()) {
                requireContext().showToast("Please enter valid room name")
            } else {
                if (feedEntity == null || feedEntity.id < 0) {
                    (requireActivity() as MainActivity).insertNewFeed(
                        FeedEntity(
                            textString,
                            isLiveCheckBox.isChecked,
                            System.currentTimeMillis()
                        )
                    )
                } else {
                    (requireActivity() as MainActivity).updateExistingFeed(
                        FeedEntity(
                            feedEntity.id,
                            textString,
                            isLiveCheckBox.isChecked,
                            System.currentTimeMillis()
                        )
                    )
                }
                dismiss()
            }
        }
    }
}