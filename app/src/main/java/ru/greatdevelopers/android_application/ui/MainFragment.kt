package ru.greatdevelopers.android_application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.*
import ru.greatdevelopers.android_application.R

class MainFragment : Fragment(){
    private lateinit var collapsingToolbarLayout : CollapsingToolbarLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        return view
    }
}