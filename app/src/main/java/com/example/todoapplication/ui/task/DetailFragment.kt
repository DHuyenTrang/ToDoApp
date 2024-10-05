package com.example.todoapplication.ui.task

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
import com.example.todoapplication.Constant
import com.example.todoapplication.R
import com.example.todoapplication.databinding.FragmentDetailBinding
import com.example.todoapplication.viewmodel.CategoryViewModel
import com.example.todoapplication.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val calendar = Calendar.getInstance()
    private var date = Date()

    val args: DetailFragmentArgs by navArgs()
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModel.TaskViewModelFactory(requireContext())
    }
    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModel.CategoryViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.dateInput.setOnClickListener {
            openDatePickerDialog()
        }

        binding.timeInput.setOnClickListener {
            openTimePickerDialog()
        }

        binding.categoryInput.setOnClickListener {
            findNavController().navigate(R.id.chooseCategoryFragment)
        }
        if(Constant.category_id > 0){
            categoryViewModel.getCategoryById(Constant.category_id).observe(viewLifecycleOwner, Observer {
                binding.categoryInput.text = it.name
            })
        }
        val currTask = taskViewModel.getTaskById(args.idTask)

        currTask.observe(viewLifecycleOwner, Observer {
            binding.titleInput.setText(it.title)
            val dateformat = SimpleDateFormat("dd/MM/yyyy").format(it.due_date)
            binding.dateInput.text = "  $dateformat"
            val timeformat = SimpleDateFormat("HH:mm").format(it.due_date)
            binding.timeInput.text = "  $timeformat"
            binding.descriptionInput.setText(it.description)
            categoryViewModel.getCategoryById(it.category_id).observe(viewLifecycleOwner, Observer {
                    data -> binding.categoryInput.text = data.name
            })
        })

        binding.doneButton.setOnClickListener{
            currTask.observe(viewLifecycleOwner, Observer {
                it.title = binding.titleInput.text.toString()
                it.due_date = this.date
                if(Constant.category_id != 0) {
                    it.category_id = Constant.category_id
                }
                it.description = binding.descriptionInput.text.toString()
                it.status = "To Do"
            })
            currTask.value?.let {
                taskViewModel.updateTask(it)
            }
            Toast.makeText(requireContext(), "Update task success", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        binding.deleteButton.setOnClickListener {
            taskViewModel.deleteTask(currTask.value!!)
            Toast.makeText(requireContext(), "Delete task success", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

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