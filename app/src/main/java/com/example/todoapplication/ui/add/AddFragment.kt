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
import androidx.navigation.fragment.navArgs
import com.example.todoapplication.databinding.FragmentAddBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val calendar = Calendar.getInstance()
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0
    val args: AddFragmentArgs by navArgs()
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

//        println(args.categoryId)
        val category_id = args.categoryId
        if (category_id != -1) {
            categoryViewModel.getCategoryById(category_id).observe(viewLifecycleOwner, Observer {
                binding.categoryInput.text = it.name
            })
        }

        binding.doneButton.setOnClickListener{ saveTask() }

    }

    fun saveTask() {
        val title = binding.titleInput.text.toString()
        val des = binding.descriptionInput.text.toString()
        val date = Date(year, month, day, hour, minute)
        val category_id = args.categoryId
        val user_id = args.userId
        val status = "To Do"
        viewModel.addTask(title, des, date, status, category_id, user_id)

        Toast.makeText(requireContext(), "Add new task success", Toast.LENGTH_SHORT).show()
    }

    private fun onCategoryClick() {
        val action = AddFragmentDirections.actionAddFragmentToChooseCategoryFragment(args.userId)
        findNavController().navigate(action)
    }

    @SuppressLint("SetTextI18n")
    fun openDatePickerDialog() {
        val datepicker: DatePickerDialog = DatePickerDialog(
            requireContext(), { DatePicker, year: Int, monthOfYear: Int, dayOfMonthh: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonthh)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattDate = dateFormat.format(selectedDate.time)
                this.year = calendar.get(Calendar.YEAR)
                this.month = calendar.get(Calendar.MONTH)
                this.day = calendar.get(Calendar.DAY_OF_MONTH)
                binding.dateInput.text = "  $formattDate"
            },
            year,
            month,
            day
        )
        datepicker.show()
    }

    @SuppressLint("SetTextI18n")
    fun openTimePickerDialog() {
        val currentTime = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
        this.hour = currentTime.get(Calendar.HOUR_OF_DAY)
        this.minute = currentTime.get(Calendar.MINUTE)
        val formattTime = timeFormat.format(currentTime.time)
        TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                binding.timeInput.text = "  $formattTime"

            },
            hour,
            minute,
            true
        ).show()
    }
}