package com.example.attendance;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

    private DbHelper dbHelper;
    private long cid;

    private MyCalendar calendar;
    private  TextView subTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        dbHelper = new DbHelper(this);
        calendar = new MyCalendar();


        Intent intent = getIntent();
        classname = intent.getStringExtra("className");
        subjectName = intent.getStringExtra("subjectName");
        position = intent.getIntExtra("position",-1);
        cid = intent.getLongExtra("cid",-1);



        setToolBar();
        loadData();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        studentAdapter = new StudentAdapter(this,studentItems);
        recyclerView.setAdapter(studentAdapter);

        studentAdapter.setItemClickLister(position1 -> changeStudentStatus());





    }

    private void loadData() {
        Cursor cursor = dbHelper.getStudentTable(cid);
        studentItems.clear();
        while (cursor.moveToNext()){
            long sid = cursor.getLong(cursor.getColumnIndexOrThrow(Constant.S_ID));
            int roll = cursor.getInt(cursor.getColumnIndexOrThrow(Constant.STUDENT_ROLL_KEY));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(Constant.STUDENT_NAME_KEY));
            studentItems.add(new StudentItem(sid,String.valueOf(roll),name));
        }
        cursor.close();
    }

    private void changeStudentStatus() {

        String status = studentItems.get(position).getStatus();

        if (status.equals("P")) status = "A";
        else status = "P";

        studentItems.get(position).setStatus(status);
        studentAdapter.notifyItemChanged(position);

    }


    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title_text_view);
        subTitle = toolbar.findViewById(R.id.toolbar_subTitle_text_view);

        ImageButton backButton = toolbar.findViewById(R.id.back_button);
        ImageButton saveButton = toolbar.findViewById(R.id.save_button);

        title.setText(classname);
        subTitle.setText(subjectName+":"+calendar.getDate());

        backButton.setOnClickListener(v -> onBackPressed());

        toolbar.inflateMenu(R.menu.student_menu);
        toolbar.setOnMenuItemClickListener(menuItem->onMenuItemCLick(menuItem));


    }

    private boolean onMenuItemCLick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.add_student){
            showAddStudentDialog();
        }
        else if (menuItem.getItemId() == R.id.show_calendar){
            showCalendar();
        }
        return  true;
    }

    private void showCalendar() {
        calendar = new MyCalendar();
        calendar.show(getSupportFragmentManager(),"");
        calendar.setOnCalendarOkClickListener(this::onCalendarOkClick);
    }

    private void onCalendarOkClick(int year, int month, int day) {
        calendar.setDate(year,month,day);
        subTitle.setText(subjectName+":"+calendar.getDate());

    }

    private void showAddStudentDialog() {
        MyDialog myDialog = new MyDialog();
        myDialog.show(getSupportFragmentManager(),MyDialog.STUDENT_ADD_DIALOG);
        myDialog.setListener((roll,name)->addStudent(roll,name));
    }

    private void addStudent(String roll, String name) {
        long sid = dbHelper.addStudent(cid,Integer.parseInt(roll),name);
        StudentItem item = new StudentItem(sid,roll,name);
        studentItems.add(item);
        studentAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0:
                showUpdateStudentDialog(item.getGroupId());
                break;
            case 1:
                deleteStudent(item.getGroupId());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateStudentDialog(int position) {
        MyDialog myDialog = new MyDialog(studentItems.get(position).getRoll(),studentItems.get(position).getName());
        myDialog.show(getSupportFragmentManager(),MyDialog.STUDENT_UPDATE_DIALOG);
        myDialog.setListener((roll,name)->updateStudent(position,name));
    }

    private void updateStudent(int position, String name) {
        dbHelper.updateStudent(studentItems.get(position).getSid(),name);
        studentItems.get(position).setName(name);
        studentAdapter.notifyItemChanged(position);
    }

    private void deleteStudent(int position) {
        dbHelper.deleteStudent(studentItems.get(position).getSid());
        studentItems.remove(position);
        studentAdapter.notifyItemRemoved(position);
    }
}
