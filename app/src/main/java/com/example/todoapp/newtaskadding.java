package com.example.todoapp;

import static android.widget.Toast.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.todoapp.model.modeled;
import com.example.todoapp.utilities.DataBase;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.util.Objects;

public class newtaskadding extends BottomSheetDialogFragment {

    public static final String TAg = "ActionButtonDialog";
    private Button taskSaveButton;
    private EditText taskText;
    private DataBase db;

    public static newtaskadding newInstance(){
        return new newtaskadding();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bottom_layout,container,false);
        Objects.requireNonNull(getDialog()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }


    @SuppressLint("UseRequireInsteadOfGet")
    @Override
    public void onViewCreated(@NonNull View view ,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        taskText = Objects.requireNonNull(getView()).findViewById(R.id.edittasktext);
        taskSaveButton = getView().findViewById(R.id.button1);

        taskText = view.findViewById(R.id.edittasktext);
        taskSaveButton = view.findViewById(R.id.button1);


        boolean isupdated = false;
        final  Bundle bundle = getArguments();
        if(bundle != null){
            isupdated = true;
            String task = bundle.getString("task");
            taskText.setText(task);
            assert task != null;
            if(task.length() > 0) {
                taskSaveButton.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.myblue));
            }
        }
        db = new DataBase(getActivity());
        db.openDatabase();

        taskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    taskSaveButton.setEnabled(false);
                    taskSaveButton.setTextColor(Color.GRAY);
                }
                else {
                    taskSaveButton.setEnabled(true);
                    taskSaveButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.myblue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final boolean finalIsupdated = isupdated;
        taskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = taskText.getText().toString();

                if (finalIsupdated) {
                    db.updateTask(bundle.getInt("id"), text);
                } else {
                    modeled task = new modeled();
                    task.setTask(text);
                    task.setStatus(0);
                    db.insertTask(task);
                    db.close();
                }
                dismiss();
            }
        });
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        super.onDismiss(dialog);
        Activity activ = getActivity();
        if(activ instanceof DialogCloseListener){
            ((DialogCloseListener)activ).handleDialogClose(dialog);
        }
    }

}
