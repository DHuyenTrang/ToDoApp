package com.example.todoapplication.ui.add

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapplication.R
import com.example.todoapplication.databinding.FragmentAddBinding
import com.example.todoapplication.viewmodel.CategoryViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val calendar = Calendar.getInstance()
    val args: AddFragmentArgs by navArgs()
    private val viewModel: CategoryViewModel by viewModels() {
        CategoryViewModel.CategoryViewModelFactory(requireContext())
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

//        val category_id = args?.toString()?.toInt()
//        if (category_id != null) {
//            viewModel.getCategoryById(category_id).observe(viewLifecycleOwner, Observer {
//                binding.categoryInput.text = it.name
//            })
//        }

    }

    private fun onCategoryClick() {
        findNavController().navigate(R.id.chooseCategoryFragment)
    }

    @SuppressLint("SetTextI18n")
    fun openDatePickerDialog() {
        val datepicker: DatePickerDialog = DatePickerDialog(
            requireContext(), { DatePicker, year: Int, monthOfYear: Int, dayOfMonthh: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonthh)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattDate = dateFormat.format(selectedDate.time)
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
        val currentTime = Calendar.getInstance()
        val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentTime.get(Calendar.MINUTE)
        TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                binding.timeInput.text = "  $hourOfDay: $minute"

            },
            startHour,
            startMinute,
            true
        ).show()
    }
}