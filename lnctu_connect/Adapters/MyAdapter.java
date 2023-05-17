package com.example.lnctu_connect.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lnctu_connect.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<MyData> dataList = new ArrayList<>();

    public void setData(List<MyData> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyData data = dataList.get(position);
        holder.question_answer.setText(data.getAnswer());
        // Load image using Glide or another library
        Glide.with(holder.itemView.getContext())
                .load(data.getScholar_id())
                .into(holder.profile_of_users);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView profile_of_users;


        TextView question_answer, answer_time, answer_given_name;


        public ViewHolder(View view) {
            super(view);
            question_answer = itemView.findViewById(R.id.question_qna);

//            answer_given_name = itemView.findViewById(R.id.answer_question_qna);

            answer_time = itemView.findViewById(R.id.timestamp_qna);

            profile_of_users = itemView.findViewById(R.id.profile_qna);

            answer_given_name = itemView.findViewById(R.id.user_name_qna);
        }
    }
}
