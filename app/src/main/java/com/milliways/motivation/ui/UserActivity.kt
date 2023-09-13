package com.milliways.motivation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.milliways.motivation.R
import com.milliways.motivation.infra.SecurityPreferences
import com.milliways.motivation.databinding.ActivityUserBinding
import com.milliways.motivation.infra.MotivationConstants

class UserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivityUserBinding.inflate(this.layoutInflater)
        setContentView(this.binding.root)

        binding.buttonSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if(view.id == R.id.button_save) {
            handleSave()
        }
    }

    private fun handleSave() {
        val name: String = binding.editName.text.toString()

        if (name == "") {
            Toast.makeText(
                this,
                R.string.validation_mandatory_name,
                Toast.LENGTH_SHORT
            ).show()

        } else {
            SecurityPreferences(this).storeString(MotivationConstants.KEY.USER_NAME, name)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}