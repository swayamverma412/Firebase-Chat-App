package com.example.chat_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Myviewholder>{

    private Context context;
    private List<MessageModel> MessageModelList;


    public MessageAdapter(Context context) {
        this.context = context;
        MessageModelList=new ArrayList<>();

    }

    public void add(MessageModel messageModel){
        MessageModelList.add(messageModel);
        notifyDataSetChanged();
    }

    public void clear(){
        MessageModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        MessageModel messageModel=MessageModelList.get(position);
        holder.msg.setText(messageModel.getMessage());
        if(messageModel.getSenderId().equals(FirebaseAuth.getInstance().getUid())){
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.teal_700));
            holder.msg.setBackgroundColor(context.getResources().getColor(R.color.white));

        }else{
            holder.main.setBackgroundColor(context.getResources().getColor(R.color.black));
            holder.msg.setBackgroundColor(context.getResources().getColor(R.color.white));

        }
    }

    @Override
    public int getItemCount() {
        return MessageModelList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder{
        private TextView msg;
        private LinearLayout main;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            msg=itemView.findViewById(R.id.message);
            main=itemView.findViewById(R.id.mainMesageLayout);
        }
    }
}
