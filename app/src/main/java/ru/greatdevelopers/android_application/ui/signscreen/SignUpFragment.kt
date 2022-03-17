package ru.greatdevelopers.android_application.ui.signscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.utils.Utils
import ru.greatdevelopers.android_application.viewmodel.SignUpViewModel

class SignUpFragment : Fragment() {
    private val signUpViewModel by viewModel<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_sign_up.setOnClickListener {
            if (et_sign_up_name.text.toString().isNotEmpty()
                && et_sign_up_login.text.toString().isNotEmpty()
                && et_sign_up_password.text.toString().isNotEmpty()
            ) {

                val name = et_sign_up_name.text.toString()
                val login = et_sign_up_login.text.toString()
                val password = et_sign_up_password.text.toString()

                if(password.matches(Regex(Utils.PASSWORD_PATTERN))){

                    signUpViewModel.registerRequest(User(id = 0, name = name, login = login, password = password, userType = "user")){
                            user: User? ->
                        if(user != null){
                            goToSignInFragment()
                        }else{
                            Utils.showToast(requireContext(),
                                getString(R.string.text_sign_up_already_exist), Toast.LENGTH_SHORT)
                        }
                    }
                }else{
                    Utils.showToast(requireContext(),
                        getString(R.string.text_sign_up_wrong_password), Toast.LENGTH_SHORT)
                }
            } else {
                Utils.showToast(requireContext(),
                    getString(R.string.text_not_complete), Toast.LENGTH_SHORT)
            }
        }
    }

    private fun goToSignInFragment(){
        findNavController().navigate(
            SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
        )
    }

}