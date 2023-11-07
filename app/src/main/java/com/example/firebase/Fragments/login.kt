package com.example.firebase.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.firebase.MainActivity
import com.example.firebase.R
import com.example.firebase.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class login : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            loadFragment(Signup())
        }

        binding.button.setOnClickListener {
            signin(binding.emailEt.text.toString(), binding.passET.text.toString())
        }

        return binding.root
    }

    private fun signin(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "SignIn Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else Toast.makeText(
                    requireContext(), "user id & password is doesn't match", Toast.LENGTH_SHORT
                ).show()
            }
        } else Toast.makeText(
            requireContext(), "Empty field are not allowed", Toast.LENGTH_SHORT
        ).show()
    }

    private fun loadFragment(fragment: Fragment) {
        val ft = fragmentManager?.beginTransaction()
        ft?.replace(R.id.container, fragment)
        ft?.commit()
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
