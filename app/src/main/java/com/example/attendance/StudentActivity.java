package com.example.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String classname,subjectName;
    private int position;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private StudentAdapter studentAdapter;
    private ArrayList<StudentItem> studentItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        Intent intent = getIntent();
        classname = intent.getStringExtra("className");
        subjectName = intent.getStringExtra("subjectName");
        position = intent.getIntExtra("position",-1);

        setToolBar();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        studentAdapter = new StudentAdapter(this,studentItems);
        recyclerView.setAdapter(studentAdapter);





    }


    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title_text_view);
        TextView subTitle = toolbar.findViewById(R.id.toolbar_subTitle_text_view);
        ImageButton backButton = toolbar.findViewById(R.id.back_button);
        ImageButton saveButton = toolbar.findViewById(R.id.save_button);

        title.setText(classname);
        subTitle.setText(subjectName);

        backButton.setOnClickListener(v -> onBackPressed());

        toolbar.inflateMenu(R.menu.student_menu);
        toolbar.setOnMenuItemClickListener(menuItem->onMenuItemCLick(menuItem));


    }

    private boolean onMenuItemCLick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.add_student){
            showAddStudentDialog();
        }
        return  true;
    }

    private void showAddStudentDialog() {

        MyDialog myDialog = new MyDialog();
        myDialog.show(getSupportFragmentManager(),MyDialog.STUDENT_ADD_DIALOG);
        myDialog.setListener((roll,name)->addStudent(roll,name));
    }

    private void addStudent(String roll, String name) {
        studentItems.add(new StudentItem(roll,name));
        studentAdapter.notifyDataSetChanged();
    }
}
