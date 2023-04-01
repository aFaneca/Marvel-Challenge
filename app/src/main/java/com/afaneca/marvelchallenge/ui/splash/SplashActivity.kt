package com.afaneca.marvelchallenge.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import com.afaneca.marvelchallenge.MainActivity
import com.afaneca.marvelchallenge.R
import com.afaneca.marvelchallenge.databinding.ActivitySplashBinding
import java.time.Year
import java.util.Calendar
import java.util.Date

/**
 * We can't rely on the new SplashScreen API, due to its UI customization restrictions
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupCopyrightNotice()
        setupSplashAnimation()
    }

    private fun setupCopyrightNotice() {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        binding.tvCopyright.text = getString(R.string.splash_copyright_notice, currentYear)
    }

    private fun setupSplashAnimation() {
        val backgroundRotation =
            ObjectAnimator.ofFloat(binding.ivBackground, View.ROTATION, 0F, 360F).apply {
                duration = 500L
                startDelay = 500L
            }

        binding.tvCopyright.visibility = View.INVISIBLE
        val logoTranslation =
            ObjectAnimator.ofFloat(binding.tvCopyright, View.TRANSLATION_Y, 100F, 0F).apply {
                duration = 700L
                startDelay = 1_000L
                doOnStart { binding.tvCopyright.isVisible = true }
            }
        val logoOpacity = ObjectAnimator.ofFloat(binding.tvCopyright, View.ALPHA, 0F, 1F).apply {
            duration = 700L
            startDelay = 1_000L
        }
        // Combine them together and start the animations
        with(AnimatorSet()) {
            playTogether(backgroundRotation, logoTranslation, logoOpacity)
            start()
            doOnEnd { goToMainActivity() }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}