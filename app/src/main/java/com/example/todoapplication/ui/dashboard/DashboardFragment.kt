package com.example.todoapplication.ui.dashboard

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
import com.example.todoapplication.databinding.FragmentDashboardBinding
import com.example.todoapplication.login.UserViewModel
import com.example.todoapplication.model.Task
import com.example.todoapplication.viewmodel.CategoryViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNavigationView: BottomNavigationView
    private val taskViewModel: DashboardViewModel by viewModels {
        DashboardViewModel.DashBoardViewModelFactory(requireContext())
    }
    private val categoryViewModel: CategoryViewModel by viewModels() {
        CategoryViewModel.CategoryViewModelFactory(requireContext())
    }
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.UserViewModelFactory(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateStatusTask()

        // bottom menu
        bottomNavigationView = binding.bottomMenu
        bottomNavigationView.selectedItemId = R.id.button_home
        replaceFragment()

        binding.addTaskButton.setOnClickListener {
            findNavController().navigate(R.id.addFragment)
        }
        userViewModel.getUser(Constant.user_id).observe(viewLifecycleOwner, Observer {
            binding.nameUser.text = "Hello ${it.username},"
        })
        //println(Constant.user_id)
        setAdapterTaskView()
        setAdapterCategory()

    }

    private fun updateStatusTask() {
        taskViewModel.getAllTaskByUser(Constant.user_id).observe(viewLifecycleOwner, Observer {
                data -> val tasks = data ?: emptyList()
            for(task: Task in tasks) {
                if(task.due_date < Calendar.getInstance().time && task.status != "Completed"){
                    taskViewModel.updateTaskOverdue(task)
                }
            }
        })
    }

    private fun setAdapterCategory() {
        val categoryAdapter = CategoryShortListAdapter(onClick)
        categoryViewModel.getAllCategory().observe(viewLifecycleOwner, Observer {
            data -> val categories = data ?: emptyList()
            categoryAdapter.setCategories(categories)
        })
        val categoriesView = binding.listCategories
        categoriesView.adapter = categoryAdapter
        categoriesView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private val onClick: (Int) -> Unit = {
        id ->
            val action = DashboardFragmentDirections.actionDashboardFragmentToDetailCategoryFragment(id)
        findNavController().navigate(action)
    }

    private fun setAdapterTaskView() {
        val taskAdapter = TodayTaskAdapter(taskViewModel)
        taskAdapter.setTasks(getTodayTask())
        val taskView = binding.listTask
        taskView.adapter = taskAdapter
        taskView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun getTodayTask(): List<Task> {
        val todayTasks = mutableListOf<Task>()

        taskViewModel.getAllTaskByUser(Constant.user_id)
            .observe(viewLifecycleOwner, Observer { data ->
                val tasks = data ?: emptyList()
                val toDay: Date = Calendar.getInstance().time
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                for (task: Task in tasks) {
                    if (dateFormat.format(task.due_date) == dateFormat.format(toDay)) {
                        todayTasks.add(task)
                    }
                }
            })
        return todayTasks
    }

    private fun replaceFragment() {
        bottomNavigationView.setOnItemSelectedListener { items ->
            when (items.itemId) {
                R.id.button_home -> true
                R.id.button_calendar -> {
                    findNavController().navigate(R.id.calendarFragment)
                    true
                }

                R.id.button_alltask -> {
                    findNavController().navigate(R.id.allTaskFragment)
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