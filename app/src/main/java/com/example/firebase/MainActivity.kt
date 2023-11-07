package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.firebase.Fragments.add_query
import com.example.firebase.Fragments.all_query
import com.example.firebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseToken()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        backstackFragment(all_query())

        firebaseAuth = FirebaseAuth.getInstance()

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerlayout,
            binding.tabLayout.topappbar,
            R.string.OpenDrawer,
            R.string.CloseDrawer
        )
        binding.drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.drawernav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Add_query -> {
                    loadFragment(add_query())
                    binding.drawerlayout.closeDrawers()
                    true
                }

                R.id.Home -> {
                    backstackFragment(all_query())
                    binding.drawerlayout.closeDrawers()
                    true
                }

                R.id.Logout -> {
                    firebaseAuth.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.container, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }

    private fun backstackFragment(fragment: Fragment) {
        fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.container, fragment)
        ft.commit()
    }

    private fun firebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isSuccessful)
            {
                Log.e("Token",it.result)
            }
            else
            {
                Log.e("Failed","Unsuccessful")
            }
        }
    }

}