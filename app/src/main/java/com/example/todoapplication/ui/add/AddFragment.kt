package com.example.todoapplication.ui.add

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.Constant
import com.example.todoapplication.R
import com.example.todoapplication.databinding.FragmentAddBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Timer
import kotlin.time.Duration.Companion.hours

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()
    private var date = Date()
    private var hour: Int = 0
    private var minute: Int = 0

    private val categoryViewModel: CategoryViewModel by viewModels() {
        CategoryViewModel.CategoryViewModelFactory(requireContext())
    }
    private val viewModel: AddFragmentViewModel by viewModels() {
        AddFragmentViewModel.AddFragmentViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dateInput.setOnClickListener {
            openDatePickerDialog()
        }

        binding.timeInput.setOnClickListener {
            openTimePickerDialog()
        }

        binding.categoryInput.setOnClickListener { onCategoryClick() }

        if (Constant.category_id != 0) {
            val categoryId = Constant.category_id
            categoryViewModel.getCategoryById(categoryId).observe(viewLifecycleOwner, Observer {
                binding.categoryInput.text = it.name
            })
        }

        binding.doneButton.setOnClickListener{ saveTask() }

    }

    fun saveTask() {
        val title = binding.titleInput.text.toString()
        val des = binding.descriptionInput.text.toString()
        val date = this.date
        val categoryId = Constant.category_id
        val userId = Constant.user_id
        val status = "To Do"
        viewModel.addTask(title, des, date, status, categoryId, userId)

        Toast.makeText(requireContext(), "Add new task success", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun onCategoryClick() {
        findNavController().navigate(R.id.chooseCategoryFragment)
    }

    @SuppressLint("SetTextI18n")
    fun openDatePickerDialog() {
        val datepicker: DatePickerDialog = DatePickerDialog(
            requireContext(), { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                val formattDate = dateFormat.format(selectedDate.time)

                this.date = selectedDate.time

                binding.dateInput.text = "  $formattDate"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datepicker.show()
    }

    @SuppressLint("SetTextI18n")
    fun openTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(),
            { _, selectedHour, selectedMinute ->
                val formattedTime = String.format("  %02d:%02d", selectedHour, selectedMinute)

                this.date.hours= selectedHour
                this.date.minutes = selectedMinute

                binding.timeInput.text = formattedTime
        }, hour, minute, true)

        timePickerDialog.show()
    }
}