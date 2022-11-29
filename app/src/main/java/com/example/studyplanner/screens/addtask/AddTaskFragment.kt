package com.example.studyplanner.screens.addtask

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.studyplanner.APP
import com.example.studyplanner.R
import com.example.studyplanner.adapter.SpinnerSubjectAdapter
import com.example.studyplanner.databinding.FragmentAddTaskBinding
import com.example.studyplanner.models.TaskModel
import com.example.studyplanner.util.getDate
import java.util.*

class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var spinnerAdapter: SpinnerSubjectAdapter

    private var subjectIdGot: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)
        subjectIdGot = arguments?.getInt("subject_id")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this) [AddTaskViewModel::class.java]
        spinnerAdapter = SpinnerSubjectAdapter()
        binding.spSubjectAddtask.adapter = spinnerAdapter

        viewModel.getSubjects().observe(viewLifecycleOwner) { listSubjects ->
            spinnerAdapter.setList(listSubjects)
        }

        if (subjectIdGot != null) {
            binding.spSubjectAddtask.setSelection(spinnerAdapter.getItemPosition(subjectIdGot!!))
            binding.spSubjectAddtask.isEnabled = false
        }

        binding.btnAddTask.setOnClickListener {
            val subjectId = spinnerAdapter.getItemId(binding.spSubjectAddtask.selectedItemPosition)
            val name = binding.etTaskNameAddtask.text.toString()
            val description = binding.etTaskDescriptionAddtask.text.toString()
            val deadline = binding.dpDeadlineAddtask.getDate()

            if (name.isNotBlank()) {
                if (deadline > Calendar.getInstance().time) {
                    viewModel.insertTask(
                        TaskModel(
                            subjectId = subjectId.toInt(),
                            name = name,
                            description = description,
                            deadline = deadline
                        )
                    ) {}
                    if (subjectIdGot != null) {
                        APP.navController.navigate(R.id.action_addTaskFragment_to_subjectAndTasksFragment)
                    } else {
                        APP.navController.navigate(R.id.action_addTaskFragment_to_startFragment)
                    }
                } else {
                    Toast.makeText(context, "Deadline date can't be in the past!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Fields are empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}