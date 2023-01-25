package com.kotlin.thirdprojectfirebase.ui.home

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.thirdprojectfirebase.R
import com.kotlin.thirdprojectfirebase.model.task.TaskModel

class HomeVH(view: View) : RecyclerView.ViewHolder(view) {

    private val taskText = view.findViewById<TextView>(R.id.task)
    private val completedText = view.findViewById<TextView>(R.id.completed)

    fun bind(task: TaskModel) {
        taskText.text = task.task

        if (task.completed) {
            completedText.text = "Completed"
            completedText.setTextColor(Color.GREEN)
        } else {
            completedText.text = "Not Completed"
            completedText.setTextColor(Color.RED)
        }
    }
}