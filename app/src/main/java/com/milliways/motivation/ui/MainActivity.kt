package com.milliways.motivation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.milliways.motivation.R
import com.milliways.motivation.data.Mock
import com.milliways.motivation.infra.SecurityPreferences
import com.milliways.motivation.databinding.ActivityMainBinding
import com.milliways.motivation.infra.MotivationConstants
import java.util.Locale

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var category: Int = MotivationConstants.FILTER.ALL
    private val mock: Mock = Mock()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setListeners()

        verifyUserName()
        handleUserName()

        handleFilter(R.id.image_all)
    }

    override fun onResume() {
        super.onResume()
        handleUserName()
    }

    override fun onClick(view: View) {
        val id = view.id
        val categoryIds = listOf(R.id.image_all, R.id.image_happy, R.id.image_sunny)

        when (id) {
            R.id.new_phrase_button -> {
                changePhrase(this.category)

            }
            in categoryIds -> {
                handleFilter(id)

            }
            R.id.text_User_name -> {
                startActivity(Intent(this, UserActivity::class.java))
            }
        }
    }

    private fun verifyUserName() {
        var name: String = SecurityPreferences(this).getString(MotivationConstants.KEY.USER_NAME)
        if(name == "") {
            startActivity(Intent(this, UserActivity::class.java))
        }
    }

    private fun setListeners () {
        binding.newPhraseButton.setOnClickListener(this)
        binding.imageAll.setOnClickListener(this)
        binding.imageHappy.setOnClickListener(this)
        binding.imageSunny.setOnClickListener(this)
        binding.textUserName.setOnClickListener(this)
    }

    private fun handleFilter(id: Int) {
        binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
        binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
        binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))

        when (id) {
            R.id.image_all -> {
                binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.white))
                this.category = MotivationConstants.FILTER.ALL
                changePhrase(this.category)
            }
            R.id.image_happy -> {
                binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.white))
                this.category = MotivationConstants.FILTER.HAPPY
                changePhrase(this.category)
            }
            R.id.image_sunny -> {
                binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.white))
                this.category = MotivationConstants.FILTER.SUNNY
                changePhrase(this.category)
            }
        }
    }

    private fun handleUserName() {
        val name = SecurityPreferences(this).getString(MotivationConstants.KEY.USER_NAME)
        val hello = getString(R.string.hello, name)
        binding.textUserName.text = hello
    }

    private fun changePhrase(id: Int) {
        val language = Locale.getDefault().language
        var phrase = this.mock.getPhrase(id, language)
        binding.line.text = phrase
    }
}