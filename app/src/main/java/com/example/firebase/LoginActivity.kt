package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.firebase.Fragments.login

class LoginActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loadFragment(login())
    }

    private fun loadFragment(fragment: Fragment) {
        fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.container, fragment)
        ft.commit()
    }

}