package com.example.aislepoc.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.aislepoc.R
import com.example.aislepoc.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    val binding : ActivityMainBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBadge()
    }

    fun showBottomNav(visible: Boolean){
        binding.bottomNavigation.visibility = if(visible){
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun setBadge(){
        val matchBadge = binding.bottomNavigation.getOrCreateBadge(R.id.matches)
        matchBadge.let {
            it.isVisible = true
            it.number = 50
            it.backgroundColor = resources.getColor(R.color.purple)
        }
        val noteBadge = binding.bottomNavigation.getOrCreateBadge(R.id.notes)
        noteBadge.let {
            it.isVisible = true
            it.number = 9
            it.backgroundColor = resources.getColor(R.color.purple)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}