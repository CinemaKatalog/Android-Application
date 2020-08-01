package ru.greatdevelopers.android_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.greatdevelopers.android_application.ui.AccountFragment
import ru.greatdevelopers.android_application.ui.MainFragment
import ru.greatdevelopers.android_application.ui.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mainFragment: MainFragment
    private lateinit var accountFragment: AccountFragment
    private lateinit var searchFragment: SearchFragment

    private lateinit var btmNavView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView(){
        accountFragment = AccountFragment()
        searchFragment = SearchFragment()
        mainFragment = MainFragment()
        btmNavView = findViewById(R.id.main_bottom_nav_view)

        btmNavView.selectedItemId = R.id.action_main
        loadFragment(mainFragment)

        btmNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_profile->{
                    loadFragment(accountFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_search->{
                    loadFragment(searchFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_main->{
                    loadFragment(mainFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else->{
                    loadFragment(mainFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }

    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}