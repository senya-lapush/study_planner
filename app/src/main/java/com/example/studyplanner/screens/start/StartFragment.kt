package com.example.studyplanner.screens.start

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studyplanner.APP
import com.example.studyplanner.DEADLINE_DAYS
import com.example.studyplanner.R
import com.example.studyplanner.adapter.TaskAdapter
import com.example.studyplanner.databinding.FragmentStartBinding
import com.example.studyplanner.models.TaskModel

class StartFragment : Fragment() {

    companion object {
        fun clickTask(task: TaskModel) {
            val bundle = Bundle()
            bundle.putParcelable("task", task)
            APP.navController.navigate(R.id.action_startFragment_to_editTaskFragment, bundle)
        }
    }

    private lateinit var binding: FragmentStartBinding

    private lateinit var recyclerViewToDo: RecyclerView
    private lateinit var adapterToDo: TaskAdapter

    private lateinit var recyclerViewDeadlineSoon: RecyclerView
    private lateinit var adapterDeadlineSoon: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this) [StartViewModel::class.java]
        viewModel.initDatabase()

        recyclerViewDeadlineSoon = binding.rvDeadlineStart
        adapterDeadlineSoon = TaskAdapter(isDeadlineSoonTask = true)
        recyclerViewDeadlineSoon.adapter = adapterDeadlineSoon

        recyclerViewToDo = binding.rvTodoStart
        adapterToDo = TaskAdapter(isDeadlineSoonTask = false)
        recyclerViewToDo.adapter = adapterToDo

        viewModel.getDeadlineSoonTasks(DEADLINE_DAYS).observe(viewLifecycleOwner) { listTasks ->
            adapterDeadlineSoon.setList(listTasks)
        }

        viewModel.getDeadlineNotSoonTasks(DEADLINE_DAYS).observe(viewLifecycleOwner) { listTasks ->
            adapterToDo.setList(listTasks)
        }

        binding.btnGoToAddTask.setOnClickListener {
            APP.navController.navigate(R.id.action_startFragment_to_addTaskFragment)
        }
    }
}