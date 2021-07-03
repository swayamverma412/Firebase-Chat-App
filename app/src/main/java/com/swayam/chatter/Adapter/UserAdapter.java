package com.swayam.chatter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swayam.chatter.Activity.HomeActivity;
import com.swayam.chatter.Activity.chatActivity;
import com.swayam.chatter.Modelclass.User;
import com.swayam.chatter.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends RecyclerView.Adapter <UserAdapter.Viewholder>{
    Context homeActivity;
    ArrayList<User> userArrayList;

    public UserAdapter(HomeActivity homeActivity, ArrayList<User> userArrayList) {
        this.homeActivity=homeActivity;
        this.userArrayList=userArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(homeActivity).inflate(R.layout.item_user_row,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        User users= userArrayList.get(position);

        holder.user_name.setText(users.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Indent indent = new Indent(homeActivity, chatActivity.class);
                indent.putExtra(name:"name",users.getName());
                indent.putExtra(name:"ReciverImage",users.getImageuri());
                indent.putExtra(name:"uid",users.getUid());
                homeActivity.startActivity(indent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {
        ImageView user_profile;
        TextView user_name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            user_profile=itemView.findViewById(R.id.profile_image);
            user_name=itemView.findViewById(R.id.user_name);

        }
    }
}
