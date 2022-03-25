package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SheetListActivity extends AppCompatActivity {


    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList listItems;
    private long cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_list);

        listItems = new ArrayList();

        cid = getIntent().getLongExtra("cid",-1);

        loadListItem();
        
        listView = findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter(this,R.layout.sheet_list,R.id.date_list_item,listItems);
        listView.setAdapter(arrayAdapter);
    }

    private void loadListItem() {
        Cursor cursor = new DbHelper(this).getDistinctMonths(cid);
        while (cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndexOrThrow(Constant.DATE_KEY));
            listItems.add(date.substring(3));
        }
    }
}