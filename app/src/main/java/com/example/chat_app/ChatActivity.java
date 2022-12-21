package com.example.chat_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.chat_app.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    String recieverId;
    DatabaseReference databaseReferenceSender,databaseReferenceReciever;
    String senderRoom, recieverRoom;
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


       recieverId= getIntent().getStringExtra("id");




       senderRoom= FirebaseAuth.getInstance().getUid()+recieverId;
       recieverId=recieverId+FirebaseAuth.getInstance().getUid();

       messageAdapter=new MessageAdapter(this);
       binding.recycler.setAdapter(messageAdapter);
       binding.recycler.setLayoutManager(new LinearLayoutManager(this));
       databaseReferenceSender= FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
       databaseReferenceReciever= FirebaseDatabase.getInstance().getReference("chats").child(recieverRoom);

       databaseReferenceSender.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                   MessageModel messageModel=dataSnapshot.getValue(MessageModel.class);
                   messageAdapter.add(messageModel);

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       binding.sendMessage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String message=binding.messageEd.getText().toString();
               if(message.trim().length()>0){
                   sendMessage(message);
               }
           }
       });
    }

    private void sendMessage(String message) {
        String messageId = UUID.randomUUID().toString();
        MessageModel messageModel= new MessageModel(messageId,FirebaseAuth.getInstance().getUid(),message);

        messageAdapter.add(messageModel);

        databaseReferenceSender
                .child(messageId)
                .setValue(messageModel);
        databaseReferenceReciever
                .child(messageId)
                .setValue(messageModel);



    }
}