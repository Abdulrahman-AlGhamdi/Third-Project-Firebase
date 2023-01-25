package com.kotlin.thirdprojectfirebase.ui.add

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kotlin.thirdprojectfirebase.R

class AddActivity : AppCompatActivity() {

    private val database = Firebase.firestore
    private lateinit var phone: String
    private lateinit var taskInput: EditText
    private lateinit var completedBox: CheckBox
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        init()

        addButton.setOnClickListener {
            val taskName = taskInput.text.toString()
            val isCompleted = completedBox.isChecked

            database.collection(USER_COLLECTION).add(mapOf(
                "phone" to phone,
                "task" to taskName,
                "completed" to isCompleted
            ))

            finish()
        }
    }

    private fun init() {
        phone = intent.getStringExtra("phone")!!
        taskInput = findViewById(R.id.task_name)
        completedBox = findViewById(R.id.check)
        addButton = findViewById(R.id.add)
    }

    companion object {
        const val USER_COLLECTION = "tasks"
    }
}