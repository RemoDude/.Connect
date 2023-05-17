package com.example.lnctu_connect.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lnctu_connect.R;

public class roughtwo_viewholder extends RecyclerView.ViewHolder {
   public TextView commentname , comment_time,Comment_text;
    public  de.hdodenhof.circleimageview.CircleImageView profile;
    LinearLayoutCompat linearLayoutCompat;
    RecyclerView ChildRecyclerView;
    public RecyclerView.LayoutManager manager;

    public roughtwo_viewholder(@NonNull View itemView) {
        super(itemView);

        commentname = itemView.findViewById(R.id.user_name_comment);
        Comment_text = itemView.findViewById(R.id.comment_comment);
//comment_time = itemView.findViewById(R.id.timestamp_post)
        profile = itemView.findViewById(R.id.profile_comment);
        linearLayoutCompat = itemView.findViewById(R.id.linearlayout_reply_line);
        ChildRecyclerView = itemView.findViewById(R.id.recycler_view_reply);

//        manager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL,false);
//        ChildRecyclerView.setLayoutManager(manager);
    }
}
