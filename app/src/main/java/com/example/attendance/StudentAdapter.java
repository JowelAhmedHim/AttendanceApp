package com.example.attendance;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyStudentViewHolder> {

    private Context context;
    private ArrayList<StudentItem> studentItems;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onClick(int position);
    }

    public void setItemClickLister(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public StudentAdapter(Context context, ArrayList<StudentItem> studentItems) {
        this.context = context;
        this.studentItems = studentItems;
    }

    @NonNull
    @Override
    public MyStudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_student_item,parent,false);
        return new MyStudentViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyStudentViewHolder holder, int position) {

        StudentItem studentItem = studentItems.get(position);

        holder.studentRoll.setText(studentItem.getRoll());
        holder.studentName.setText(studentItem.getName());
        holder.studentStatus.setText(studentItem.getStatus());
        holder.cardView.setCardBackgroundColor(getColour(position));




    }

    private int getColour(int position) {
        String status = studentItems.get(position).getStatus();
        if (status.equals("P"))
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.present)));
        else if (status.equals("A"))
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.absent)));

        return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.white)));
    }

    @Override
    public int getItemCount() {
        return studentItems.size();
    }

    class MyStudentViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView studentRoll,studentName,studentStatus;
        private CardView cardView;

        public MyStudentViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            studentRoll = itemView.findViewById(R.id.roll);
            studentName = itemView.findViewById(R.id.name);
            studentStatus = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.card_view);

            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(),0,0,"Edit");
            menu.add(getAdapterPosition(),1,0,"DELETE");

        }
    }
}
