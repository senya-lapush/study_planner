package com.example.studyplanner

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.studyplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP = this
        navController = Navigation.findNavController(this, R.id.nav_fragment)

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.subject_menu -> {
                    navController.navigate(R.id.subjectsFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.tasks_menu -> {
                    navController.navigate(R.id.startFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.settings_menu -> {
                    //nav
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        binding.bottomNav.selectedItemId = R.id.tasks_menu
    }
}