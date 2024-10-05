package com.example.todoapplication.ui.calendar

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
import com.example.todoapplication.databinding.FragmentCalendarBinding
import com.example.todoapplication.model.Task
import com.example.todoapplication.viewmodel.TaskViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNavigationView: BottomNavigationView
    val taskViewModel: TaskViewModel by viewModels {
        TaskViewModel.TaskViewModelFactory(requireContext())
    }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var datePicked: String = ""
    val date = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datePicked = dateFormat.format(date.time)
        setTaskAdapter()

        binding.calendarView.setOnDateChangeListener { _, year, month, day ->
            date.set(year, month, day)
            datePicked = dateFormat.format(date.time)
            setTaskAdapter()
        }

        // bottom menu
        bottomNavigationView = binding.bottomMenu
        bottomNavigationView.selectedItemId = R.id.button_calendar
        replaceFragment()
    }

    private fun setTaskAdapter() {
        val recyclerView = binding.listTask
        val adapter = CalendarTaskAdapter(onClick)
        val tasksPicked = mutableListOf<Task>()
        taskViewModel.getAllTaskByUser(Constant.user_id)
            .observe(viewLifecycleOwner, Observer{
                val tasks = it ?: emptyList()
                for(task: Task in tasks){
                    if(dateFormat.format(task.due_date.time) == datePicked){
                        tasksPicked.add(task)
                    }
                }
                adapter.setTasks(tasksPicked)
            })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private val onClick: (Int) -> Unit = {
        val action = CalendarFragmentDirections.actionCalendarFragmentToDetailFragment(it)
        findNavController().navigate(action)
    }

    private fun replaceFragment() {
        bottomNavigationView.setOnItemSelectedListener { items ->
            when(items.itemId) {
                R.id.button_home -> {
                    findNavController().navigate(R.id.dashboardFragment)
                    true
                }
                R.id.button_calendar -> true
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