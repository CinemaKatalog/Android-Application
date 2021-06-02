package ru.greatdevelopers.android_application.ui.mainscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_menu.*
import ru.greatdevelopers.android_application.R

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private var isFirstLaunch = true
    /*private val mainFragment = MainFragment()
    private val accountFragment = ProfileFragment()
    private val searchFragment = SearchFragment()
    private val favoriteFragment = FavoriteFragment()
    private var activeFragment: Fragment = mainFragment*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //navController = Navigation.findNavController(view)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_menu_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        main_bottom_nav_view.selectedItemId = R.id.mainFragment
        main_bottom_nav_view.setupWithNavController(navController)

        //initView()

    }
/*

    private fun initView() {

        main_bottom_nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profileFragment -> {
                    NavigationUI.onNavDestinationSelected(it, navController);
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.searchFragment -> {
                    NavigationUI.onNavDestinationSelected(it, navController);

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.mainFragment -> {
                    NavigationUI.onNavDestinationSelected(it, navController);

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favoriteFragment -> {
                    NavigationUI.onNavDestinationSelected(it, navController);

                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(it, navController);
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
        */
/*if (isFirstLaunch) {
            main_bottom_nav_view.selectedItemId = R.id.action_menuFragment_to_mainFragment
            childFragmentManager.beginTransaction().apply {
                add(R.id.main_fragment_container, accountFragment).hide(accountFragment)
                add(R.id.main_fragment_container, searchFragment).hide(searchFragment)
                add(R.id.main_fragment_container, favoriteFragment).hide(favoriteFragment)
                add(R.id.main_fragment_container, mainFragment)
            }.commit()
            isFirstLaunch = false
        }*//*

        //loadFragment(mainFragment)

    }
*/

    /*private fun loadFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commitNow()
        activeFragment = fragment
    }*/

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.profileFragment -> {
                var intentEdit = Intent(this, EditActivity::class.java)
                startActivity(intentEdit)
                true
            }
            R.id.mainFragment -> {
                Toast.makeText(this, "delete action clicked", Toast.LENGTH_LONG).show();
                true;
            }
            R.id.favoriteFragment ->{
                Toast.makeText(this,"Home action", Toast.LENGTH_LONG).show()
                true
            }
            R.id.searchFragment ->{
                Toast.makeText(this,"Home action", Toast.LENGTH_LONG).show()
                true
            }
            else-> super.onOptionsItemSelected(item)
        }
    }*/

}