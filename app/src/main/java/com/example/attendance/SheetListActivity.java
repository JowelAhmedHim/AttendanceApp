package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SheetListActivity extends AppCompatActivity {


    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> listItems = new ArrayList();;
    private long cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_list);

        cid = getIntent().getLongExtra("cid",-1);

        loadListItem();
        
        listView = findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter(this,R.layout.sheet_list,R.id.date_list_item,listItems);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(((parent, view, position, id) -> openSheetActivity(position)));
    }

    private void openSheetActivity(int position) {
        long[] id_Array = getIntent().getLongArrayExtra("idArray");
        int[] rollArray = getIntent().getIntArrayExtra("rollArray");
        String[] nameArray = getIntent().getStringArrayExtra("nameArray");
        Intent intent = new Intent(this,SheetActivity.class);
        intent.putExtra("idArray",id_Array);
        intent.putExtra("rollArray",rollArray);
        intent.putExtra("nameArray",nameArray);
        intent.putExtra("month",listItems.get(position));
        startActivity(intent);
    }

    private void loadListItem() {
        Cursor cursor = new DbHelper(this).getDistinctMonths(cid);
        while (cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndexOrThrow(Constant.DATE_KEY));
            listItems.add(date.substring(3));
        }
    }
}