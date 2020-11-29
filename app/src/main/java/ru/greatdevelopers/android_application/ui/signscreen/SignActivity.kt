package ru.greatdevelopers.android_application.ui.signscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.FavoriteFragment
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.MainFragment
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.ProfileFragment
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.SearchFragment
import ru.greatdevelopers.android_application.ui.signscreen.fragments.SignInFragment
import ru.greatdevelopers.android_application.ui.signscreen.fragments.SignUpFragment

class SignActivity:AppCompatActivity() {
    private lateinit var signInFragment: SignInFragment
    private lateinit var signUpFragment: SignUpFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)
        initView()
    }

    private fun initView() {
        signInFragment = SignInFragment()
        signUpFragment = SignUpFragment()

        loadFragment(signInFragment)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.sign_fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}