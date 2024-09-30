package com.example.todoapplication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.databinding.FragmentSignInBinding
import com.example.todoapplication.model.User

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels() {
        UserViewModel.UserViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // not have account -> sign up
        binding.buttonSignUp.setOnClickListener{
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        // click login
        binding.buttonLogin.setOnClickListener { onLoginClick() }
    }

    fun onLoginClick() {
        val emailInput = binding.inputEmail.text.toString()
        val passwordInput = binding.inputPassword.text.toString()
        viewModel.getAllUser().observe(viewLifecycleOwner, Observer {
            data -> val listUser: List<User> = data ?: emptyList()
            for(user in listUser) {
                if(user.email == emailInput && user.password == passwordInput) {
                    Toast.makeText(requireContext(), "Login success", Toast.LENGTH_SHORT).show()

                    val action = SignInFragmentDirections.actionSignInFragmentToDashboardFragment(user.id)
                    findNavController().navigate(action)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}