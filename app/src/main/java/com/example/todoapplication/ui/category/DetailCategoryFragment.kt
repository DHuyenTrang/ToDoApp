package com.example.todoapplication.ui.category

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapplication.R
import com.example.todoapplication.adapter.TaskAdapter
import com.example.todoapplication.databinding.FragmentDetailCategoryBinding
import com.example.todoapplication.viewmodel.CategoryViewModel
import com.example.todoapplication.viewmodel.TaskViewModel

class DetailCategoryFragment : Fragment() {
    private var _binding: FragmentDetailCategoryBinding? = null
    private val binding get() = _binding!!
    val args: DetailCategoryFragmentArgs by navArgs()
    private val categoryViewModel: CategoryViewModel by viewModels() {
        CategoryViewModel.CategoryViewModelFactory(requireContext())
    }
    private val taskViewModel: TaskViewModel by viewModels() {
        TaskViewModel.TaskViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNameCategory()

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.editButton.setOnClickListener {
            editCategory()
        }

        setTaskAdapter()
    }

    private fun setTaskAdapter() {
        val adapter = TaskAdapter(onClick, taskViewModel)
        val recyclerView = binding.listTask
        taskViewModel.getAllTaskByCategory(args.categoryId).observe(viewLifecycleOwner, Observer {
            adapter.setTasks(it)
            binding.totalTask.text = "Total: ${it.size}"
        })
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    private val onClick: (Int) -> Unit = {
        val action = DetailCategoryFragmentDirections.actionDetailCategoryFragmentToDetailFragment(it)
        findNavController().navigate(action)
    }

    @SuppressLint("MissingInflatedId")
    fun editCategory() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.edit_category_dialog, null)

        with(builder) {
            setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
                val nameCategory = dialogLayout.findViewById<EditText>(R.id.name).text.toString()
                categoryViewModel.getCategoryById(args.categoryId).observe(viewLifecycleOwner, Observer {
                    categoryViewModel.updateCategory(it, nameCategory)
                })
                println(nameCategory)
                Log.d("Category", "Success edit")
            }
            setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int ->
                Log.d("Category", "Cancel edit")
            }
            setView(dialogLayout)
            show()
        }
    }

    private fun setNameCategory() {
        categoryViewModel.getCategoryById(args.categoryId)
            .observe(viewLifecycleOwner, Observer {
                    data -> val category = data ?: null
                if (category != null) {
                    binding.nameCategory.text = category.name
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}