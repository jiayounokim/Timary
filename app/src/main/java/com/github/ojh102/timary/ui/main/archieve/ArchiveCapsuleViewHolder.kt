package com.github.ojh102.timary.ui.main.archieve

import androidx.recyclerview.widget.RecyclerView
import com.github.ojh102.timary.databinding.ViewCapsuleArchiveBinding
import com.github.ojh102.timary.model.realm.Capsule
import com.github.ojh102.timary.util.TimaryParser

class ArchiveCapsuleViewHolder(
        private val binding: ViewCapsuleArchiveBinding,
        timaryParser: TimaryParser

) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.timaryParser = timaryParser
    }

    fun bind(item: Capsule) {
        binding.capsule = item
    }

}