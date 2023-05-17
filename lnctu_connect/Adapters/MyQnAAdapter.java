package com.example.lnctu_connect.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lnctu_connect.Questions_Answers;
import com.example.lnctu_connect.R;
import com.example.lnctu_connect.modalls.My_Question_Modal;
import com.example.lnctu_connect.modalls.qna_data;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MyQnAAdapter extends RecyclerView.Adapter<MyQnAAdapter.ViewHolder> {

    public static Context context;

    List<String[]> myDataList;
//    List<qna_data> mydataList;



    List<My_Question_Modal> mydataList;


    Integer number;

    private int likeCount = 0;
    private boolean isLiked = false;

    ValueEventListener valueEventListener;

    Integer likes;


    public MyQnAAdapter(List<My_Question_Modal> mydataList) {
        this.mydataList = mydataList;
    }

//    public  MyQnAAdapter(List<String[]> myDataList) {
//        this.myDataList = myDataList;
//    }

//    public MyQnAAdapter(List<qna_data> myDataList) {
//        this.mydataList = myDataList;
//    }





    // Create new views (invoked by the layout manager)
    @Override
    public MyQnAAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_post_profile, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        String[] item = myDataList.get(position);
//        qna_data data = mydataList.get(position);

        My_Question_Modal question_modal = mydataList.get(position);



        context = holder.itemView.getContext();


        holder.question_asked.setText(question_modal.getQuestion_qna_asked());
        holder.time_of_question.setText(question_modal.getQna_timestamp_asked());
        holder.category_choosen.setText(question_modal.getCategory_qna_asked());
        holder.username.setText(question_modal.getUser_qna_name_asked());
        String pushkey = question_modal.getPushkey();
        String scholar = question_modal.getScholar_id();

        holder.upvote.setText(String.valueOf(question_modal.getUpvote()));
        holder.downvote.setText(String.valueOf(question_modal.getDownvote()));


        System.out.println(pushkey);

//        holder.question_asked.setText(item[0]);
//        holder.time_of_question.setText(item[1]);
//        holder.category_choosen.setText(item[2]);
//        holder.username.setText(item[3]);
//        String pushkey = item[5];
//        String scholar = item[6];
//
//        holder.upvote.setText("item[7]");
//        holder.downvote.setText("item[8]");
//
//        holder.upvote.setText(item[7]);
//        holder.downvote.setText(item[8]);


        Glide.with(holder.profile_of_users.getContext())
                .load(question_modal.getImage()).into(holder.profile_of_users);

//        Glide.with(holder.profile_of_users.getContext())
//                .load(item[4]).into(holder.profile_of_users);

        DatabaseReference answersRef = FirebaseDatabase.getInstance().getReference("QnA/Questions");




        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, Questions_Answers.class);
//
                intent.putExtra("question_asked", question_modal.getQuestion_qna_asked());
                intent.putExtra("time", question_modal.getQna_timestamp_asked());
                intent.putExtra("username", question_modal.getUser_qna_name_asked());
                intent.putExtra("profile", question_modal.getImage());
                intent.putExtra("key", question_modal.getPushkey());
                intent.putExtra("scholar_key", question_modal.getScholar_id());


//                intent.putExtra("question_asked", item[0]);
//                intent.putExtra("time", item[1]);
//                intent.putExtra("username", item[3]);
//                intent.putExtra("profile", item[4]);
//                intent.putExtra("key", item[5]);
//                intent.putExtra("scholar_key", item[6]);
                view.getContext().startActivity(intent);


            }
        });

    }

    // Return the size of mDataList (invoked by the layout manager)
    @Override
    public int getItemCount() {
//        return myDataList.size();
        return mydataList.size();
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  question_asked, time_of_question,category_choosen , username, upvote, downvote;
        de.hdodenhof.circleimageview.CircleImageView profile_of_users;
        LinearLayoutCompat linear, upvote_liner;

        AppCompatImageView upimage;

        public ViewHolder(View itemView) {
            super(itemView);

            linear = itemView.findViewById(R.id.answer_question_qna);

            profile_of_users = itemView.findViewById(R.id.profile_qna);

            profile_of_users = itemView.findViewById(R.id.profile_qna);
            username = itemView.findViewById(R.id.user_name_qna);
            time_of_question = itemView.findViewById(R.id.timestamp_qna);
            category_choosen = itemView.findViewById(R.id.category_qna);
            question_asked = itemView.findViewById(R.id.question_qna);

            upvote = itemView.findViewById(R.id.upvote_waala_qna);
            downvote = itemView.findViewById(R.id.downvote_waala_qna);
            upvote_liner = itemView.findViewById(R.id.upvote_linear);

        }
    }
}
