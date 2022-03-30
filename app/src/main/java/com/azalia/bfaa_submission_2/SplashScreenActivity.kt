package com.azalia.bfaa_submission_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.azalia.bfaa_submission_2.databinding.ActivitySplashScreenBinding
import com.azalia.bfaa_submission_2.ui.main.MainActivity
import com.azalia.bfaa_submission_2.util.Constanta.TIME_SPLASH

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var splashScreenBinding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenBinding.root)

        val handler = Handler(mainLooper)

        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_SPLASH)

        supportActionBar?.hide()
    }
}