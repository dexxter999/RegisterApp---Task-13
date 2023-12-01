package com.example.registerapp.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.registerapp.data.FieldItemSubListItem

class ItemDiffCallback : DiffUtil.ItemCallback<FieldItemSubListItem>() {
    override fun areItemsTheSame(
        oldItem: FieldItemSubListItem,
        newItem: FieldItemSubListItem
    ) = oldItem.fieldId == newItem.fieldId

    override fun areContentsTheSame(
        oldItem: FieldItemSubListItem,
        newItem: FieldItemSubListItem
    ) = oldItem == newItem
}
