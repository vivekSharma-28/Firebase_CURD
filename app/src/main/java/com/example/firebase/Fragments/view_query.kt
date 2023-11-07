package com.example.firebase.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firebase.databinding.FragmentViewQueryBinding

class view_query : Fragment() {

    private lateinit var binding: FragmentViewQueryBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewQueryBinding.inflate(inflater, container, false)

        val bundle = this.arguments

        if (bundle != null) {

            binding.apply {
                textName.text = bundle.getString("Name")
                textEmail.text = bundle.getString("Email")
                textNumber.text = bundle.getString("Number")
                textQuery.text = bundle.getString("Query")
            }

        }

        return binding.root
    }

}