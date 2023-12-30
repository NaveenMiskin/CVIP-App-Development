package com.example.todoapp.utilities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapp.model.modeled;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String NAME = "toDOAPPDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = " CREATE TABLE " + TODO_TABLE +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TASK + " TEXT," + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DataBase(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        // basically drop the previous or old tables......
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        // we are again creating the table
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertTask(@NonNull modeled task) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null, cv);
        db.close();

    }


    public void updateStatus(int id, int status) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});

    }

    public void updateTask(int id, String task) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db = this.getWritableDatabase();
        db.delete(TODO_TABLE, ID + "=?", new String[] {String.valueOf(id)});
    }


    @SuppressLint("Range")
    public List<modeled> getAllTask() {
        db = this.getWritableDatabase();
        List<modeled> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(TODO_TABLE, null, null,
                    null, null, null,
                    null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        modeled task = new modeled();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }
                    while (cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

}
