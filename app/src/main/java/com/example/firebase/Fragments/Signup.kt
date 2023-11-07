package com.example.firebase.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.firebase.R
import com.example.firebase.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class Signup : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignupBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            loadFragment(login())
        }
        binding.button.setOnClickListener {

            signup(
                binding.emailEt.text.toString(),
                binding.passET.text.toString(),
                binding.confirmPassEt.text.toString()
            )
        }
        return binding.root
    }

    private fun signup(email: String, password: String, confirmPass: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty()) {
            if (password == confirmPass) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(requireContext(), "SignUp Successful", Toast.LENGTH_SHORT)
                            .show()
                        loadFragment(login())
                    } else {
                        Toast.makeText(
                            requireContext(), it.exception.toString(), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else Toast.makeText(requireContext(), "Password is not matching", Toast.LENGTH_SHORT)
                .show()

        } else Toast.makeText(requireContext(), "Empty field are not allowed", Toast.LENGTH_SHORT)
            .show()

    }

    private fun loadFragment(fragment: Fragment) {
        val ft = fragmentManager?.beginTransaction()
        ft?.replace(R.id.container, fragment)
        ft?.commit()
    }
}