package com.example.lnctu_connect.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lnctu_connect.MainActivity2;

import com.example.lnctu_connect.R;
import com.example.lnctu_connect.modalls.rough_one;
import com.example.lnctu_connect.modalls.rough_two;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class rough_one_Adapter extends RecyclerView.Adapter<rough_one_Adapter.ViewHolder> {


    List<rough_one> oneList;
    List<rough_two> secondList = new ArrayList<>();
    rough_one_Adapter first_adapter;
    rough_two_adapter second_adapter;
    public Context context;
    String pushkey;


//    private OnDataPass onDataPass;

    public rough_one_Adapter(List<rough_one> oneList, String pushkey) {
        this.oneList = oneList;
        this.pushkey = pushkey;
    }

    public rough_one getItem(int position) {
        return oneList.get(position);
    }

    OnItemClickListener clickListener;


    public interface OnItemClickListener {
        void onItemClick(String pushKey);
    }


    private MainActivity2 mActivity;
    private OnCommentItemClickListener mListener;

    public interface OnCommentItemClickListener {
        void onCommentItemClick(View view, int position);

        void onCommentItemClick(View view, int position, int replyPosition);

        void onItemClick(String pushKey, String scholar_id);
    }


    public void setOnCommentItemClickListener(OnCommentItemClickListener listener) {
        mListener = listener;

    }


//
//    public rough_one_Adapter(List<rough_one> oneList) {
//        this.oneList = oneList;
//    }


    @NonNull
    @Override
    public rough_one_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_post, parent, false);
        return new rough_one_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        rough_one one = oneList.get(holder.getAdapterPosition());

        context = holder.itemView.getContext();

        holder.Comment_text.setText(one.getComment());

        long currentTime = System.currentTimeMillis();

// Calculate the time difference in milliseconds
        long timeDifference = currentTime - one.getTimestamp();
// Convert time difference to seconds, minutes, hours, days, weeks, months, and years
        long seconds = timeDifference / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        long months = days / 30; // Assumes a month has 30 days
        long years = days / 365; // Assumes a year has 365 days

// Create a formatted string based on the calculated time difference
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

        setProfileName(one.getScholar_id(), holder.commentname);


        holder.show_replies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onCommentItemClick(view, position);
                    mListener.onItemClick(one.getPushkey(), one.getScholar_id());
                }
            }
        });


        setProfileImage(one.getScholar_id(), holder.profile);
        DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference("posts").child(pushkey)
                .child("Comments").child(oneList.get(position).getPushkey().trim());

        postsRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                secondList.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {

//                        Log.d("TAG", "onDataChange: " + snapshot2.getKey());

                        for (DataSnapshot snapshot3 : snapshot2.getChildren()) {


                            if (snapshot3.getChildrenCount() > 0) {

                                holder.linearLayoutCompat.setVisibility(View.VISIBLE);
                                long keys = snapshot2.getChildrenCount();
                                String numbers = String.valueOf(keys);
                                holder.replies_numbers.setText(numbers);

                                Log.d("TAG", "onDataChange: " + snapshot3.getKey());

                            } else {
                                holder.linearLayoutCompat.setVisibility(View.GONE);
                            }


                        }


                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.linearLayoutCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.ChildRecyclerView.getVisibility() == View.GONE) {

                    holder.ChildRecyclerView.setVisibility(View.VISIBLE);


                    holder.indicator.setVisibility(View.VISIBLE);


                    String commentId = oneList.get(position).getPushkey();
                    DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference("posts").child(pushkey)
                            .child("Comments").child(commentId.trim());
//                    postsRef.keepSynced(true);

                    System.out.println(commentId);
                    postsRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            secondList.clear();

                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                for (DataSnapshot snapshot2 : snapshot1.getChildren()) {

                                    for (DataSnapshot snapshot3 : snapshot2.getChildren()) {


                                        rough_two roughTwo = snapshot3.getValue(rough_two.class);

                                        secondList.add(roughTwo);
                                        holder.indicator.setVisibility(View.GONE);

                                        Log.d("new", "onClick: " + one.getComment());

                                        second_adapter = new rough_two_adapter(secondList);

                                        second_adapter.setOnReplyItemClickListener(new rough_two_adapter.OnReplyItemClickListener() {
                                            @Override
                                            public void onReplyItemClick(View view, int reply) {
                                                if (mListener != null) {
                                                    mListener.onCommentItemClick(view, position, reply);
                                                }
                                            }
                                        });

                                        if (holder.ChildRecyclerView.getVisibility() == View.VISIBLE) {
                                            holder.replies_numbers.setText("Hide replies");
                                            holder.morereply.setText("");
                                            holder.viewreply.setText("");

                                        } else if (holder.ChildRecyclerView.getVisibility() == View.GONE) {
                                            long keys = snapshot2.getChildrenCount();
                                            String numbers = String.valueOf(keys);
                                            holder.replies_numbers.setText(numbers);


                                        }


                                        second_adapter.notifyItemInserted(secondList.size() - 1);

                                        holder.ChildRecyclerView.setAdapter(second_adapter);
                                        second_adapter.notifyDataSetChanged();

                                    }


                                }


                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

//                    holder.ChildRecyclerView.setVisibility(View.GONE);
                } else if (holder.ChildRecyclerView.getVisibility() == View.VISIBLE) {
//                    slideUp(holder.ChildRecyclerView);
                    holder.ChildRecyclerView.setVisibility(View.GONE);
                    DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference("posts").child(pushkey)
                            .child("Comments").child(oneList.get(position).getPushkey().trim());

                    postsRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            secondList.clear();

                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                                for (DataSnapshot snapshot2 : snapshot1.getChildren()) {

//                        Log.d("TAG", "onDataChange: " + snapshot2.getKey());

                                    for (DataSnapshot snapshot3 : snapshot2.getChildren()) {


                                        if (snapshot3.getChildrenCount() > 0) {

                                            holder.linearLayoutCompat.setVisibility(View.VISIBLE);
                                            long keys = snapshot2.getChildrenCount();
                                            String numbers = String.valueOf(keys);
                                            holder.replies_numbers.setText(numbers);
                                            holder.morereply.setText("more replies");
                                            holder.viewreply.setText("View");

                                            Log.d("TAG", "onDataChange: " + snapshot3.getKey());

                                        } else {
                                            holder.linearLayoutCompat.setVisibility(View.GONE);
                                        }


                                    }


                                }


                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

//                    holder.replies_numbers.setText("numbers");

                }

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
        return oneList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView commentname, comment_time, Comment_text, replies_numbers, morereply, viewreply, show_replies;
        de.hdodenhof.circleimageview.CircleImageView profile;
        LinearLayoutCompat linearLayoutCompat;
        RecyclerView ChildRecyclerView;
        CircularProgressIndicator indicator;
        TextView time;

        AppCompatImageButton button;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentname = itemView.findViewById(R.id.user_name_comment);
            Comment_text = itemView.findViewById(R.id.comment_comment);
comment_time = itemView.findViewById(R.id.timestamp_post);
            profile = itemView.findViewById(R.id.profile_comment);
            linearLayoutCompat = itemView.findViewById(R.id.linearlayout_reply_line);
            ChildRecyclerView = itemView.findViewById(R.id.recycler_view_reply);

            replies_numbers = itemView.findViewById(R.id.replies_count);
            indicator = itemView.findViewById(R.id.reply_load);
            viewreply = itemView.findViewById(R.id.hide_viewtext);
            morereply = itemView.findViewById(R.id.more_replies);
            show_replies = itemView.findViewById(R.id.replies);
            time = itemView.findViewById(R.id.timestamp);

        }
    }

    private void setProfileImage(String scholar_id, de.hdodenhof.circleimageview.CircleImageView profile) {

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

    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }


    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

}
