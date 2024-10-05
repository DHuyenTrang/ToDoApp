package com.example.todoapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapplication.databinding.ItemTaskBinding
import com.example.todoapplication.model.Task
import com.example.todoapplication.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class TaskAdapter(private val onClick: (Int) -> Unit, val taskViewModel: TaskViewModel)
    : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallBack()) {

    inner class TaskViewHolder(private val binding: ItemTaskBinding): ViewHolder(binding.root) {
        fun bind(task: Task) {
            val timeFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
            val getTime = timeFormat.format(task.due_date)

            binding.time.text = getTime
            binding.title.text = task.title

            if(task.status == "Overdue"){
                binding.time.setTextColor(Color.parseColor("#FF5722"))
            }
            binding.taskItem.setOnClickListener {
                onClick(task.id)
            }
            if(task.status == "Completed") binding.isDone.isChecked = true
            binding.isDone.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    binding.time.setTextColor(Color.parseColor("#2196F3"))
                    taskViewModel.updateTaskCompleted(task)
                } else {
                    binding.time.setTextColor(Color.parseColor("#6b6e71"))
                    taskViewModel.updateTaskTodo(task)
                }
            }
        }
    }

    fun setTasks(tasks: List<Task>) {
        submitList(tasks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TaskDiffCallBack: DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }
}