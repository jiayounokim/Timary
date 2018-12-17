package com.github.ojh102.timary.ui.main.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.ojh102.timary.R
import com.github.ojh102.timary.databinding.ViewCapsuleCloseBinding
import com.github.ojh102.timary.model.realm.Capsule
import com.github.ojh102.timary.util.TimaryParser
import com.github.ojh102.timary.util.extension.toast

class ClosedCapsuleViewHolder(
        private val binding: ViewCapsuleCloseBinding,
        timaryParser: TimaryParser
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.timaryParser = timaryParser
    }

    fun bind(item: Capsule) {
        binding.capsule = item
    }

    fun setOnClickListener(clickListener: View.OnClickListener) {
        binding.capsuleClickListener = clickListener
    }

}