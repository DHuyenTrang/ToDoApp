package com.example.todoapplication.ui.add

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
import com.example.todoapplication.adapter.CategoryListAdapter
import com.example.todoapplication.databinding.FragmentChooseCategoryBinding
import com.example.todoapplication.model.Category

class ChooseCategoryFragment : Fragment() {
    private var _binding: FragmentChooseCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryViewModel by viewModels() {
        CategoryViewModel.CategoryViewModelFactory(requireContext())
    }
    val args: ChooseCategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChooseCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addTaskButton.setOnClickListener{
            openCreateDialog()
        }

        val recyclerAdapter = CategoryListAdapter(onClick)

        viewModel.getAllCategory().observe(viewLifecycleOwner, Observer {
            data -> val categories: List<Category> = data ?: emptyList()
            recyclerAdapter.setCategories(categories)
        })

        val recyclerView = binding.listCategories
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

    }

    private val onClick: (Int) -> Unit = { id ->
        val action = ChooseCategoryFragmentDirections.actionChooseCategoryFragmentToAddFragment(id, args.userId)
        findNavController().navigate(action)
    }

    fun openCreateDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.create_category_dialog, null)
        val nameCategory = dialogLayout.findViewById<EditText>(R.id.name_input)

        with(builder) {
            setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
                viewModel.insertCategory(nameCategory.text.toString())
            }
            setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int ->
                Log.d("Category", "Cancel create")
            }
            setView(dialogLayout)
            show()
        }
    }
}