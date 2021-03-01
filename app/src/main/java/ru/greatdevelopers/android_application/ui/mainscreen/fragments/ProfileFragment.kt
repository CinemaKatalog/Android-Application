package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.Utils.Utils
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.viewmodel.ProfileViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val vm by viewModel<ProfileViewModel> { parametersOf(requireArguments().getInt("user_id")) }

    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>
    private var user: User? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_exit.setOnClickListener {
            /*var intentExit = Intent(activity, SignActivity::class.java)
            startActivity(intentExit)*/
        }
        initViews(savedInstanceState)
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickname" to tv_name,
            "user_type" to tv_user_type,
            "name" to et_name,
            "email" to et_email,
            "password" to et_password
        )

        vm.user.observe(viewLifecycleOwner, Observer { foundUser ->
            for ((k, v) in viewFields) {
                when (k) {
                    "nickname" -> v.text = foundUser.name
                    "user_type" -> v.text = foundUser.userType
                    "name" -> v.text = foundUser.name
                    "email" -> v.text = foundUser.login
                    "password" -> v.text = foundUser.password
                }
            }
            user = foundUser
        })
        vm.initialRequest()

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        showCurrentMode(isEditMode)

        btn_edit_profile.setOnClickListener(View.OnClickListener {

            if (isEditMode) {
                if (viewFields["name"]?.text.toString().isNotEmpty() &&
                    viewFields["email"]?.text.toString().isNotEmpty() &&
                    viewFields["password"]?.text.toString().isNotEmpty()
                ) {
                    if (viewFields["password"]?.text.toString()
                            .matches(Regex(Utils.PASSWORD_PATTERN))
                    ) {
                        var updateUser = User(
                            user!!.id,
                            name = viewFields["name"]?.text.toString(),
                            login = viewFields["email"]?.text.toString(),
                            password = viewFields["password"]?.text.toString(),
                            userType = user!!.userType
                        )

                        vm.updateUserInfo(updateUser) {
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
        })
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