package com.example.studyplanner.screens.addsubject

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.studyplanner.APP
import com.example.studyplanner.R
import com.example.studyplanner.databinding.FragmentAddSubjectBinding
import com.example.studyplanner.databinding.FragmentSubjectsBinding
import com.example.studyplanner.models.SubjectModel

class AddSubjectFragment : Fragment() {

    private lateinit var binding: FragmentAddSubjectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddSubjectBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val viewModel = ViewModelProvider(this) [AddSubjectViewModel::class.java]
        binding.btnAddSubject.setOnClickListener {
            val subjectName = binding.etSubjectNameAddSubject.text.toString()
            if (subjectName.isNotBlank()) {
                viewModel.insertSubject(SubjectModel(name = subjectName)) {}
                APP.navController.navigate(R.id.action_addSubjectFragment_to_subjectsFragment)
            } else {
                Toast.makeText(context, "Field is empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}