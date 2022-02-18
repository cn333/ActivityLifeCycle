package com.example.activitylifecycle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.example.activitylifecycle.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate")

        binding.discountButton.setOnClickListener {
            val firstName = binding.firstName.text.toString().trim()
            val lastName = binding.lastName.text.toString().trim()
            val email = binding.email.text.toString().trim()

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle(R.string.add_text_validation)
                    .setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            } else {
                val fullName = firstName.plus(" ").plus(lastName)
                binding.discountCodeConfirmation.text = getString(R.string.discount_code_confirmation, fullName)
                // Generates discount code
                binding.discountCode.text = UUID.randomUUID().toString().take(8).uppercase()

                hideKeyboard()
                clearInput()
            }
        }
    }

    private fun clearInput() {
        binding.firstName.text?.clear()
        binding.lastName.text?.clear()
        binding.email.text?.clear()
    }

    private fun hideKeyboard() {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
        outState.putString(DISCOUNT_CONFIRMATION_MESSAGE, binding.discountCodeConfirmation.text.toString())
        outState.putString(DISCOUNT_CODE, binding.discountCode.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState")

        binding.discountCodeConfirmation.text =
            savedInstanceState.getString(DISCOUNT_CONFIRMATION_MESSAGE, "")
        binding.discountCode.text = savedInstanceState.getString(DISCOUNT_CODE, "")

    }

    companion object {
        val TAG = "MainActivity"
        val DISCOUNT_CONFIRMATION_MESSAGE = "DISCOUNT_CONFIRMATION_MESSAGE"
        val DISCOUNT_CODE = "DISCOUNT_CODE"
    }
}