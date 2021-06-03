package ru.greatdevelopers.android_application.ui.signscreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.utils.Utils
import ru.greatdevelopers.android_application.viewmodel.SignInViewModel

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val viewModel by viewModel<SignInViewModel>()

    private var user: User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner) { foundUser -> user = foundUser }
        initViews()
    }

    private fun initViews() {
        btn_login.setOnClickListener {
            if (et_sign_in_login.text.toString().isNotEmpty()
                && et_sign_in_password.text.toString().isNotEmpty()
            ) {

                val login = et_sign_in_login.text.toString()
                val password = et_sign_in_password.text.toString()

                viewModel.loginRequest(login) { user: User ->
                    if (user.password == password) {
                        viewModel.saveUser(user)
                        goToMenuActivity(user)
                    } else {
                        Utils.showToast(
                            requireContext(),
                            getString(R.string.text_sign_up_incorrect), Toast.LENGTH_SHORT
                        )
                    }
                }
            } else {
                Utils.showToast(
                    requireContext(),
                    getString(R.string.text_not_complete), Toast.LENGTH_SHORT
                )
            }


        }

        btn_login_sign_up.setOnClickListener {
            goToSignUpFragment()
        }
    }

    private fun goToMenuActivity(user: User) {
        /*findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToMenuFragment()
        )*/
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToMenuFragment()
        )
    }

    private fun goToSignUpFragment() {
        //findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        )
    }
}