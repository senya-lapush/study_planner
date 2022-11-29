package com.example.studyplanner.screens.subjectsandtasks

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.studyplanner.APP
import com.example.studyplanner.R
import com.example.studyplanner.adapter.SubjectAdapter
import com.example.studyplanner.adapter.SubjectWithTasksAdapter
import com.example.studyplanner.databinding.FragmentSubjectAndTasksBinding
import com.example.studyplanner.db.views.TaskView
import com.example.studyplanner.models.SubjectModel
import com.example.studyplanner.models.TaskModel
import com.example.studyplanner.models.relationships.SubjectWithTasksModel

class SubjectAndTasksFragment : Fragment() {

    companion object {
        fun clickTask(task: TaskModel) {
            val bundle = bundleOf()
            bundle.putParcelable("task", task)
            bundle.putBoolean("subject_chosen", true)
            APP.navController.navigate(R.id.action_subjectAndTasksFragment_to_editTaskFragment, bundle)
        }
    }

    private lateinit var binding: FragmentSubjectAndTasksBinding
    private lateinit var currentSubject: SubjectModel

    lateinit var recyclerViewTasks: RecyclerView
    lateinit var subjectWithTasksAdapter: SubjectWithTasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectAndTasksBinding.inflate(layoutInflater, container, false)
        currentSubject = arguments?.getParcelable<SubjectModel>("subject") as SubjectModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this) [SubjectAndTasksViewModel::class.java]
        binding.tvSubjectAndTasksHeader.setText(currentSubject.name)

        recyclerViewTasks = binding.rvSubjectAndTasks
        subjectWithTasksAdapter = SubjectWithTasksAdapter()
        recyclerViewTasks.adapter = subjectWithTasksAdapter

        viewModel.getTasksWithSubjectId(currentSubject.id).observe(viewLifecycleOwner) { listTasks ->
            subjectWithTasksAdapter.setList(listTasks)
        }

        binding.btnGoToAddTaskFromSubject.setOnClickListener {
            val bundle = bundleOf()
            bundle.putInt("subject_id", currentSubject.id)
            APP.navController.navigate(R.id.action_subjectAndTasksFragment_to_addTaskFragment)
        }

        binding.btnEditSubjectName.setOnClickListener {
            binding.tvSubjectAndTasksHeader.isEnabled = true
        }

        binding.tvSubjectAndTasksHeader.doAfterTextChanged {
            binding.tvSubjectAndTasksHeader.isEnabled = false
            val subjectName = binding.tvSubjectAndTasksHeader.text.toString()
            if (subjectName.isNotBlank()) {
                currentSubject.name = subjectName
                viewModel.updateSubject(currentSubject) {}
            } else {
                Toast.makeText(context, "Field is empty!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDeleteSubject.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    viewModel.deleteSubject(currentSubject) {}
                    APP.navController.navigate(R.id.action_subjectAndTasksFragment_to_subjectsFragment)
                }
                .setNegativeButton("No") {dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

}