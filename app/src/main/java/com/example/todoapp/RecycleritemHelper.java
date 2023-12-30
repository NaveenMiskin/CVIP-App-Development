package com.example.todoapp;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.adapter.adapterclass;

public class RecycleritemHelper extends ItemTouchHelper.SimpleCallback {

    private adapterclass adapt;

    public RecycleritemHelper(adapterclass adapt) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapt = adapt;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,@NonNull RecyclerView.ViewHolder viewHolder,@NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapt.getContext());
            builder.setTitle("DELETE TASK");
            builder.setMessage("Are You Sure U Want To delete This Task?");
            builder.setPositiveButton("confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapt.deleteItem(position);
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapt.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        else {
            adapt.editItem(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c,@NonNull RecyclerView recyclerView,@NonNull RecyclerView.ViewHolder viewHolder, float dx, float dy, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable background;
        View itemView = viewHolder.itemView;
        int backgroundcorneroffset = 20;
        if (dx > 0) {
            icon = ContextCompat.getDrawable(adapt.getContext(), R.drawable.ic_baseline_edit_24);
            background = new ColorDrawable(ContextCompat.getColor(adapt.getContext(), R.color.myblue));
        }
        else {
            icon = ContextCompat.getDrawable(adapt.getContext(), R.drawable.ic_baseline_delete_24);
            background = new ColorDrawable(Color.RED);
        }
        assert icon != null;
        int iconmargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int icontop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconButtom = icontop + icon.getIntrinsicHeight();

        if (dx > 0) {
            int iconleft = itemView.getLeft() + iconmargin;
            int iconRuRight = itemView.getLeft() + iconmargin + icon.getIntrinsicHeight();
            icon.setBounds(iconleft, icontop, iconRuRight, iconButtom);
            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dx) + backgroundcorneroffset, itemView.getBottom());
        } else if (dx < 0) {
            int iconleft = itemView.getRight() - iconmargin - icon.getIntrinsicWidth();
            int iconRuRight = itemView.getRight() - iconmargin;
            icon.setBounds(iconleft, icontop, iconRuRight, iconButtom);
            background.setBounds(itemView.getRight() + ((int) dx) - backgroundcorneroffset, itemView.getTop(),
                    itemView.getRight(), itemView.getBottom());
        } else {
            background.setBounds(0, 0, 0, 0);
        }
        background.draw(c);
        icon.draw(c);
    }
}
