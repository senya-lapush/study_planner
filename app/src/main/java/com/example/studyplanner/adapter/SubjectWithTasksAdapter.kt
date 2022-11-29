package com.example.studyplanner.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studyplanner.R
import com.example.studyplanner.db.views.TaskView
import com.example.studyplanner.models.TaskModel
import com.example.studyplanner.screens.subjectsandtasks.SubjectAndTasksFragment
import java.util.*

class SubjectWithTasksAdapter : RecyclerView.Adapter<SubjectWithTasksAdapter.TaskViewHolder>() {

    private var listTasks = emptyList<TaskModel>()

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_task, parent, false)
        return TaskViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tv_task_name_item).text = listTasks[position].name
        holder.itemView.findViewById<TextView>(R.id.tv_task_description_item).text = listTasks[position].description

        val date = listTasks[position].deadline
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        holder.itemView.findViewById<TextView>(R.id.tv_task_deadline_item).text =
            "until ${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH)}"
    }

    override fun getItemCount(): Int {
        return listTasks.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<TaskModel>) {
        listTasks = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: TaskViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            SubjectAndTasksFragment.clickTask(listTasks[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: TaskViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }
}