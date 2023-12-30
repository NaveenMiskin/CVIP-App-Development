package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todoapp.adapter.adapterclass;
import com.example.todoapp.model.modeled;
import com.example.todoapp.utilities.DataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView taskrecyclerview;
    private adapterclass taskadapter;
    private FloatingActionButton floatingbutn;

    private List<modeled> tasklist;
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = new DataBase(this);
        db.openDatabase();

        taskrecyclerview = findViewById(R.id.recyclerviewtask);
        taskrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        taskadapter = new adapterclass(db,MainActivity.this);
        taskrecyclerview.setAdapter(taskadapter);

        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new RecycleritemHelper(taskadapter));
        itemTouchHelper.attachToRecyclerView(taskrecyclerview);

        floatingbutn = findViewById(R.id.floatingbutn);
        tasklist = db.getAllTask();
        Collections.reverse(tasklist);
        taskadapter.settask(tasklist);

        floatingbutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newtaskadding.newInstance().show(getSupportFragmentManager(),newtaskadding.TAg);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        tasklist = db.getAllTask();
        Collections.reverse(tasklist);
        taskadapter.settask(tasklist);
        taskadapter.notifyDataSetChanged();
    }
}