package com.kotlin.thirdprojectsql.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kotlin.thirdprojectfirebase.R
import com.kotlin.thirdprojectfirebase.model.task.TaskModel
import com.kotlin.thirdprojectsql.ui.add.AddActivity

class HomeActivity : AppCompatActivity() {

    private val database = Firebase.firestore
    private lateinit var phone: String
    private lateinit var recycler: RecyclerView
    private lateinit var addButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()

        addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)

            intent.putExtra("phone", phone)
            startActivity(intent)
        }
    }

    private fun init() {
        phone = intent.getStringExtra("phone")!!
        recycler = findViewById(R.id.recycler)
        addButton = findViewById(R.id.add)
    }

    override fun onResume() {
        super.onResume()
        database.collection(USER_COLLECTION).get().addOnSuccessListener { result ->
            val array = arrayListOf<TaskModel>()

            for (document in result) {
                document.id
                val userPhone = document.get("phone")

                if (phone == userPhone) {
                    val task = document.toObject(TaskModel::class.java)
                    array.add(task)
                }
            }

            val adapter = HomeAdapter(array)
            recycler.adapter = adapter
        }
    }

    companion object {
        const val USER_COLLECTION = "tasks"
    }
}