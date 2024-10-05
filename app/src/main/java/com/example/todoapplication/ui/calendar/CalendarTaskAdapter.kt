package com.example.todoapplication.ui.calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapplication.databinding.ItemCalendarTaskBinding
import com.example.todoapplication.databinding.ItemTaskBinding
import com.example.todoapplication.model.Task
import java.text.SimpleDateFormat
import java.util.Locale

class CalendarTaskAdapter(private val onClick: (Int) -> Unit) :
    ListAdapter<Task, CalendarTaskAdapter.TaskViewHolder>(TaskDiffCallBack()) {

    inner class TaskViewHolder(private val binding: ItemCalendarTaskBinding) :
        ViewHolder(binding.root) {
        fun bind(task: Task) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            binding.date.text = dateFormat.format(task.due_date)
            binding.time.text = timeFormat.format(task.due_date)
            binding.status.text = task.status
            binding.status.setTextColor(
                when (task.status) {
                    "Completed" -> Color.parseColor("#2196F3")
                    "Overdue" -> Color.parseColor("#FF5722")
                    else -> Color.parseColor("#717171")
                }
            )
            binding.title.text = task.title
            binding.itemCalendarTask.setOnClickListener {
                onClick(task.id)
            }
        }
    }

    fun setTasks(tasks: List<Task>) {
        submitList(tasks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =
            ItemCalendarTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TaskDiffCallBack : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }
}