package kz.jibergroup.studyinn.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.presentation.auth.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    lateinit var mNavController: NavController
    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNavController()
        initNavigationActionListener()
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationClickListener)

        viewModel.userState.observe(this, userStateObserver)
        viewModel.checkUserAuthority()

    }


    private fun createNavController() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        mNavController = host.navController

        toolbar.setupWithNavController(mNavController)
    }


    private val bottomNavigationClickListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    mNavController.navigate(R.id.homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_catalog -> {
                    mNavController.navigate(R.id.catalogFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_profile -> {
                    mNavController.navigate(R.id.profileFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_course -> {
                    mNavController.navigate(R.id.myCoursesFragment)
                    return@OnNavigationItemSelectedListener true
                }
                else -> {
                    return@OnNavigationItemSelectedListener true
                }
            }
        }


    private fun initNavigationActionListener() {
        mNavController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.catalogFragment -> {
                    toolbar.visibility = View.GONE
                }
                R.id.profileFragment -> {
                    toolbar.visibility = View.GONE
                }
                else -> {
                    toolbar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onBackPressed() {
        findNavController(R.id.main_nav_host_fragment).navigateUp()
    }

    private val userStateObserver = Observer<MainViewModel.UserState> {
        when (it) {
            MainViewModel.UserState.UNAUTHORIZED -> {
                navigateToLogin()
            }
            else -> {
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}
