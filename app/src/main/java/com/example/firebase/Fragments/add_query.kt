package com.example.firebase.Fragments

import android.app.Activity
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.firebase.R
import com.example.firebase.databinding.FragmentAddQueryBinding
import com.example.firebase.model.Model
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class add_query : Fragment() {

    private lateinit var binding: FragmentAddQueryBinding
    private lateinit var firebase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddQueryBinding.inflate(inflater, container, false)
        firebase = FirebaseDatabase.getInstance().getReference("Query")

        binding.buttonSubmit.setOnClickListener {
            saveData()
            hideKeyboardFrom(binding.root)
        }

        binding.buttonClear.setOnClickListener {
            binding.apply {
                editName.text?.clear()
                editEmail.text?.clear()
                editNumber.text?.clear()
                editQuery.text?.clear()
            }
        }
        return binding.root
    }

    private fun saveData() {
        val name = binding.editName.text.toString().trim()
        val email = binding.editEmail.text.toString()
        val number = binding.editNumber.text.toString()
        val query = binding.editQuery.text.toString()
        if (!valid(name, email, number, query)) {
            val id = firebase.push().key!!
            val data = Model(id, name, email, number, query)
            firebase.child(id).setValue(data).addOnCompleteListener {
                binding.apply {
                    editName.text?.clear()
                    editEmail.text?.clear()
                    editNumber.text?.clear()
                    editQuery.text?.clear()
                }
                Toast.makeText(
                    this@add_query.requireContext(), "Data Entered Successfully", Toast.LENGTH_SHORT
                ).show()
                loadFragment(all_query())
            }.addOnFailureListener {
                Toast.makeText(this@add_query.requireContext(), "error", Toast.LENGTH_SHORT).show()
            }
        } else {
            valid(name, email, number, query)
        }
    }

    private fun valid(name: String, email: String, number: String, query: String): Boolean {

        var nameError = true
        var emailError = true
        var numberError = true
        var queryError = true

        if (name == "") {
            binding.editName.error = "Please Enter your Name"
        } else {
            val nPattern =
                "^[A-Z][a-zA-Z]{3,}(?: [A-Z][a-zA-Z]*){0,2}\$".toRegex(RegexOption.IGNORE_CASE)
            if (!nPattern.containsMatchIn(name)) {
                binding.editName.error = "Please enter the valid name "
            } else {
                nameError = false
            }
        }

        if (email == "") {
            binding.editEmail.error = "Please Enter your E-mail "
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.editEmail.error = "Please enter the valid E-mail "
            } else {
                emailError = false
            }
        }

        if (number == "") {
            binding.editNumber.error = "Please Enter your Mobile Number "
        } else {
            val mPattern =
                "^(?:(?:\\+|0{0,2})91(\\s*[ -]\\s*)?|0?)?[789]\\d{9}|(\\d[ -]?){10}\\d\$\n".toRegex(
                    RegexOption.IGNORE_CASE
                )
            if (!mPattern.containsMatchIn(number)) {
                binding.editNumber.error = "Please Enter A Valid Number "
            } else {
                numberError = false
            }
        }

        if (query.isEmpty()) {
            binding.editQuery.error = "Please enter your query"
        } else {
            queryError = false
        }

        if (!nameError && !emailError && !numberError && !queryError) {
            return false
        }

        return true
    }

    private fun loadFragment(fragment: Fragment) {
        val ft = fragmentManager?.beginTransaction()
        ft!!.replace(R.id.container, fragment)
        ft.commit()
    }

    private fun hideKeyboardFrom(view: View) {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}