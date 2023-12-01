package com.example.registerapp.adapters

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.registerapp.data.FieldItemSubListItem
import com.example.registerapp.databinding.NestedRecyclerviewItemChooserBinding
import com.example.registerapp.databinding.NestedRecyclerviewItemInputBinding
import com.example.registerapp.data.FieldType

class NestedRecyclerViewAdapter :
    ListAdapter<FieldItemSubListItem, RecyclerView.ViewHolder>(ItemDiffCallback()) {

    private var onChooserItemClick: ((NestedItem) -> Unit) = { }

    inner class InputViewHolder(private val binding: NestedRecyclerviewItemInputBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FieldItemSubListItem, isLastPosition: Boolean) = with(binding) {
            root.id = item.fieldId
            root.hint = item.hint
            root.isActivated = item.isActive
            root.inputType = inputType(item.keyboard)
            if (isLastPosition) {
                etInput.setBackgroundResource(android.R.color.transparent)
            }
            root.setOnClickListener { onChooserItemClick(NestedItem.TextField) }
        }
    }

    inner class ChooserViewHolder(private val binding: NestedRecyclerviewItemChooserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FieldItemSubListItem, isLastPosition: Boolean) = with(binding) {
            root.id = item.fieldId
            root.hint = item.hint
            root.isActivated = item.isActive
            root.inputType = inputType(item.keyboard)
            if (isLastPosition) {
                etChooser.setBackgroundResource(android.R.color.transparent)
            }
            root.setOnClickListener {
                when (item.hint) {
                    "Birthday" -> root.setOnClickListener {
                        onChooserItemClick(
                            NestedItem.DatePicker(
                                binding.etChooser
                            )
                        )
                    }

                    "Gender" -> root.setOnClickListener {
                        onChooserItemClick(
                            NestedItem.GenderPicker(
                                binding.etChooser
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FieldType.INPUT -> InputViewHolder(
                NestedRecyclerviewItemInputBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> ChooserViewHolder(
                NestedRecyclerviewItemChooserBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is InputViewHolder -> {
                val isLastPosition = position == itemCount - 1
                holder.bind(getItem(position), isLastPosition)
            }

            is ChooserViewHolder -> {
                val isLastPosition = position == itemCount - 1
                holder.bind(getItem(position), isLastPosition)
            }
        }
    }

    private fun inputType(value: String): Int =
        if (value == "number") InputType.TYPE_CLASS_NUMBER else InputType.TYPE_CLASS_TEXT

    override fun getItemViewType(position: Int) =
        if (getItem(position).fieldType == "input") FieldType.INPUT else FieldType.CHOOSER

    fun onChooserItemClick(onChooserItemClick: ((NestedItem) -> Unit)) {
        this.onChooserItemClick = onChooserItemClick
    }

}


sealed class NestedItem {
    data object TextField : NestedItem()
    data class DatePicker(val editText: EditText) : NestedItem()
    data class GenderPicker(val editText: EditText) : NestedItem()
}
