package ru.greatdevelopers.android_application.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.greatdevelopers.android_application.R

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
    }
}