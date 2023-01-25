package com.kotlin.thirdprojectfirebase.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.thirdprojectfirebase.R
import com.kotlin.thirdprojectfirebase.model.task.TaskModel

class HomeAdapter(
    private val taskList: List<TaskModel>
) : RecyclerView.Adapter<HomeVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeVH {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.task_list_item, parent, false)

        return HomeVH(view)
    }

    override fun onBindViewHolder(holder: HomeVH, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = taskList.size
}