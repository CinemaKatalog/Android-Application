package ru.greatdevelopers.android_application.ui.signscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_menu.*
import ru.greatdevelopers.android_application.R

class SignFragment: Fragment(R.layout.fragment_sign) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_sign_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

    }
}