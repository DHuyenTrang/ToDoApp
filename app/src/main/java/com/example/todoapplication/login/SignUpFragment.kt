package com.example.todoapplication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels() {
        UserViewModel.UserViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // had account -> Sign in
        binding.buttonSignIn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonSignUp.setOnClickListener { onSignUpClick() }
    }

    private fun onSignUpClick() {
        if (checkInput()) {
            viewModel.insertUser(
                binding.inputUsername.text.toString(),
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString(),
            )
            Toast.makeText(requireContext(), "Register account successful", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }
    fun checkInput(): Boolean {

        var check: Boolean = true
        if(binding.inputPassword.text.toString() == ""){
            binding.inputPassword.error = "You need to enter password"
            check = false
        }
        if(binding.inputUsername.text.toString() == ""){
            binding.inputUsername.error = "You need to enter username"
            check = false
        }

        if(binding.inputEmail.text.toString() == ""){
            binding.inputEmail.error = "You need to enter email"
            check = false
        }

        return check
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}