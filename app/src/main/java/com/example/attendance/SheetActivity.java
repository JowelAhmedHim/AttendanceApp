package com.example.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SheetActivity extends AppCompatActivity {


    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);

        dbHelper = new DbHelper(this);
        showTable();
    }

    private void showTable() {
        TableLayout tableLayout = findViewById(R.id.table_layout);
        long[] id_Array = getIntent().getLongArrayExtra("idArray");
        int[] rollArray = getIntent().getIntArrayExtra("rollArray");
        String[] nameArray = getIntent().getStringArrayExtra("nameArray");
        String month = getIntent().getStringExtra("month");

        Toast.makeText(this, ""+id_Array.length, Toast.LENGTH_SHORT).show();

        int DAY_IN_MONTH = getDayInMonth(month);

        TableRow[] rows = new TableRow[id_Array.length + 1];
        TextView[] tv_rolls = new TextView[id_Array.length + 1];
        TextView[] tv_names = new TextView[id_Array.length + 1];
        TextView[][] tv_status = new TextView[id_Array.length + 1][DAY_IN_MONTH + 1];

        for (int i = 0; i < id_Array.length + 1; i++) {
            tv_rolls[i] = new TextView(this);
            tv_names[i] = new TextView(this);
            for (int j = 1; j <= DAY_IN_MONTH; j++) {
                tv_status[i][j] = new TextView(this);
            }
        }

        tv_rolls[0].setText("Roll");
        tv_rolls[0].setTypeface(tv_rolls[0].getTypeface(), Typeface.BOLD);
        tv_names[0].setText("Name");
        tv_names[0].setTypeface(tv_names[0].getTypeface(), Typeface.BOLD);
        for (int i = 1; i <= DAY_IN_MONTH; i++) {
            tv_status[0][i].setText(String.valueOf(i));
            tv_status[0][i].setTypeface(tv_status[0][i].getTypeface(), Typeface.BOLD);
        }
        for (int i=1; i<id_Array.length+1; i++){
            tv_rolls[i].setText(String.valueOf(rollArray[i-1]));
            tv_names[i].setText((nameArray[i-1]));
            for (int j=1 ;j<=DAY_IN_MONTH; j++){
                String day = String.valueOf(j);
                if (day.length() ==1){
                    day = "0"+day;
                }
                String date = day+"."+month;
                String status = dbHelper.getStatus(id_Array[i-1],date);
                tv_status[i][j].setText(status);
            }
        }

        for (int i =0; i<id_Array.length+1;i++){
            rows[i] = new TableRow(this);
            rows[i].addView(tv_rolls[i]);
            rows[i].addView(tv_names[i]);

            for (int j=1;j<=DAY_IN_MONTH;j++){
                rows[i].addView(tv_status[i][j]);
            }

            tableLayout.addView(rows[i]);
        }



    }

    private int getDayInMonth(String month) {
        int monthIndex = Integer.parseInt(month.substring(0, 1));
        int year = Integer.parseInt(month.substring(4));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, monthIndex);
        calendar.set(Calendar.YEAR, year);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


}