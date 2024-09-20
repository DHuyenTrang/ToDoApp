package com.example.todoapplication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

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

        // click Sign Up
        binding.buttonSignUp.setOnClickListener {
            checkInput()
        }
    }

    fun checkInput(): Boolean {
        if(binding.inputUsername.text == null) {
            binding.inputUsername.error = "Invalid Username"
        }
        return true;
    }
}