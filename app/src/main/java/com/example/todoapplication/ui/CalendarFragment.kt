package com.example.todoapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.databinding.FragmentCalendarBinding
import com.example.todoapplication.databinding.FragmentDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class CalendarFragment : Fragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNavigationView: BottomNavigationView

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

        // bottom menu
        bottomNavigationView = binding.bottomMenu
        bottomNavigationView.selectedItemId = R.id.button_calendar
        replaceFragment()
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