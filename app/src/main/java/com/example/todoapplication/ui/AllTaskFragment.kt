package com.example.todoapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapplication.Constant
import com.example.todoapplication.R
import com.example.todoapplication.databinding.FragmentAllTaskBinding
import com.example.todoapplication.adapter.TaskAdapter
import com.example.todoapplication.model.Task
import com.example.todoapplication.viewmodel.TaskViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class AllTaskFragment : Fragment() {
    private var _binding: FragmentAllTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNavigationView: BottomNavigationView
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModel.TaskViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // bottom menu
        bottomNavigationView = binding.bottomMenu
        bottomNavigationView.selectedItemId = R.id.button_alltask
        replaceFragment()
        setAdapterOverdue()
        setAdapterTodo()
    }

    private fun setAdapterOverdue() {
        val recyclerView = binding.overdueItems
        val adapter = TaskAdapter(onClick)
        taskViewModel.getAllTaskByUserStatus("Overdue", Constant.user_id)
            .observe(viewLifecycleOwner, Observer {
                val tasks = it ?: emptyList()
                adapter.setTasks(tasks)
            })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setAdapterTodo() {
        val recyclerView = binding.todoItems
        val adapter = TaskAdapter(onClick)
        taskViewModel.getAllTaskByUserStatus("To Do", Constant.user_id)
            .observe(viewLifecycleOwner, Observer {
                val tasks = it ?: emptyList()
                adapter.setTasks(tasks)
            })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private val onClick: (Int) -> Unit = {
        val action = AllTaskFragmentDirections.actionAllTaskFragmentToDetailFragment(it)
        findNavController().navigate(action)
    }

    private fun replaceFragment() {
        bottomNavigationView.setOnItemSelectedListener { items ->
            when (items.itemId) {
                R.id.button_home -> {
                    findNavController().navigate(R.id.dashboardFragment)
                    true
                }
                R.id.button_calendar -> {
                    findNavController().navigate(R.id.calendarFragment)
                    true
                }

                R.id.button_alltask -> {
                    true
                }

                R.id.button_setting -> {
                    findNavController().navigate(R.id.settingFragment)
                    true
                }
                else -> false
            }
        }
    }
}