package com.example.studyplanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.studyplanner.R
import com.example.studyplanner.models.SubjectModel

class SpinnerSubjectAdapter : BaseAdapter() {

    private var listSubjects = emptyList<SubjectModel>()

    override fun getCount(): Int {
        return listSubjects.size
    }

    override fun getItem(position: Int): String {
        return listSubjects[position].name
    }

    override fun getItemId(position: Int): Long {
        return listSubjects[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView: View? = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.spinner_dropdown_item_add_task, parent, false)
            convertView.findViewById<TextView>(R.id.tv_spinner_dropdown_item).text = getItem(position)
        }
        return convertView
    }

    fun setList(list: List<SubjectModel>) {
        listSubjects = list
        notifyDataSetChanged()
    }

    fun getItemPosition(id: Int): Int {
        var position = 0
        for (i in listSubjects.indices) {
            if (listSubjects[i].id == id) {
                position = i
                break
            }
        }
        return position
    }
}