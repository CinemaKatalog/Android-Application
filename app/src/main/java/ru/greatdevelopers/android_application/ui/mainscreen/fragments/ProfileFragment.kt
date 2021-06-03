package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.ui.mainscreen.MenuFragment
import ru.greatdevelopers.android_application.ui.mainscreen.MenuFragmentDirections
import ru.greatdevelopers.android_application.utils.Utils
import ru.greatdevelopers.android_application.viewmodel.ProfileViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    private val profileViewModel by viewModel<ProfileViewModel>()

    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(savedInstanceState)
        initVM()
    }

    /*override fun onStart() {
        super.onStart()
        profileViewModel.loadUser()
    }*/

    override fun onResume() {
        super.onResume()
        profileViewModel.loadUser()
    }

    private fun initVM() {
        profileViewModel.user.observe(viewLifecycleOwner) { foundUser ->
            if (foundUser == null) {

                group_signed.visibility = View.GONE
                group_unsigned.visibility = View.VISIBLE

                /*val action = ProfileFragmentDirections.actionProfileFragmentToUnregisteredFragment()
                findNavController().navigate(action)*/
                return@observe
            }else{
                group_unsigned.visibility =View.GONE
                group_signed.visibility = View.VISIBLE
            }

            for ((k, v) in viewFields) {
                when (k) {
                    "nickname" -> v.text = foundUser.name
                    "user_type" -> v.text = foundUser.userType
                    "name" -> v.text = foundUser.name
                    "email" -> v.text = foundUser.login
                    "password" -> v.text = foundUser.password
                }
            }
        }
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickname" to tv_name,
            "user_type" to tv_user_type,
            "name" to et_name,
            "email" to et_email,
            "password" to et_password
        )
        btn_not_authorized_profile.setOnClickListener {
            /*findNavController().navigate(R.id.signInFragment)*/
            (requireParentFragment().requireParentFragment() as MenuFragment).findNavController()
                .navigate(MenuFragmentDirections.actionMenuFragmentToSignInFragment())
        }
        btn_exit.setOnClickListener {
            profileViewModel.signOut()
            // не нашел способа как переходить на другой экран из дочернего фрагмента, кроме этого
            (requireParentFragment().requireParentFragment() as MenuFragment).findNavController()
                .navigate(MenuFragmentDirections.actionMenuFragmentToSignInFragment())
            //должно работать так, но не работает (из дочернего графа можно перейти в любую точку родительского, из родительского только в дочерний)
            /*findNavController().navigate(R.id.sign_nav_graph)*/

        }
        btn_edit_profile.setOnClickListener {

            if (isEditMode) {
                if (viewFields["name"]?.text.toString().isNotEmpty() &&
                    viewFields["email"]?.text.toString().isNotEmpty() &&
                    viewFields["password"]?.text.toString().isNotEmpty()
                ) {
                    if (viewFields["password"]?.text.toString()
                            .matches(Regex(Utils.PASSWORD_PATTERN))
                    ) {

                        profileViewModel.updateUserInfo(
                            name = viewFields["name"]?.text.toString(),
                            login = viewFields["email"]?.text.toString(),
                            password = viewFields["password"]?.text.toString()
                        ) {
                            Utils.showToast(
                                requireContext(),
                                getString(R.string.text_changes_saved), Toast.LENGTH_SHORT
                            )
                        }

                    } else {
                        Utils.showToast(
                            requireContext(),
                            getString(R.string.text_sign_up_wrong_password), Toast.LENGTH_SHORT
                        )
                        isEditMode = !isEditMode
                    }
                } else {
                    isEditMode = !isEditMode
                    Utils.showToast(
                        requireContext(),
                        getString(R.string.text_not_complete), Toast.LENGTH_SHORT
                    )
                }
            }

            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }
        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        showCurrentMode(isEditMode)
    }

    private fun showCurrentMode(isEdit: Boolean) {
        val info = viewFields.filter { setOf("name", "email", "password").contains(it.key) }
        for ((k, v) in info) {
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if (isEdit) 255 else 0
        }

        with(btn_edit_profile) {

            val icon = if (isEdit) {
                resources.getDrawable(R.drawable.ic_baseline_save_24, context.theme)
            } else {
                resources.getDrawable(R.drawable.ic_baseline_edit_24, context.theme)
            }

            setImageDrawable(icon)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_EDIT_MODE, isEditMode)
    }
}