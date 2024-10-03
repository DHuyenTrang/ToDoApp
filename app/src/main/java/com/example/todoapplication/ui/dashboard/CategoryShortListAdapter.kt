package com.example.todoapplication.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapplication.databinding.ItemCategoryBinding
import com.example.todoapplication.databinding.ItemCategoryShortBinding
import com.example.todoapplication.model.Category

class CategoryShortListAdapter(private val onClick: (Int) -> Unit):
    ListAdapter<Category, CategoryShortListAdapter.CategoryViewHolder>(CategoryDiffCallback()){

    inner class CategoryViewHolder(private val binding: ItemCategoryShortBinding): ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.nameCategory.text = category.name
            binding.shortCategoryItem.setOnClickListener { onClick(category.id) }
        }
    }

    fun setCategories(categories: List<Category>) {
        submitList(categories)
    }
    // async diffutil
    class CategoryDiffCallback: DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryShortBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}