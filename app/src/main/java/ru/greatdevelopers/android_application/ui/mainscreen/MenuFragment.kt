package ru.greatdevelopers.android_application.ui.mainscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_menu.*
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.FavoriteFragment
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.MainFragment
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.ProfileFragment
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.SearchFragment

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private var isFirstLaunch = true
    private val mainFragment = MainFragment()
    private val accountFragment = ProfileFragment()
    private val searchFragment = SearchFragment()
    private val favoriteFragment = FavoriteFragment()
    private var activeFragment: Fragment = mainFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        main_bottom_nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_profile -> {
                    loadFragment(accountFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_search -> {
                    loadFragment(searchFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_main -> {
                    loadFragment(mainFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_favourite -> {
                    loadFragment(favoriteFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    loadFragment(mainFragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
        if (isFirstLaunch) {
            main_bottom_nav_view.selectedItemId = R.id.action_main
            childFragmentManager.beginTransaction().apply {
                add(R.id.main_fragment_container, accountFragment).hide(accountFragment)
                add(R.id.main_fragment_container, searchFragment).hide(searchFragment)
                add(R.id.main_fragment_container, favoriteFragment).hide(favoriteFragment)
                add(R.id.main_fragment_container, mainFragment)
            }.commit()
            isFirstLaunch = false
        }
        loadFragment(mainFragment)

    }

    private fun loadFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commitNow()
        activeFragment = fragment
    }
}