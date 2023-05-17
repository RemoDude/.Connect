package com.example.lnctu_connect.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lnctu_connect.R;
import com.example.lnctu_connect.modalls.ChildItem;
import android.content.Context;

import java.util.List;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder> {

    public ChildItemAdapter(Context context) {
        this.context = context;
    }

    Context context;

    private List<ChildItem> ChildItemList;

    // Constructor
    ChildItemAdapter(List<ChildItem> childItemList)
    {
        this.ChildItemList = childItemList;
    }


    @NonNull
    @Override
    public ChildItemAdapter.ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.reply_comment_post,
                        parent, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildItemAdapter.ChildViewHolder holder, int position) {

        ChildItem data = ChildItemList.get(position);

        context = holder.itemView.getContext();

        holder.Comment_text.setText(data.getComment_reply());
        holder.commentname.setText(String.valueOf(data.getTimestamp()));

    }

    @Override
    public int getItemCount() {
        return ChildItemList.size();
    }

    public class ChildViewHolder  extends RecyclerView.ViewHolder
    {
        TextView commentname , comment_time,Comment_text;
        de.hdodenhof.circleimageview.CircleImageView profile;
        LinearLayoutCompat linearLayoutCompat;



        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);

            commentname = itemView.findViewById(R.id.user_name_comment);
            Comment_text = itemView.findViewById(R.id.comment_comment);
//comment_time = itemView.findViewById(R.id.timestamp_post)
            profile = itemView.findViewById(R.id.profile_comment);
            linearLayoutCompat = itemView.findViewById(R.id.linearlayout_reply_line);
        }
    }
}
