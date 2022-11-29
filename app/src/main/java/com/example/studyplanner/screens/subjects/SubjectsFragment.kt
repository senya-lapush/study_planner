package com.example.studyplanner.screens.subjects

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studyplanner.APP
import com.example.studyplanner.R
import com.example.studyplanner.adapter.SubjectAdapter
import com.example.studyplanner.databinding.FragmentSubjectsBinding
import com.example.studyplanner.models.SubjectModel
import com.example.studyplanner.models.relationships.SubjectWithTasksModel

class SubjectsFragment : Fragment() {

    companion object {
        fun clickSubject(subject: SubjectModel) {
            val bundle = Bundle()
            bundle.putParcelable("subject", subject)
            APP.navController.navigate(R.id.action_subjectsFragment_to_subjectAndTasksFragment, bundle)
        }
    }

    private lateinit var binding: FragmentSubjectsBinding
    lateinit var recyclerViewSubjects: RecyclerView
    lateinit var subjectAdapter: SubjectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this) [SubjectsViewModel::class.java]
        recyclerViewSubjects = binding.rvSubjects
        subjectAdapter = SubjectAdapter()
        recyclerViewSubjects.adapter = subjectAdapter

        binding.btnGoToAddSubject.setOnClickListener {
            APP.navController.navigate(R.id.action_subjectsFragment_to_addSubjectFragment)
        }

        viewModel.getAllSubjectsWithTasks().observe(viewLifecycleOwner) { listSubjects ->
            subjectAdapter.setList(listSubjects)
        }
    }
}