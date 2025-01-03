package com.android.banca_android.ui.init

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.mibanca.R
import com.android.mibanca.databinding.ActivityInitBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInitBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() = with(binding) {
        setupNavController()
    }

    private fun setupNavController() = with(binding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_home_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigation.setupWithNavController(navController)

        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.cardsFragment -> {
                    navController.navigate(R.id.cardsFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.paymentFragment -> {
                    navController.navigate(R.id.paymentFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.transactionsFragment -> {
                    navController.navigate(R.id.transactionsFragment)
                    return@setOnItemSelectedListener true
                }

                else -> false
            }
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fragmentManager: FragmentManager,
                fragment: Fragment,
                view: View,
                savedInstanceState: Bundle?
            ) {
            }
        }, true)
    }
}
