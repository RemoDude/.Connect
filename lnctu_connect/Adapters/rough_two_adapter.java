package com.example.lnctu_connect.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lnctu_connect.MainActivity2;
import com.example.lnctu_connect.R;
import com.example.lnctu_connect.modalls.rough_two;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class rough_two_adapter extends RecyclerView.Adapter<rough_two_adapter.ViewHolder>{

    private List<rough_two> replyList;
    public Context context;

    public rough_two_adapter(MainActivity2 mActivity) {
        this.mActivity = mActivity;
    }

    private MainActivity2 mActivity;



    public interface OnReplyItemClickListener {
        void onReplyItemClick(View view, int position);
    }


    private OnReplyItemClickListener mListener;

    public void setOnReplyItemClickListener(OnReplyItemClickListener listener) {
        mListener = listener;
    }



    public rough_two_adapter(List<rough_two> replyList) {
        this.replyList = replyList;
    }

    public rough_two_adapter(Context context) {
        this.context = context;
    }

    public rough_two_adapter(String comment)
    {
    }

    @NonNull
    @Override
    public rough_two_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_comment_post, parent, false);
        return new rough_two_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rough_two_adapter.ViewHolder holder, int position) {

        context = holder.itemView.getContext();

        rough_two two = replyList.get(position);
holder.Comment_text.setText(two.getComment_reply());

        setProfileName(two.getScholar_id(), holder.commentname);


        long currentTime = System.currentTimeMillis();

// Calculate the time difference in milliseconds
        long timeDifference = currentTime - two.getTimestamp();
// Convert time difference to seconds, minutes, hours, days, weeks, months, and years
        long seconds = timeDifference / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        long months = days / 30; // Assumes a month has 30 days
        long years = days / 365; // Assumes a year has 365 days

//        reate a formatted string based on the calculated time difference
        String formattedTime;
        if (seconds < 60) {
            formattedTime = seconds + " seconds ago";
        } else if (minutes < 60) {
            formattedTime = minutes + " minutes ago";
        } else if (hours < 24) {
            formattedTime = hours + " hours ago";
        } else if (days < 7) {
            formattedTime = days + " days ago";
        } else if (weeks < 4) {
            formattedTime = weeks + " weeks ago";
        } else if (months < 12) {
            formattedTime = months + " months ago";
        } else {
            formattedTime = years + " years ago";
        }

        Log.d("TIME", "onBindViewHolder: " + formattedTime);

        holder.time.setText(formattedTime);


        holder.show_replies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onReplyItemClick(view, position);
                }
            }
        });


        setProfileImage(two.getScholar_id(), holder.profile);




    }
    private void setProfileImage(String scholar_id,de.hdodenhof.circleimageview.CircleImageView profile ) {

        DatabaseReference profile_image = FirebaseDatabase.getInstance().getReference()
                .child("users").child(scholar_id).child("profiles");

        profile_image.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String imageUrl = snapshot.child("image").getValue(String.class);

                Glide.with(context).load(imageUrl).into(profile);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void setProfileName(String scholar_id, TextView commentname) {
        DatabaseReference profile_image = FirebaseDatabase.getInstance().getReference()
                .child("users").child(scholar_id).child("profiles");

        profile_image.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("user__name").getValue(String.class);

                commentname.setText(name);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    public int getItemCount() {
        return replyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView commentname, comment_time, Comment_text, show_replies;
        de.hdodenhof.circleimageview.CircleImageView profile;
        LinearLayoutCompat linearLayoutCompat;
        RecyclerView ChildRecyclerView;
        TextView time, replier;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentname = itemView.findViewById(R.id.user_name_comment);
            Comment_text = itemView.findViewById(R.id.comment_comment);
//comment_time = itemView.findViewById(R.id.timestamp_post)
            profile = itemView.findViewById(R.id.profile_comment);
            linearLayoutCompat = itemView.findViewById(R.id.linearlayout_reply_line);
            ChildRecyclerView = itemView.findViewById(R.id.recycler_view_reply);
            show_replies = itemView.findViewById(R.id.replies);
            time = itemView.findViewById(R.id.timestamp);
            replier = itemView.findViewById(R.id.reply_name);



        }
    }
}
