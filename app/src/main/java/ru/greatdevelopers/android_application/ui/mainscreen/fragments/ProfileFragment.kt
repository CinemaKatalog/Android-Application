package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.ui.mainscreen.MainActivity
import ru.greatdevelopers.android_application.ui.signscreen.SignActivity

class ProfileFragment : Fragment(R.layout.fragment_profile){
    private lateinit var exitButton:Button
    companion object{
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }
    var isEditMode = false
    lateinit var viewFields:Map<String, TextView>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitButton = view.findViewById(R.id.btn_exit)
            exitButton.setOnClickListener {
                var intentExit = Intent(activity, SignActivity::class.java)
                startActivity(intentExit)
            }
        initViews(savedInstanceState)
    }

    private fun initViews(savedInstanceState: Bundle?){
        viewFields = mapOf(
            "nickname" to tv_name,
            "user_type" to tv_user_type,
            "name" to et_name,
            "email" to et_email,
            "password" to et_password
        )

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        showCurrentMode(isEditMode)


        btn_edit_profile.setOnClickListener(View.OnClickListener {
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        })


    }

    private fun showCurrentMode(isEdit: Boolean) {
        val info = viewFields.filter { setOf("name", "email", "password").contains(it.key) }
        for ((k, v) in info){
            v as EditText
            v.isFocusable = isEdit
            v.isFocusableInTouchMode = isEdit
            v.isEnabled = isEdit
            v.background.alpha = if(isEdit) 255 else 0
        }

        with(btn_edit_profile){

            val icon = if(isEdit){
                resources.getDrawable(R.drawable.ic_baseline_save_24, context.theme)
            }else{
                resources.getDrawable(R.drawable.ic_baseline_edit_24, context.theme)
            }

            setImageDrawable(icon)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState?.putBoolean(IS_EDIT_MODE, isEditMode)
    }
}