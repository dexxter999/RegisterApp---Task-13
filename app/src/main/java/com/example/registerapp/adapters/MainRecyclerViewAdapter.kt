package com.example.registerapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.registerapp.data.FieldItemSubListItem
import com.example.registerapp.databinding.MainRecyclerviewItemBinding

class MainRecyclerViewAdapter :
    ListAdapter<List<FieldItemSubListItem>, MainRecyclerViewAdapter.MainItemViewHolder>(
        MainDiffCallBack()
    ) {
    private var onNestedChooserItemClick: ((NestedItem) -> Unit)? = null

    inner class MainItemViewHolder(private val binding: MainRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(innerData: List<FieldItemSubListItem>) {
            with(binding) {
                val innerAdapter = NestedRecyclerViewAdapter()
                innerAdapter.submitList(innerData)
                innerAdapter.onChooserItemClick { hint ->
                    onNestedChooserItemClick?.invoke(hint)
                }
                nestedRecyclerView.adapter = innerAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        return MainItemViewHolder(
            MainRecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun onNestedChooserItemClick(click: (NestedItem) -> Unit) {
        this.onNestedChooserItemClick = click
    }

}

