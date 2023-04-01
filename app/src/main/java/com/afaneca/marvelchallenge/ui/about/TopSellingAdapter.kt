package com.afaneca.marvelchallenge.ui.about

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afaneca.marvelchallenge.R
import com.afaneca.marvelchallenge.databinding.AdapterComicItemBinding
import com.afaneca.marvelchallenge.ui.model.TopSellingItemUiModel
import com.afaneca.marvelchallenge.ui.utils.ImageLoader

class TopSellingAdapter(
    private val onItemClick: (item: TopSellingItemUiModel) -> Unit
) :
    ListAdapter<TopSellingItemUiModel, TopSellingAdapter.ViewHolder>(
        AsyncDifferConfig.Builder(DiffCallback()).build()
    ) {

    private class DiffCallback : DiffUtil.ItemCallback<TopSellingItemUiModel>() {
        override fun areItemsTheSame(
            oldItem: TopSellingItemUiModel,
            newItem: TopSellingItemUiModel
        ) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: TopSellingItemUiModel,
            newItem: TopSellingItemUiModel
        ) =
            oldItem == newItem
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopSellingAdapter.ViewHolder {
        val binding = AdapterComicItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TopSellingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopSellingAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    inner class TopSellingViewHolder(private val binding: AdapterComicItemBinding) :
        ViewHolder(binding.root) {
        override fun bind(item: TopSellingItemUiModel) {
            binding.root.setOnClickListener { onItemClick(item) }
            with(binding.tvName) {
                text = item.name
            }

            with(binding.ivCover) {
                ImageLoader.loadImageIntoView(
                    context,
                    item.imgUrl,
                    this,
                    placeholder = R.drawable.missing_comic
                )
            }
        }
    }


    abstract inner class ViewHolder(rootView: View) :
        RecyclerView.ViewHolder(rootView) {
        abstract fun bind(item: TopSellingItemUiModel)
    }
}