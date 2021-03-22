package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_not_authorized.*
import ru.greatdevelopers.android_application.R

class UnregisteredFragment: Fragment(R.layout.fragment_not_authorized) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_not_authorized.setOnClickListener {
            /*val action = UnregisteredFragmentDirections.actionUnregisteredFragmentToSignNavGraph()
            it.findNavController().navigate(action)*/
        }
    }
}