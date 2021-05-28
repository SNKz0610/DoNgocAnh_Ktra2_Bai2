package com.snkz.dongocanh_ktra2_bai2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private EditText edtID, edtName, edtDate, edtTime;
    private CheckBox checkBoxStatus;
    private Button btnAdd, btnShow, btnEdit, btnDelete, btnSum;
    private RecyclerView recyclerView;
    private RecycleViewAdapter adpater;
    private SQLExamHelper sqlExamHelper;
    private SearchView searchView;
    private TextView tvSumWriting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent intent = this.getIntent();
        edtID.setText(intent.getStringExtra("id"));
        edtID.setEnabled(false);
        edtName.setText(intent.getStringExtra("name"));
        edtDate.setText(intent.getStringExtra("date"));
        edtTime.setText(intent.getStringExtra("time"));
        String status= intent.getStringExtra("status");
        String check = "Writing";

        if(status!= null && status.equals(check)){
            checkBoxStatus.setChecked(true);
        } else {
            checkBoxStatus.setChecked(false);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adpater = new RecycleViewAdapter();
        sqlExamHelper = new SQLExamHelper(this);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int date = calendar.get(Calendar.DAY_OF_MONTH);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtDate.setText( dayOfMonth+ "/" +(month+1)+ "/" +year);
                    }
                }, year, month, date);
                datePickerDialog.show();
            }
        });

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        edtTime.setText(""+hourOfDay+":"+minute);
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = 0;
                String status;
                try{
                    id = Integer.parseInt(edtID.getText().toString());
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }

                String name = edtName.getText().toString();
                String date = edtDate.getText().toString();
                String time = edtTime.getText().toString();
                if(checkBoxStatus.isChecked()){
                    status = "Writing";
                } else {
                    status = "Non-Writing";
                }

                sqlExamHelper.add(new Exam(id, name, date, time, status));
                Toast.makeText(getApplicationContext(),"Success adding exam!",Toast.LENGTH_LONG).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
                String curentDate = simpleDateFormat.format(calendar1.getTime());
                if(curentDate.compareTo(edtDate.getText().toString()) > 0) {
                    int id = 0;
                    try {
                        id = Integer.parseInt(edtID.getText().toString());
                    } catch (NumberFormatException e) {
                        System.out.println(e);
                    }
                    sqlExamHelper.delete(id);
                    edtID.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Success deleting exam!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Date thi < Date Cureent", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtID.setEnabled(false);
                ArrayList<Exam> arrExam = sqlExamHelper.show();
                adpater.setArrExam(arrExam);
                recyclerView.setAdapter(adpater);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Exam> arrExam = sqlExamHelper.getExamByName(newText);
                adpater.setArrExam(arrExam);
                recyclerView.setAdapter(adpater);
                return true;
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status;
                int id = Integer.parseInt(edtID.getText().toString().trim());
                String name = edtName.getText().toString().trim();
                String date = edtDate.getText().toString().trim();
                String time = edtTime.getText().toString().trim();
                if(checkBoxStatus.isChecked()){
                    status = "Writing";
                } else {
                    status = "Non-Writing";
                }
                sqlExamHelper.edit(new Exam(id, name, date, time, status));
                sqlExamHelper.close();
                edtID.setEnabled(true);
                Toast.makeText(getApplicationContext(),"Success editing exam!",Toast.LENGTH_LONG).show();
            }
        });

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = sqlExamHelper.getNumberWriting();
                tvSumWriting.setText(count+" exams use writing!");
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        edtID = findViewById(R.id.edt_id);
        edtName = findViewById(R.id.edt_name);
        edtDate = findViewById(R.id.edt_calendar);
        edtTime = findViewById(R.id.edt_time);
        checkBoxStatus = findViewById(R.id.checkboxstatus);
        btnAdd = findViewById(R.id.btn_add);
        btnShow = findViewById(R.id.btn_show);
        btnSum = findViewById(R.id.btn_count);
        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);
        searchView = findViewById(R.id.searchview);
        recyclerView = findViewById(R.id.recycleview);
        tvSumWriting = findViewById(R.id.tv_sum);
    }
}