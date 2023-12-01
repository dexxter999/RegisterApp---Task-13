package com.example.registerapp.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.registerapp.data.FieldItemSubListItem

class MainDiffCallBack : DiffUtil.ItemCallback<List<FieldItemSubListItem>>() {
    override fun areItemsTheSame(
        oldItem: List<FieldItemSubListItem>,
        newItem: List<FieldItemSubListItem>
    ) = oldItem.firstOrNull()?.fieldId == newItem.firstOrNull()?.fieldId

    override fun areContentsTheSame(
        oldItem: List<FieldItemSubListItem>,
        newItem: List<FieldItemSubListItem>
    ) = oldItem == newItem
}