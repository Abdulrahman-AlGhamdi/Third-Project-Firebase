package com.kotlin.thirdprojectfirebase.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kotlin.thirdprojectfirebase.R
import com.kotlin.thirdprojectfirebase.model.user.UserModel
import com.kotlin.thirdprojectfirebase.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private val database = Firebase.firestore
    private lateinit var viewModel: LoginViewModel
    private lateinit var phoneInput: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()

        loginButton.setOnClickListener {
            val phone = phoneInput.text.toString()
            val isPhoneValid = viewModel.isPhoneValid(phone)
            val reference = database.collection(USER_COLLECTION)
            val task = reference.get()

            if (isPhoneValid) {
                task.addOnSuccessListener { result ->
                    for (document in result) {
                        val user = document.toObject(UserModel::class.java)

                        if (user.phone == phone) {
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.putExtra("phone", user.phone)
                            startActivity(intent)
                            break
                        }

                        printMessage("User doesn't exist")
                    }
                }

                task.addOnFailureListener { exception ->
                    Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "onCreate: ${exception.message}")
                }
            } else printMessage("Please enter a valid number")
        }
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        phoneInput = findViewById(R.id.input)
        loginButton = findViewById(R.id.login)
    }

    private fun printMessage(message: String) = Toast.makeText(
        applicationContext,
        message,
        Toast.LENGTH_SHORT
    ).show()

    companion object {
        const val USER_COLLECTION = "users"
    }
}