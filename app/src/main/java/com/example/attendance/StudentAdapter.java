package com.example.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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




    }

    @Override
    public int getItemCount() {
        return studentItems.size();
    }

    class MyStudentViewHolder extends RecyclerView.ViewHolder{

        private TextView studentRoll,studentName,studentStatus;

        public MyStudentViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            studentRoll = itemView.findViewById(R.id.roll);
            studentName = itemView.findViewById(R.id.name);
            studentStatus = itemView.findViewById(R.id.status);

            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
        }
    }
}
