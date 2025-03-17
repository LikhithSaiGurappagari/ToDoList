package com.example.todolist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityListOfTasksBinding

class list_of_tasks : AppCompatActivity() {
    private lateinit var binding: ActivityListOfTasksBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListOfTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = TaskAdapter(
            mutableListOf(),
            onEditClick = { task ->
                val intent = Intent(this, EditTaskActivity::class.java)
                intent.putExtra("task_id", task.id)
                intent.putExtra("task_title", task.title)
                intent.putExtra("task_description", task.description)
                startActivity(intent)
            },
            onTaskDeleted = { task ->
                taskViewModel.delete(task)
            },
            onTaskUpdated = { task ->
                taskViewModel.update(task)  // Update task instead of deleting
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        taskViewModel.allTasks.observe(this) { tasks ->
            adapter.updateTasks(tasks)
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, add_task::class.java)
            startActivity(intent)
        }
    }
}