package com.example.todoapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.MainActivity;
import com.example.todoapp.R;
import com.example.todoapp.model.modeled;
import com.example.todoapp.newtaskadding;
import com.example.todoapp.utilities.DataBase;

import java.util.List;

    public class adapterclass extends RecyclerView.Adapter<adapterclass.ViewHolder> {

        private List<modeled> todolist;
       final private MainActivity actvi;
        final private DataBase db;

        public adapterclass(DataBase db,MainActivity actvi){
            this.db = db;
            this.actvi = actvi;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_task,parent,false);
            return new ViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder , int pos){
            db.openDatabase();
            final modeled item = todolist.get(pos);
            holder.task.setText(item.getTask());
            holder.task.setChecked(toBoolean(item.getStatus()));
            holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        db.updateStatus(item.getId(), 1);
                    } else {
                        db.updateStatus(item.getId(), 0);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return todolist.size();
        }

        public boolean toBoolean(int n){
            return n != 0;
        }

        public void settask(List<modeled> todolist) {
            this.todolist = todolist;
            notifyDataSetChanged();
        }

        public Context getContext(){
            return actvi;
        }

        public void deleteItem(int position){
            modeled item = todolist.get(position);
            db.deleteTask(item.getId());
            todolist.remove(position);
            notifyItemRemoved(position);
        }

        public void editItem(int position){
            modeled item = todolist.get(position);
            Bundle bundle = new Bundle();
            bundle.putInt("id",item.getId());
            bundle.putString("task",item.getTask());
            newtaskadding fragment = new newtaskadding();
            fragment.setArguments(bundle);
            fragment.show(actvi.getSupportFragmentManager(),newtaskadding.TAg);
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            CheckBox task;
            public ViewHolder(@NonNull View view){
                super(view);
                task = view.findViewById(R.id.todocheckbox);
            }
        }
    }


