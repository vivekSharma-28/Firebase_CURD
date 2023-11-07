package com.example.firebase.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.Adpator.Adaptor
import com.example.firebase.R
import com.example.firebase.databinding.FragmentAllQueryBinding
import com.example.firebase.model.Model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class all_query : Fragment() {

    private lateinit var binding: FragmentAllQueryBinding
    private lateinit var arrayList: ArrayList<Model>
    private lateinit var recyclerAdaptor: Adaptor
    lateinit var firebase: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentAllQueryBinding.inflate(inflater, container, false)
        binding.allQuery.apply {
            layoutManager = LinearLayoutManager(this@all_query.requireContext())
            setHasFixedSize(true)
        }
        arrayList = arrayListOf()
        getData()
        return binding.root
    }

    private fun getData() {
        binding.allQuery.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        firebase = FirebaseDatabase.getInstance().getReference("Query")

        firebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val qData = data.getValue(Model::class.java)
                        arrayList.add(qData!!)
                    }
                    recyclerAdaptor = Adaptor(arrayList)
                    binding.allQuery.adapter = recyclerAdaptor
                    recyclerAdaptor.setOnItemClickListener = {


                        val fragment2 = view_query()
                        fragment2.arguments = Bundle().apply {
                            putString("Name", it.name)
                            putString("Email", it.email)
                            putString("Number", it.number)
                            putString("Query", it.query)
                        }
                        fragmentManager?.beginTransaction()?.replace(R.id.container, fragment2)
                            ?.addToBackStack(null)?.commit()

                    }
                    binding.apply {
                        allQuery.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@all_query.requireContext(), "Cancelled", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}