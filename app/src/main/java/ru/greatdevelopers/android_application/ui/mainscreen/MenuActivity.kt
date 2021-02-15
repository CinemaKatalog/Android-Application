package ru.greatdevelopers.android_application.ui.mainscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.FavoriteFragment
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.MainFragment
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.ProfileFragment
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.SearchFragment

class MenuActivity : AppCompatActivity() {
    private lateinit var mainFragment: MainFragment
    private lateinit var accountFragment: ProfileFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var favoriteFragment: FavoriteFragment
    private lateinit var passedData: Bundle

    private lateinit var btmNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        passedData = intent.extras!!
        initView()
    }

    private fun initView(){
        accountFragment = ProfileFragment()
        accountFragment.arguments = passedData

        searchFragment = SearchFragment()
        searchFragment.arguments = passedData

        mainFragment = MainFragment()
        mainFragment.arguments = passedData

        favoriteFragment = FavoriteFragment()
        favoriteFragment.arguments = passedData

        btmNavView = findViewById(R.id.main_bottom_nav_view)

        btmNavView.selectedItemId = R.id.action_main
        loadFragment(mainFragment)

        btmNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_profile ->{
                    loadFragment(accountFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_search ->{
                    loadFragment(searchFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_main ->{
                    loadFragment(mainFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_favourite ->{
                    loadFragment(favoriteFragment)
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