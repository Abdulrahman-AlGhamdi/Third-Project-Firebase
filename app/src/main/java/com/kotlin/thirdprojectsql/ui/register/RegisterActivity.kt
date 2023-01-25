package com.kotlin.thirdprojectfirebase.ui.register

import android.content.Intent
import android.os.Bundle
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
import com.kotlin.thirdprojectsql.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private val database = Firebase.firestore
    private lateinit var viewModel: RegisterViewModel
    private lateinit var phoneInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var nameInput: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()

        registerButton.setOnClickListener {
            val phone = phoneInput.text.toString()
            val email = emailInput.text.toString()
            val name = nameInput.text.toString()
            val isPhoneValid = viewModel.isPhoneValid(phone)

            if (isPhoneValid) {
                val reference = database.collection(LoginActivity.USER_COLLECTION)
                val getTask = reference.get()

                getTask.addOnSuccessListener { result ->
                    var check = false
                    for (document in result) {
                        val user = document.toObject(UserModel::class.java)

                        if (user.phone == phone) {
                            check = true
                            break
                        }
                    }

                    if (check) Toast.makeText(this, "User exist", Toast.LENGTH_SHORT).show()
                    else {
                        val newUser = mapOf(
                            "phone" to phone,
                            "email" to email,
                            "name" to name
                        )

                        val addTask = reference.add(newUser)

                        addTask.addOnSuccessListener {
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.putExtra("phone", phone)
                            startActivity(intent)
                        }
                    }
                }
            } else Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        phoneInput = findViewById(R.id.phone)
        emailInput = findViewById(R.id.email)
        nameInput = findViewById(R.id.name)
        registerButton = findViewById(R.id.register)
    }
}