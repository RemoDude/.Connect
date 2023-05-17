package com.example.lnctu_connect.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lnctu_connect.Questions_Answers;
import com.example.lnctu_connect.R;
import com.example.lnctu_connect.login.otp_login;
import com.example.lnctu_connect.login.registserd_using;
import com.example.lnctu_connect.modalls.qna_data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import stream.customalert.CustomAlertDialogue;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    public Context context;

    List<String[]> myDataList;
    List<qna_data> mydataList;
    //    int values = 0;
    private int values = 0;
    private int downvote_value = 0;

    private Window window;
    ValueEventListener valueEventListener, valueEventListener1;



    Long number;
    private Long likeCount;
    private Long downlikeCount;
    private boolean isLiked = false;

    String value;


    private DatabaseReference qnaRef;
    List<DataSnapshot> dataSnapshotList;


//    public MyRecyclerViewAdapter(List<qna_data> myDataList) {
//        this.mydataList = myDataList;
//    }


    public MyRecyclerViewAdapter(List<qna_data> mydataList, Window window) {
        this.mydataList = mydataList;
        this.window = window;
    }

    public MyRecyclerViewAdapter(DatabaseReference qnaRef, List<DataSnapshot> dataSnapshotList) {
        this.qnaRef = qnaRef;
        this.dataSnapshotList = dataSnapshotList;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_post_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {

        qna_data data = mydataList.get(position);


        context = holder.itemView.getContext();


        holder.question_asked.setText(data.getQuestion_qna_asked());

        holder.time_of_question.setText(data.getQna_timestamp_asked());
        holder.category_choosen.setText(data.getCategory_qna_asked());
        holder.username.setText(data.getUser_qna_name_asked());

////
//        holder.upvote.setText(String.valueOf(data.getUpvote()));
//        holder.downvote.setText(String.valueOf(data.getDownvote()));


        getLikesCount(data.getPushkey(), holder.upvote);
        getDisLikesCount(data.getPushkey(), holder.downvote);

        Glide.with(holder.profile_of_users.getContext())
                .load(data.getImage()).into(holder.profile_of_users);


        DatabaseReference LikesUpdate = FirebaseDatabase.getInstance().getReference().child("AllQnA").child(data.getPushkey()).child("Likes");
        DatabaseReference DisLikesUpdate = FirebaseDatabase.getInstance().getReference().child("AllQnA").child(data.getPushkey()).child("DisLike");


        LikesUpdate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.child(value.trim()).exists())
                {


//                    Toast.makeText(context, "Exists", Toast.LENGTH_SHORT).show();

                    holder.upimage.setImageResource(R.drawable.new_arrow_up_color);



                }

                else
                {
                    holder.upimage.setImageResource(R.drawable.new_arrow_up);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DisLikesUpdate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                if (snapshot.child(value.trim()).equals(value))
                if (snapshot.child(value.trim()).exists())
                {


//                    Toast.makeText(context, "Exists", Toast.LENGTH_SHORT).show();

                    holder.downimage.setImageResource(R.drawable.new_arrow_down_color);



                }

                else
                {
                    holder.downimage.setImageResource(R.drawable.downarrow);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        SharedPreferences sharedPreferences = context.getSharedPreferences("myKey", MODE_PRIVATE);

        value = sharedPreferences.getString("value", "");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);


        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, data.getQuestion_qna_asked(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, Questions_Answers.class);


                intent.putExtra("question_asked", data.getQuestion_qna_asked());
                intent.putExtra("username", data.getUser_qna_name_asked());
                intent.putExtra("time", data.getQna_timestamp_asked());
                intent.putExtra("profile", data.getImage());
                intent.putExtra("key", data.getPushkey());
                intent.putExtra("scholar_key", data.getScholar_id());

                view.getContext().startActivity(intent);


            }
        });


        holder.upvote_liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pushkey = data.getPushkey();

                HashMap<String, Object> obj = new HashMap<>();
                obj.put(value, true);


                DatabaseReference LikesUpdate = FirebaseDatabase.getInstance().getReference().child("AllQnA").child(pushkey).child("Likes");
                DatabaseReference DisLikesUpdate = FirebaseDatabase.getInstance().getReference().child("AllQnA").child(pushkey).child("DisLike");


                valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {



                        if (snapshot.child(value.trim()).exists()) {
                            LikesUpdate.child(value.trim()).removeValue();

                        }

                        else  {

                            LikesUpdate.updateChildren(obj);

                            holder.upimage.setImageResource(R.drawable.new_arrow_up_color);

                        }


                        LikesUpdate.removeEventListener(valueEventListener);
//                        DisLikesUpdate.removeEventListener(valueEventListener);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };


                valueEventListener1 = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.child(value.trim()).exists()) {
                            // If value exists in DisLike node, remove it
                            DisLikesUpdate.child(value.trim()).removeValue();

                            // Add the value to Likes node
                            LikesUpdate.updateChildren(obj);

                        }

//                        else
//                        {
//
//                            LikesUpdate.updateChildren(obj);
//
//                        }
                        DisLikesUpdate.removeEventListener(valueEventListener1);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                LikesUpdate.addListenerForSingleValueEvent(valueEventListener);
                DisLikesUpdate.addListenerForSingleValueEvent(valueEventListener1);



            }
        });



        holder.downvote_liear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pushkey = data.getPushkey();

                HashMap<String, Object> obj = new HashMap<>();
                obj.put(value, true);


                DatabaseReference LikesUpdate = FirebaseDatabase.getInstance().getReference().child("AllQnA").child(pushkey).child("Likes");
                DatabaseReference DisLikesUpdate = FirebaseDatabase.getInstance().getReference().child("AllQnA").child(pushkey).child("DisLike");


                valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {



                        if (snapshot.child(value.trim()).exists()) {
                            DisLikesUpdate.child(value.trim()).removeValue();

                        }

                        else  {

                            DisLikesUpdate.updateChildren(obj);

                        }


                        DisLikesUpdate.removeEventListener(valueEventListener);
//                        DisLikesUpdate.removeEventListener(valueEventListener);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };


                valueEventListener1 = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.child(value.trim()).exists()) {
                            // If value exists in DisLike node, remove it
                            LikesUpdate.child(value.trim()).removeValue();

                            // Add the value to Likes node
                            DisLikesUpdate.updateChildren(obj);
                        }

//                        else
//                        {
//
//                            LikesUpdate.updateChildren(obj);
//
//                        }
                        LikesUpdate.removeEventListener(valueEventListener1);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                DisLikesUpdate.addListenerForSingleValueEvent(valueEventListener);
                LikesUpdate.addListenerForSingleValueEvent(valueEventListener1);



            }
        });



//                                                                      Start part
//        holder.upvote_liner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String pushkey = data.getPushkey();
//                String scholarid = data.getScholar_id();
//
//
//
//                DatabaseReference LikeVote = FirebaseDatabase.getInstance().getReference("AllQuestions").child(pushkey).child("Likes");
//
//                DatabaseReference DownVote = FirebaseDatabase.getInstance().getReference("AllQuestions").child(pushkey).child("DisLikes");
//
//                DatabaseReference answersRef = FirebaseDatabase.getInstance().getReference("QnA/Questions").child(pushkey).child(scholarid);
//
//                DatabaseReference user_question = FirebaseDatabase.getInstance().getReference("users").child(valuess).child("Questions")
//                        .child(pushkey);
//
//                Map<String, Object> upvote = new HashMap<>();
//                upvote.put(valuess, "Likes");
//
//                LikeVote.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        String keys = snapshot.getKey();
//
//                        Long count = snapshot.getChildrenCount();
//                        Map<String, Object> increment = new HashMap<>();
//                        Map<String, Object> decrement = new HashMap<>();
//                        Map<String, Object> downvote_decrement = new HashMap<>();
//                        increment.put("upvote", ServerValue.increment(1));
//                        decrement.put("upvote", ServerValue.increment(-1));
//                        downvote_decrement.put("downvote", ServerValue.increment(-1));
//
////
//
//                        if (snapshot.child(valuess.trim()).exists()) {
//                            LikeVote.child(valuess).removeValue();
//
//                            answersRef.updateChildren(decrement);
//                            user_question.updateChildren(decrement);
//
//
////                                System.out.println(count);
//                        } else {
//
//                            LikeVote.updateChildren(upvote);
//                            answersRef.updateChildren(increment);
//                            user_question.updateChildren(increment);
//
//
//                            DownVote.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                                    if (snapshot.child(valuess.trim()).exists()) {
//
//                                        DownVote.child(valuess.trim()).removeValue();
//                                        answersRef.updateChildren(downvote_decrement);
//                                        user_question.updateChildren(downvote_decrement);
//
//
//                                    }
//
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//
//
////                                System.out.println(count);
//                        }
//
//                        notifyItemChanged(position);
////                            holder.upvote.setText(count.toString());
//                        System.out.println(count + " Aaeshi hai");
//                        Toast.makeText(context, count.toString(), Toast.LENGTH_SHORT).show();
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//            }
//        });


//        holder.downvote_liear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String pushkey = data.getPushkey();
//                String scholarid = data.getScholar_id();
//
//
//                DatabaseReference LikeVote = FirebaseDatabase.getInstance().getReference("AllQuestions").child(pushkey).child("Likes");
//
//
//                DatabaseReference DownVote = FirebaseDatabase.getInstance().getReference("AllQuestions").child(pushkey).child("DisLikes");
//
//                DatabaseReference answersRef = FirebaseDatabase.getInstance().getReference("QnA/Questions").child(pushkey).child(scholarid);
//
//                DatabaseReference user_question = FirebaseDatabase.getInstance().getReference("users").child(valuess).child("Questions")
//                        .child(pushkey);
//
//                Map<String, Object> upvote = new HashMap<>();
//                upvote.put(valuess, "DisLikes");
//
//
//                DownVote.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        Long count = snapshot.getChildrenCount();
//                        Map<String, Object> increment = new HashMap<>();
//                        Map<String, Object> decrement = new HashMap<>();
//                        Map<String, Object> upvote_decrement = new HashMap<>();
//
//                        increment.put("downvote", ServerValue.increment(1));
//                        decrement.put("downvote", ServerValue.increment(-1));
//                        upvote_decrement.put("upvote", ServerValue.increment(-1));
//
//                        if (snapshot.child(valuess.trim()).exists()) {
//
//                            DownVote.child(valuess.trim()).removeValue();
//
//                            answersRef.updateChildren(decrement);
//                            user_question.updateChildren(decrement);
//
//
//
//
//                        } else {
//
//
//                            DownVote.updateChildren(upvote);
//                            answersRef.updateChildren(increment);
//                            user_question.updateChildren(increment);
//
//
//
//                            LikeVote.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                                    if (snapshot.child(valuess.trim()).exists()) {
//
//                                        LikeVote.child(valuess.trim()).removeValue();
//                                        answersRef.updateChildren(upvote_decrement);
//                                        user_question.updateChildren(upvote_decrement);
//
//
//                                    }
//
//                                    notifyItemChanged(position);
//
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//
//
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//            }
//        });


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                @SuppressLint("RestrictedApi") MenuBuilder menuBuilder = new MenuBuilder(context);
                MenuInflater inflater = new MenuInflater(context);
                inflater.inflate(R.menu.menu, menuBuilder);

                boolean isOpt2Visible = data.getScholar_id().equals(value);
                MenuItem opt2MenuItem = menuBuilder.findItem(R.id.opt2);
                opt2MenuItem.setVisible(isOpt2Visible);
                // Create menu popup helper
                @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(context, menuBuilder, view);
                optionsMenu.setForceShowIcon(true);




                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.opt1: // Handle option1 Click


                                Toast.makeText(context, data.getScholar_id() + value, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(context, item.getItemId(), Toast.LENGTH_SHORT).show();

                                return true;


                            case R.id.opt2:

                                CustomAlertDialogue.Builder alert = new CustomAlertDialogue.Builder(context)
                                        .setStyle(CustomAlertDialogue.Style.DIALOGUE)
                                        .setCancelable(false)
                                        .setTitle("Alert!")
                                        .setMessage("Are you sure to delete this Question?")
                                        .setPositiveText("Yes")
                                        .setPositiveColor(stream.customalert.R.color.positive)
                                        .setPositiveTypeface(Typeface.DEFAULT_BOLD)
                                        .setOnPositiveClicked(new CustomAlertDialogue.OnPositiveClicked() {
                                            @Override
                                            public void OnClick(View view, Dialog dialog) {
                                                dialog.dismiss();

                                                FirebaseDatabase.getInstance().getReference().child("QnA")
                                                        .child("Questions").child(data.getPushkey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                FirebaseDatabase.getInstance().getReference("users").child(value).child("Questions")
                                                                        .child(data.getPushkey()).removeValue();


                                                            }
                                                        });


                                            }

                                        })
                                        .setNegativeText("No")
                                        .setNegativeColor(stream.customalert.R.color.positive)
                                        .setOnNegativeClicked(new CustomAlertDialogue.OnNegativeClicked() {
                                            @Override
                                            public void OnClick(View view, Dialog dialog) {
                                                dialog.dismiss();




                                            }
                                        })
                                        .setDecorView(window.getDecorView())
                                        .build();


                                alert.show();

                                Toast.makeText(context, data.getPushkey(), Toast.LENGTH_SHORT).show();
                                return true;

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

    private void getDisLikesCount(String pushkey, TextView downvote) {
        DatabaseReference DisLikesUpdate = FirebaseDatabase.getInstance().getReference().child("AllQnA").child(pushkey).child("DisLike");

        DisLikesUpdate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long likesCount = snapshot.getChildrenCount();
//                System.out.println(likesCount + " This is likes count");
                downvote.setText(String.valueOf(likesCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    private void getLikesCount(String pushkey, TextView upvote) {

        DatabaseReference LikesUpdate = FirebaseDatabase.getInstance().getReference().child("AllQnA").child(pushkey).child("Likes");
        DatabaseReference DisLikesUpdate = FirebaseDatabase.getInstance().getReference().child("AllQnA").child(pushkey).child("DisLike");

        LikesUpdate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long likesCount = snapshot.getChildrenCount();
//                System.out.println(likesCount + " This is likes count");
                upvote.setText(String.valueOf(likesCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });

    }


    @Override
    public int getItemCount() {
        return mydataList.size();
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView question_asked, time_of_question, category_choosen, username, upvote, downvote;
        de.hdodenhof.circleimageview.CircleImageView profile_of_users;
        LinearLayoutCompat linear, upvote_liner, downvote_liear;

        AppCompatImageView upimage, downimage;

        ImageView imageView;

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
            downvote_liear = itemView.findViewById(R.id.downvote_linear);
            imageView = itemView.findViewById(R.id.menu_button);

            upimage = itemView.findViewById(R.id.upvote_button);
            downimage = itemView.findViewById(R.id.downward_arrow);

        }


    }


}
