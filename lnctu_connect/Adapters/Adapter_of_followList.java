package com.example.lnctu_connect.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lnctu_connect.R;
import com.example.lnctu_connect.Searched_Uses_Profile;
import com.example.lnctu_connect.modalls.My_Question_Modal;
import com.example.lnctu_connect.modalls.follow_list;

import java.util.List;

public class Adapter_of_followList extends RecyclerView.Adapter<Adapter_of_followList.ViewHolder> {

    List<follow_list> mydataList;

    public static Context context;


    public Adapter_of_followList(List<follow_list> mydataList) {
        this.mydataList = mydataList;
    }




    @NonNull
    @Override
    public Adapter_of_followList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follower_list, parent, false);
        return new Adapter_of_followList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_of_followList.ViewHolder holder, int position) {

        context = holder.itemView.getContext();

        follow_list follows = mydataList.get(position);

        holder.username.setText(follows.getUser__name());
        holder.justname.setText(follows.getUniq_name());

        Glide.with(context).load(follows.getImage())
                .into(holder.profile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                System.out.println(model.getUsername());

                Intent intent = new Intent(holder.itemView.getContext(), Searched_Uses_Profile.class);
                intent.putExtra("scholar_id", follows.getScholar_id());
                intent.putExtra("name", follows.getUser__name());
                view.getContext().startActivity(intent);


            }
        });



    }

    @Override
    public int getItemCount() {
        return mydataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        de.hdodenhof.circleimageview.CircleImageView profile;
        TextView username, justname;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profile_image);
            username = itemView.findViewById(R.id.username);
            justname = itemView.findViewById(R.id.justname);
        }
    }
}
