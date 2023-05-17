package com.example.lnctu_connect.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lnctu_connect.R;
import com.example.lnctu_connect.modalls.Question_Answers_modal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_of_Answers extends RecyclerView.Adapter<Adapter_of_Answers.ViewHolder> {


    public static Context context;

    List<String[]> myDataList;
    List<String[]> datalist;
    String values;


    List<Question_Answers_modal> question_answers_List ;


    public Adapter_of_Answers(List<Question_Answers_modal> myDataList) {
        this.question_answers_List = myDataList;

    }



    @NonNull
    @Override
    public Adapter_of_Answers.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_recyclerview, parent, false);
        return new Adapter_of_Answers.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_of_Answers.ViewHolder holder, int position) {

        context = holder.itemView.getContext();


        Question_Answers_modal question_answers_modal = question_answers_List.get(position);


        holder.question_answer.setText(question_answers_modal.getAnswer());
        holder.answer_given_name.setText(question_answers_modal.getAnswer_name());
        holder.answer_time.setText(question_answers_modal.getAnswer_time());

        holder.upvotes.setText(String.valueOf(question_answers_modal.getUpvotes()));
        holder.downvotes.setText(String.valueOf(question_answers_modal.getDownvotes()));


        Glide.with(holder.profile_of_users.getContext())
                .load(question_answers_modal.getImage()).into(holder.profile_of_users);


        SharedPreferences sharedPreferences = context.getSharedPreferences("myKey", MODE_PRIVATE);

        values = sharedPreferences.getString("value", "");

holder.upvote_liner.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        String push = question_answers_modal.getPushkey();
        String Question_key = question_answers_modal.getQuestion_key();
String scholarid = question_answers_modal.getScholar_id();


        DatabaseReference LikeVote = FirebaseDatabase.getInstance().getReference("AllAnswers").child(push).child("Likes");
        DatabaseReference DownVote = FirebaseDatabase.getInstance().getReference("AllAnswers").child(push).child("DisLikes");

        DatabaseReference answersRef = FirebaseDatabase.getInstance().getReference("QnA/Answers").child(Question_key).child(push).child(scholarid);

        Map<String, Object> upvote = new HashMap<>();
        upvote.put( values, "Likes");

        LikeVote.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Long count = snapshot.getChildrenCount();

                Map<String, Object> increment = new HashMap<>();
                Map<String, Object> decrement = new HashMap<>();
                Map<String, Object> downvote_decrement = new HashMap<>();

                increment.put("upvotes", ServerValue.increment(1));
                decrement.put("upvotes", ServerValue.increment(-1));
                downvote_decrement.put("downvotes", ServerValue.increment(-1));

           String keys = snapshot.getKey();

                System.out.println(keys);

//                LikeVote.updateChildren(upvote);


                if (snapshot.child(values.trim()).exists())
                {

                    LikeVote.child(values.trim()).removeValue();

                    answersRef.updateChildren(decrement);


                }

                else
                {

                    LikeVote.updateChildren(upvote);
                    answersRef.updateChildren(increment);

                    DownVote.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            if (snapshot.child(values.trim()).exists()) {

                                DownVote.child(values.trim()).removeValue();
                                answersRef.updateChildren(downvote_decrement);

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }






//           if (push.equals(keys.trim()))
//           {
//
//               Toast.makeText(context, "exist", Toast.LENGTH_SHORT).show();
//               LikeVote.child(values).removeValue();
//
//           }
//
//           else
//           {
//               LikeVote.updateChildren(upvote);
//               Toast.makeText(context, "doesnt exist", Toast.LENGTH_SHORT).show();
//
//           }
//                if (snapshot.child(push.trim()).exists()) {
//                    LikeVote.child(push).removeValue();
//
//                    answersRef.updateChildren(decrement);
//
//
////                                System.out.println(count);
//                }
//
//                else
//                {
//                    LikeVote.updateChildren(upvote);
//                    answersRef.updateChildren(increment);
//
//
//                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
});




        holder.downvote_liear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String push = question_answers_modal.getPushkey();
                String Question_key = question_answers_modal.getQuestion_key();
                String scholarid = question_answers_modal.getScholar_id();


                DatabaseReference LikeVote = FirebaseDatabase.getInstance().getReference("AllAnswers").child(push).child("Likes");
                DatabaseReference DownVote = FirebaseDatabase.getInstance().getReference("AllAnswers").child(push).child("DisLikes");

                DatabaseReference answersRef = FirebaseDatabase.getInstance().getReference("QnA/Answers").child(Question_key).child(push).child(scholarid);

                Map<String, Object> upvote = new HashMap<>();
                upvote.put( values, "DisLikes");



                DownVote.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        Long count = snapshot.getChildrenCount();
                        Map<String, Object> increment = new HashMap<>();
                        Map<String, Object> decrement = new HashMap<>();
                        Map<String, Object> upvote_decrement = new HashMap<>();


                        increment.put("downvotes", ServerValue.increment(1));
                        decrement.put("downvotes", ServerValue.increment(-1));
                        upvote_decrement.put("upvotes", ServerValue.increment(-1));




                        if (snapshot.child(values.trim()).exists()) {

                            DownVote.child(values.trim()).removeValue();

                            answersRef.updateChildren(decrement);


                        }

                        else
                        {



                            DownVote.updateChildren(upvote);
                            answersRef.updateChildren(increment);
                            LikeVote.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    if (snapshot.child(values.trim()).exists()) {

                                        LikeVote.child(values.trim()).removeValue();
                                        answersRef.updateChildren(upvote_decrement);

                                    }

                                    notifyItemChanged(position);


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });




                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });






            }
        });















        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                @SuppressLint("RestrictedApi") MenuBuilder menuBuilder = new MenuBuilder(context);
                MenuInflater inflater = new MenuInflater(context);
                inflater.inflate(R.menu.menu, menuBuilder);

                // Create menu popup helper
                @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, view);
                optionsMenu.setForceShowIcon(true);

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.opt1: // Handle option1 Click
                                return true;
//                            case R.id.opt2: // Handle option2 Click
//                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {
                    }
                });

                // Show menu
                optionsMenu.show();

            }
        });




    }

    @Override
    public int getItemCount() {
        return question_answers_List.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView profile_of_users;

        ImageView imageView;
        TextView question_answer, answer_time, answer_given_name, upvotes, downvotes;
        LinearLayoutCompat linear, upvote_liner, downvote_liear;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            question_answer = itemView.findViewById(R.id.question_qna);



            answer_time = itemView.findViewById(R.id.timestamp_qna);

            profile_of_users = itemView.findViewById(R.id.profile_qna);

            answer_given_name = itemView.findViewById(R.id.user_name_qna);

            upvotes = itemView.findViewById(R.id.upvotes_qna);
            downvotes = itemView.findViewById(R.id.downvotes_qna);

            imageView = itemView.findViewById(R.id.menu_button);

            upvote_liner = itemView.findViewById(R.id.upvote_linear);
            downvote_liear = itemView.findViewById(R.id.downvote_linear);



        }
    }
}
