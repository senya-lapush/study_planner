package com.example.studyplanner.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studyplanner.R
import com.example.studyplanner.db.views.TaskView
import com.example.studyplanner.screens.start.StartFragment
import java.util.*

class TaskAdapter(private val isDeadlineSoonTask: Boolean) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var listTasks = emptyList<TaskView>()

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        var layout = R.layout.item_layout_todo
        if (isDeadlineSoonTask)
            layout = R.layout.item_layout_deadline

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TaskViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tv_subject_item).text = listTasks[position].subjectName
        holder.itemView.findViewById<TextView>(R.id.tv_task_item).text = listTasks[position].task.name

        if (isDeadlineSoonTask) {
            val date = listTasks[position].task.deadline
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date
            holder.itemView.findViewById<TextView>(R.id.tv_deadline_item).text =
                "until ${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH)}"
        }
    }

    override fun getItemCount(): Int {
        return listTasks.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<TaskView>) {
        listTasks = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: TaskViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            StartFragment.clickTask(listTasks[holder.adapterPosition].task)
        }
    }

    override fun onViewDetachedFromWindow(holder: TaskViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }
}