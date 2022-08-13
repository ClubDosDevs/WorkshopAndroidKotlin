package com.example.devnotes

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.devnotes.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<NotesViewModel>()
    private lateinit var binding: ActivityMainBinding
    val fbutton: ExtendedFloatingActionButton
        get() = binding.fbutton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val navhost = binding.navContainer.id
        supportFragmentManager.commit {
            add(navhost, HomeFragment())
        }
    }
}

fun AppCompatActivity.addFragment(host: Int, fragment: Fragment) {
    supportFragmentManager.commit {
        add(host, fragment)
    }
}