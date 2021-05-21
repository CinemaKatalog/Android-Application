package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_not_authorized.*
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.ui.mainscreen.MenuFragment
import ru.greatdevelopers.android_application.ui.mainscreen.MenuFragmentDirections

class UnregisteredFragment: Fragment(R.layout.fragment_not_authorized) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_not_authorized.setOnClickListener {
            // не нашел способа как переходить на другой экран из дочернего фрагмента, кроме этого
            /*(requireParentFragment().requireParentFragment() as MenuFragment).findNavController()
                .navigate(MenuFragmentDirections.actionMenuFragmentToSignInFragment())*/
        }
    }
}