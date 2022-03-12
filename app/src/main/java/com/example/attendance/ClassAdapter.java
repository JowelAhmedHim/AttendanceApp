package com.example.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyClassViewHolder> {

    private Context context;
    private ArrayList<ClassItem> classItems;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onClick(int position);
    }

    public void setItemClickLister(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public ClassAdapter(Context context, ArrayList<ClassItem> classItems) {
        this.context = context;
        this.classItems = classItems;
    }

    @NonNull
    @Override
    public MyClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_class_item,parent,false);
        return new MyClassViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyClassViewHolder holder, int position) {

        ClassItem classItem = classItems.get(position);

        holder.className.setText(classItem.getClassName());
        holder.subjectName.setText(classItem.getSubjectName());

    }

    @Override
    public int getItemCount() {
        return classItems.size();
    }

    class MyClassViewHolder extends RecyclerView.ViewHolder{

        private TextView className,subjectName;

        public MyClassViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            className = itemView.findViewById(R.id.row_class_name);
            subjectName = itemView.findViewById(R.id.row_subject_name);

            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
        }
    }
}
