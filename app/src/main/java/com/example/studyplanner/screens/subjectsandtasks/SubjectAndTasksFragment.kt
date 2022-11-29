package com.example.studyplanner.screens.subjectsandtasks

import android.app.AlertDialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.studyplanner.APP
import com.example.studyplanner.R
import com.example.studyplanner.adapter.SubjectWithTasksAdapter
import com.example.studyplanner.databinding.FragmentSubjectAndTasksBinding
import com.example.studyplanner.models.SubjectModel
import com.example.studyplanner.models.TaskModel


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
            bundle.putParcelable("subject", currentSubject)
            APP.navController.navigate(R.id.action_subjectAndTasksFragment_to_addTaskFragment, bundle)
        }

        binding.btnEditSubjectName.setOnClickListener {
            binding.tvSubjectAndTasksHeader.isEnabled = true
            binding.tvSubjectAndTasksHeader.requestFocus()
            binding.tvSubjectAndTasksHeader.setSelection(binding.tvSubjectAndTasksHeader.length())
            val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.tvSubjectAndTasksHeader, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.tvSubjectAndTasksHeader.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.tvSubjectAndTasksHeader.isEnabled = false
                val subjectName = binding.tvSubjectAndTasksHeader.text.toString()
                if (subjectName.isNotBlank()) {
                    currentSubject.name = subjectName
                    viewModel.updateSubject(currentSubject) {}
                } else {
                    Toast.makeText(context, "Field is empty!", Toast.LENGTH_SHORT).show()
                }
                true
            } else {
                false
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