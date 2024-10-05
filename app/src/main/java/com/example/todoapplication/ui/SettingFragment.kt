package com.example.todoapplication.ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.Constant
import com.example.todoapplication.R
import com.example.todoapplication.databinding.FragmentAllTaskBinding
import com.example.todoapplication.databinding.FragmentSettingBinding
import com.example.todoapplication.login.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNavigationView: BottomNavigationView
    val userViewModel: UserViewModel by viewModels {
        UserViewModel.UserViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // bottom menu
        bottomNavigationView = binding.bottomMenu
        bottomNavigationView.selectedItemId = R.id.button_setting
        replaceFragment()
        userViewModel.getUser(Constant.user_id).observe(viewLifecycleOwner, Observer {
            binding.userName.text = it.username
        })

        binding.editName.setOnClickListener {
            openCreateDialog()
        }
    }

    @SuppressLint("MissingInflatedId")
    fun openCreateDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.edit_username_dialog, null)
        val nameUser = dialogLayout.findViewById<EditText>(R.id.new_name)

        with(builder) {
            setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
                userViewModel.getUser(Constant.user_id).observe(viewLifecycleOwner, Observer {
                    val newName = nameUser.text.toString()
                    userViewModel.updateUser(it, newName)
                    Toast.makeText(requireContext(), "Edit username", Toast.LENGTH_SHORT).show()
                })
            }
            setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int ->
                Log.d("User", "Cancel edit")
            }
            setView(dialogLayout)
            show()
        }
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
                    findNavController().navigate(R.id.allTaskFragment)
                    true
                }

                R.id.button_setting -> {
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}