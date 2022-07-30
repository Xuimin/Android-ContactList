package com.example.contactlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlist.data.model.BaseItem
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.model.Title
import com.example.contactlist.databinding.ItemLayoutContactBinding
import com.example.contactlist.databinding.ItemLayoutTitleBinding

class ContactAdapter(private var items: List<BaseItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_TITLE -> createTitleViewHolder(parent)
            else -> createItemViewHolder(parent)
        }
    }

    private fun createItemViewHolder(parent: ViewGroup): ContactItemViewHolder {
        val binding = ItemLayoutContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactItemViewHolder(binding)
    }

    private fun createTitleViewHolder(parent: ViewGroup): TitleViewHolder {
        val binding = ItemLayoutTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if(holder is ContactItemViewHolder) {
            holder.bind(item as Contact)
        }
        if(holder is TitleViewHolder) {
            holder.bind(item as Title)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return when(items[position].getType()) {
            BaseItem.ITEM_TYPE -> VIEW_TYPE_ITEM
            else -> VIEW_TYPE_TITLE
        }
    }

    fun setModels(models: List<BaseItem>) {
        this.items = models
        notifyDataSetChanged()
    }

    inner class ContactItemViewHolder(
        private val binding: ItemLayoutContactBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact) {
            binding.tvName.text = item.name
            binding.tvPhone.text = item.phone
            binding.llContact.setOnClickListener {
                listener?.onItemClicked(item)
            }

            binding.btnDelete.setOnClickListener {
                listener?.onDeleteClicked(item)
            }
        }
    }

    inner class TitleViewHolder(private val binding: ItemLayoutTitleBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Title) {
            binding.tvTitle.text = item.title
        }
    }

    interface Listener {
        fun onItemClicked(item: Contact)
        fun onDeleteClicked(item: Contact)
    }

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_TITLE = 1
    }
}