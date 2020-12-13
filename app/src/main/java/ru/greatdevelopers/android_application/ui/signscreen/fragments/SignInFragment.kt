package ru.greatdevelopers.android_application.ui.signscreen.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.Utils.Utils
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.ui.mainscreen.MainActivity
import ru.greatdevelopers.android_application.viewmodel.SignInViewModel

class SignInFragment : Fragment() {
    private val signInViewModel by viewModel<SignInViewModel>()

    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button
    private lateinit var signUpFragment: SignUpFragment
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInButton = view.findViewById(R.id.btn_login)
        signUpButton = view.findViewById(R.id.btn_login_sign_up)
        signUpFragment = SignUpFragment()


        signInViewModel.user.observe(viewLifecycleOwner, Observer { foundUser ->
            user = foundUser
        })
        signInButton.setOnClickListener {
            if (et_sign_in_login.text.toString().isNotEmpty()
                && et_sign_in_password.text.toString().isNotEmpty()
            ) {

                val login = et_sign_in_login.text.toString()
                val password = et_sign_in_password.text.toString()

                signInViewModel.loginRequest(login){
                    user: User ->
                    if(user.password == password){
                        var intentSignIn = Intent(requireContext(), MainActivity::class.java)
                        intentSignIn.putExtra("user_id", user.id)
                        startActivity(intentSignIn)
                    }else{
                        Utils.showToast(requireContext(),
                            getString(R.string.text_sign_up_incorrect), Toast.LENGTH_SHORT)
                    }
                }
            } else {
                Utils.showToast(requireContext(),
                    getString(R.string.text_not_complete), Toast.LENGTH_SHORT)
            }


        }

        signUpButton.setOnClickListener {
            loadFragment(signUpFragment)
        }

    }

    private fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.sign_fragment_container, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}