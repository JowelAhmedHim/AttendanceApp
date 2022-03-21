package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ClassAdapter classAdapter;
    private ArrayList<ClassItem> classItems = new ArrayList<>();
    private DbHelper dbHelper;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setToolBar();
        dbHelper = new DbHelper(this);

        loadData();


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> showDialog());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        classAdapter = new ClassAdapter(this,classItems);
        recyclerView.setAdapter(classAdapter);

        classAdapter.setItemClickLister(position -> gotoItemActivity(position));



    }

    private void loadData() {
        Cursor cursor = dbHelper.getClassTable();
        classItems.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(Constant.C_ID));
            String className = cursor.getString(cursor.getColumnIndexOrThrow(Constant.CLASS_NAME_KEY));
            String subjectName = cursor.getString(cursor.getColumnIndexOrThrow(Constant.SUBJECT_NAME_KEY));

            classItems.add(new ClassItem(id,className,subjectName));
        }
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.toolbar_title_text_view);
        TextView subTitle = toolbar.findViewById(R.id.toolbar_subTitle_text_view);
        ImageButton backButton = toolbar.findViewById(R.id.back_button);
        ImageButton saveButton = toolbar.findViewById(R.id.save_button);

        title.setText(R.string.app_name);
        subTitle.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
    }

    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this,StudentActivity.class);
        intent.putExtra("className",classItems.get(position).getClassName());
        intent.putExtra("subjectName",classItems.get(position).getSubjectName());
        intent.putExtra("position",position);
        intent.putExtra("cid",classItems.get(position).getCid());
        startActivity(intent);
    }

    private void showDialog(){

        MyDialog myDialog = new MyDialog();
        myDialog.show(getSupportFragmentManager(),MyDialog.CLASS_ADD_DIALOG);
        myDialog.setListener((className,subjectName)->addClass(className,subjectName));

    }

    private void addClass(String className,String subjectName) {

        //insert data in database
        long cid = dbHelper.addClass(className,subjectName);

        ClassItem classItem = new ClassItem(cid,className,subjectName);

        //add data in arraylist
        classItems.add(classItem);

        //notify data changed to adapter
        classAdapter.notifyDataSetChanged();


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 0:
                showDeleteDialog(item.getGroupId());
                break;
            case 1:
                deleteClass(item.getGroupId());
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showDeleteDialog(int position) {
        MyDialog myDialog = new MyDialog();
        myDialog.show(getSupportFragmentManager(),MyDialog.CLASS_UPDATE_DIALOG);
        myDialog.setListener((className,subjectName)->updateClass(position,className,subjectName));
    }

    private void updateClass(int position, String className, String subjectName) {
        dbHelper.updateClass(classItems.get(position).getCid(),className,subjectName);
        classItems.get(position).setClassName(className);
        classItems.get(position).setSubjectName(subjectName);

        classAdapter.notifyItemChanged(position);
    }

    private void deleteClass(int position) {
        //ctr+shift+up arrow
        dbHelper.deleteClass(classItems.get(position).getCid());
        classItems.remove(position);
        classAdapter.notifyItemRemoved(position);
    }
}