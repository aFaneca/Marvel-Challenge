package com.afaneca.marvelchallenge.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afaneca.marvelchallenge.databinding.AdapterCharacterItemBinding
import com.afaneca.marvelchallenge.ui.model.CharacterUiModel
import com.afaneca.marvelchallenge.ui.utils.ImageLoader

class CharacterListAdapter(private val onItemClick: (item: CharacterUiModel) -> Unit) :
    ListAdapter<CharacterUiModel, CharacterListAdapter.ViewHolder>(
        AsyncDifferConfig.Builder(DiffCallback()).build()
    ) {

    private class DiffCallback : DiffUtil.ItemCallback<CharacterUiModel>() {
        override fun areItemsTheSame(oldItem: CharacterUiModel, newItem: CharacterUiModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CharacterUiModel, newItem: CharacterUiModel) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    inner class ViewHolder(private val binding: AdapterCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CharacterUiModel) {
            binding.root.setOnClickListener { onItemClick(item) }
            binding.tvCharacter.text = item.name
            with(binding.ivCharacter) {
                ImageLoader.loadImageIntoView(context, item.imgUrl ?: "", this)
            }
        }
    }
}
