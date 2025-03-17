package com.example.todolist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.data.Task
import com.example.todolist.databinding.TaskItemViewBinding
import com.example.todolist.TaskViewModel
import com.example.todolist.databinding.ActivityAddTaskBinding

class EditTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private var taskId: Int = 0  // Store Task ID
    private var isCompleted: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get task data from Intent
        taskId = intent.getIntExtra("task_id", 0)
        val taskTitle = intent.getStringExtra("task_title") ?: ""
        val taskDescription = intent.getStringExtra("task_description") ?: ""
        isCompleted = intent.getBooleanExtra("task_completed", false)

        // Set existing task details
        binding.etTitle.setText(taskTitle)
        binding.etDescription.setText(taskDescription)

        // Handle Save Button Click
        binding.btnSave.setOnClickListener {
            val updatedTitle = binding.etTitle.text.toString()
            val updatedDescription = binding.etDescription.text.toString()

            if (updatedTitle.isNotBlank()) {
                val updatedTask = Task(id = taskId, title = updatedTitle, description = updatedDescription, isCompleted = isCompleted )
                taskViewModel.update(updatedTask)  // Update in Room Database
                Toast.makeText(this, "Task Updated!", Toast.LENGTH_SHORT).show()
                finish()  // Close activity
            } else {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}