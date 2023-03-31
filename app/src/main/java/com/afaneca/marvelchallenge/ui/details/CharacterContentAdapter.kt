package com.afaneca.marvelchallenge.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afaneca.marvelchallenge.R
import com.afaneca.marvelchallenge.databinding.AdapterComicItemBinding
import com.afaneca.marvelchallenge.databinding.AdapterEventItemBinding
import com.afaneca.marvelchallenge.databinding.AdapterStoriesSeriesItemBinding
import com.afaneca.marvelchallenge.ui.model.CharacterContentUiModel
import com.afaneca.marvelchallenge.ui.normalizeUrlToHttps
import com.afaneca.marvelchallenge.ui.utils.ImageLoader

sealed class ListViewType(val id: Int) {
    object Comic : ListViewType(1)
    object Event : ListViewType(2)
    object StorySeries : ListViewType(3)
}

class CharacterContentAdapter(
    private val viewType: ListViewType,
) :
    ListAdapter<CharacterContentUiModel, CharacterContentAdapter.ViewHolder>(
        AsyncDifferConfig.Builder(DiffCallback()).build()
    ) {

    private class DiffCallback : DiffUtil.ItemCallback<CharacterContentUiModel>() {
        override fun areItemsTheSame(
            oldItem: CharacterContentUiModel,
            newItem: CharacterContentUiModel
        ) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: CharacterContentUiModel,
            newItem: CharacterContentUiModel
        ) =
            oldItem == newItem
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewType.id
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterContentAdapter.ViewHolder = when (viewType) {
        ListViewType.Comic.id -> {
            val binding =
                AdapterComicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ComicViewHolder(binding)
        }

        ListViewType.Event.id -> {
            val binding =
                AdapterEventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            EventViewHolder(binding)
        }

        else -> {
            val binding = AdapterStoriesSeriesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            StorySeriesViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: CharacterContentAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ComicViewHolder(private val binding: AdapterComicItemBinding) :
        ViewHolder(binding.root) {
        override fun bind(item: CharacterContentUiModel) {
            with(binding.tvName) {
                text =
                    if (item.name.isNullOrBlank()) context.getString(com.afaneca.marvelchallenge.R.string.general_name_unknown) else item.name
            }
            with(binding.ivCover) {
                ImageLoader.loadImageIntoView(
                    context,
                    item.imgUrl?.normalizeUrlToHttps() ?: "",
                    this,
                    placeholder = R.drawable.missing_comic
                )
            }
        }
    }

    inner class EventViewHolder(private val binding: AdapterEventItemBinding) :
        ViewHolder(binding.root) {
        override fun bind(item: CharacterContentUiModel) {
            with(binding.tvTitle) {
                text =
                    if (item.name.isNullOrBlank()) context.getString(R.string.general_name_unknown) else item.name
            }
            with(binding.tvDescription) {
                text =
                    if (item.description.isNullOrBlank()) context.getString(R.string.general_description_unknown) else item.description
            }
            with(binding.ivCover) {
                ImageLoader.loadImageIntoView(
                    context,
                    item.imgUrl?.normalizeUrlToHttps() ?: "",
                    this,
                    placeholder = R.drawable.missing_comic
                )
            }
        }
    }

    inner class StorySeriesViewHolder(private val binding: AdapterStoriesSeriesItemBinding) :
        ViewHolder(binding.root) {
        override fun bind(item: CharacterContentUiModel) {
            with(binding.tvTitle) {
                text =
                    if (item.name.isNullOrBlank()) context.getString(R.string.general_name_unknown) else item.name
            }
            with(binding.tvDescription) {
                text =
                    if (item.description.isNullOrBlank()) context.getString(R.string.general_description_unknown) else item.description
            }
            with(binding.ivCover) {
                ImageLoader.loadImageIntoView(
                    context,
                    item.imgUrl?.normalizeUrlToHttps() ?: "",
                    this,
                    placeholder = R.drawable.missing_comic
                )
            }
        }
    }

    abstract inner class ViewHolder(rootView: View) :
        RecyclerView.ViewHolder(rootView) {
        abstract fun bind(item: CharacterContentUiModel)
    }
}