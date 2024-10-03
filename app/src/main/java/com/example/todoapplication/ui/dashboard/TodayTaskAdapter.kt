package com.example.todoapplication.ui.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapplication.R
import com.example.todoapplication.databinding.ItemTodayTaskBinding
import com.example.todoapplication.model.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TodayTaskAdapter():
    ListAdapter<Task, TodayTaskAdapter.TodayTaskViewHolder>(TodayTaskDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayTaskViewHolder {
        val binding = ItemTodayTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodayTaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodayTaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setTasks(tasks: List<Task>) {
        submitList(tasks)
    }

    inner class TodayTaskViewHolder(private val binding: ItemTodayTaskBinding): ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(task: Task) {
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val getTime = timeFormat.format(task.due_date)

            binding.time.text = getTime
            binding.title.text = task.title
            if(binding.isDone.isChecked) {
                task.status = "Completed"
                binding.time.setTextColor(R.color.blue)
            }
        }
    }

    class TodayTaskDiffCallback: DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }
}