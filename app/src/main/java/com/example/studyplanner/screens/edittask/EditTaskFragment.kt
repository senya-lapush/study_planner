package com.example.studyplanner.screens.edittask

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.studyplanner.APP
import com.example.studyplanner.R
import com.example.studyplanner.adapter.SpinnerSubjectAdapter
import com.example.studyplanner.databinding.FragmentEditTaskBinding
import com.example.studyplanner.models.TaskModel
import com.example.studyplanner.util.getDate
import java.util.*

class EditTaskFragment : Fragment() {

    private lateinit var binding: FragmentEditTaskBinding
    private lateinit var spinnerAdapter: SpinnerSubjectAdapter
    private lateinit var currentTask: TaskModel

    private var subjectChosen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditTaskBinding.inflate(layoutInflater, container, false)
        currentTask = arguments?.getParcelable<TaskModel>("task") as TaskModel
        subjectChosen = arguments?.getBoolean("subject_chosen") == true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this) [EditTaskViewModel::class.java]
        spinnerAdapter = SpinnerSubjectAdapter()
        binding.spSubjectEdittask.adapter = spinnerAdapter

        viewModel.getSubjects().observe(viewLifecycleOwner) { listSubjects ->
            spinnerAdapter.setList(listSubjects)
            binding.spSubjectEdittask.setSelection(spinnerAdapter.getItemPosition(currentTask.subjectId))
        }

        if (subjectChosen) {
            binding.spSubjectEdittask.isEnabled = false
        }

        binding.etTaskNameEdittask.setText(currentTask.name)
        binding.etTaskDescriptionEdittask.setText(currentTask.description)

        val calendar = Calendar.getInstance()
        calendar.time = currentTask.deadline
        binding.dpDeadlineEdittask.updateDate(calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        binding.btnEditTask.setOnClickListener {
            val subjectId = spinnerAdapter.getItemId(binding.spSubjectEdittask.selectedItemPosition)
            val name = binding.etTaskNameEdittask.text.toString()
            val description = binding.etTaskDescriptionEdittask.text.toString()
            val deadline = binding.dpDeadlineEdittask.getDate()

            if (name.isNotBlank()) {
                if (deadline > Calendar.getInstance().time) {
                    currentTask.subjectId = subjectId.toInt()
                    currentTask.name = name
                    currentTask.description = description
                    currentTask.deadline = deadline
                    viewModel.updateTask(currentTask) {}
                    if (subjectChosen) {
                        APP.navController.navigate(R.id.action_editTaskFragment_to_subjectAndTasksFragment)
                    } else {
                        APP.navController.navigate(R.id.action_editTaskFragment_to_startFragment)
                    }
                } else {
                    Toast.makeText(context, "Deadline date can't be in the past!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Fields are empty!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDeleteTask.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    viewModel.deleteTask(currentTask) {}
                }
                .setNegativeButton("No") {dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

}