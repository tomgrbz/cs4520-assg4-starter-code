package com.cs4520.assignment4.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cs4520.assignment4.R
import com.cs4520.assignment4.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    private lateinit var loginFragmentBinding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginFragmentBinding = LoginFragmentBinding.inflate(inflater, container, false);
        val view = loginFragmentBinding.root
        usernameEditText = loginFragmentBinding.usernameEditText
        passwordEditText = loginFragmentBinding.passwordEditText
        loginButton = loginFragmentBinding.loginButton
        val nav = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username == "admin" && password == "admin") {
                usernameEditText.text.clear()
                passwordEditText.text.clear()
                nav.navigate(R.id.action_loginFragment_to_productListFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Incorrect username or password",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
        return view
    }
}