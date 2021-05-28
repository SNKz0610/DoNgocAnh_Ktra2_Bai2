package com.snkz.dongocanh_ktra2_bai2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private ArrayList<Exam> arrExam;

    public RecycleViewAdapter() {
        this.arrExam = arrExam;
    }

    public void setArrExam(ArrayList<Exam> arrExam) {
        this.arrExam = arrExam;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_exam, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, int position) {
        Exam exam = arrExam.get(position);
        if (exam == null){
            return;
        } else {
            holder.tvID.setText("ID: " + exam.getId());
            holder.tvName.setText("Name: " + exam.getName());
            holder.tvDate.setText("Date: " + exam.getDate());
            holder.tvTime.setText("Time begins: " + exam.getTime());
            holder.tvStatus.setText("Status: " + exam.getStatus());

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);
                    intent.putExtra("id", String.valueOf(exam.getId()));
                    intent.putExtra("name", exam.getName());
                    intent.putExtra("date", exam.getDate());
                    intent.putExtra("time", exam.getTime());
                    intent.putExtra("status", exam.getStatus());

                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(arrExam!=null){
            return arrExam.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvID, tvName, tvDate, tvTime, tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_idexam);
            tvName = itemView.findViewById(R.id.tv_nameexam);
            tvDate = itemView.findViewById(R.id.tv_dateexam);
            tvTime = itemView.findViewById(R.id.tv_timeexam);
            tvStatus = itemView.findViewById(R.id.tv_statusexam);
        }
    }
}
