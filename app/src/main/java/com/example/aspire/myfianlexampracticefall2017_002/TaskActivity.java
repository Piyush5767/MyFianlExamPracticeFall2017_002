package com.example.aspire.myfianlexampracticefall2017_002;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TaskActivity extends AppCompatActivity {

    private TaskManager taskManager;
    private EditText txtId, txtTaskName, txtTaskDescription ;
    private Button btnAdd, btnShow, btnEdit;
    private final static String TABLE_NAME = "AndroidTask";

    // SQL String to create a table
    private static final String tableCreatorString =
            "CREATE TABLE "+ TABLE_NAME + " (taskId integer primary key, taskName text,taskDescription text);";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        //
        txtId = (EditText) findViewById(R.id.edTextTaskId);
        txtTaskName = (EditText) findViewById(R.id.edTextTaskName);
        txtTaskDescription = (EditText) findViewById(R.id.edTextTaskDescription);
        //
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnShow = (Button)findViewById(R.id.btnShow);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        //
        // initialize the tables
        try {
            taskManager = new TaskManager(this);
            //create the table
            taskManager.dbInitialize(TABLE_NAME, tableCreatorString);
        }
        catch(Exception exception)
        {
            Toast.makeText(TaskActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",exception.getMessage());
        }
    }

    public void showTask(View v)
    {
        try {
            Task task = taskManager.getTaskById(txtId.getText().toString(), "taskId");
            txtTaskName.setText(task.getTaskName());
            txtTaskDescription.setText(task.getTaskDescription());
        }
        catch (Exception exception)
        {
            Toast.makeText(TaskActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",exception.getMessage());

        }
    }
    //
    public void addTask(View v)
    {
        //read values
        int taskId = Integer.parseInt(txtId.getText().toString());
        String taskName = txtTaskName.getText().toString();
        String taskDescription = txtTaskDescription.getText().toString();
        //initialize ContentValues object with the new task
        ContentValues contentValues = new ContentValues();
        contentValues.put("taskId",taskId);
        contentValues.put("taskName",taskName);
        contentValues.put("taskDescription",taskDescription);
        //
        try {
            taskManager.addRow(contentValues);
        }
        catch(Exception exception)
        {
            //
            Toast.makeText(TaskActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",exception.getMessage());

        }
    }

    public void editTask(View v)
    {
        int taskId = Integer.parseInt(txtId.getText().toString());

        String taskName = txtTaskName.getText().toString();
        String taskDescription = txtTaskDescription.getText().toString();
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("taskId",taskId);
            contentValues.put("taskName",taskName);
            contentValues.put("taskDescription",taskDescription);
            //edit the row
            boolean b=taskManager.editRow(taskId, "taskId", contentValues);
        }
        catch(Exception exception)
        {
            Toast.makeText(TaskActivity.this,
                    exception.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("Error: ",exception.getMessage());

        }
    }
}

