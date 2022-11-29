package com.example.studyplanner.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studyplanner.R
import com.example.studyplanner.models.relationships.SubjectWithTasksModel
import com.example.studyplanner.screens.subjects.SubjectsFragment

class SubjectAdapter : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {

    private var listSubjects = emptyList<SubjectWithTasksModel>()

    class SubjectViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_subjects, parent, false)
        return SubjectViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tv_subject_item_subjects).text = listSubjects[position].subject.name
        holder.itemView.findViewById<TextView>(R.id.tv_task_count_item_subjects).text = "${listSubjects[position].tasks.size} tasks"
    }

    override fun getItemCount(): Int {
        return listSubjects.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<SubjectWithTasksModel>) {
        listSubjects = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: SubjectViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener {
            SubjectsFragment.clickSubject(listSubjects[holder.adapterPosition].subject)
        }
    }

    override fun onViewDetachedFromWindow(holder: SubjectViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }
}