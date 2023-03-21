package com.devdroid.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.devdroid.mychat.databinding.ActivityChatBinding;
import com.devdroid.mychat.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class chatActivity extends AppCompatActivity {
ActivityChatBinding binding;
String reciverId;
String senderRoom,reciverRoom;
DatabaseReference databaseReferenceSender,databaseReferencReciver ;
MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        reciverId=getIntent().getStringExtra("id");

        senderRoom= FirebaseAuth.getInstance().getUid()+reciverId;
        reciverRoom=  reciverRoom+FirebaseAuth.getInstance().getUid();
        messageAdapter=new MessageAdapter(this);
        binding.recycler.setAdapter(messageAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));


        databaseReferenceSender= FirebaseDatabase.getInstance().getReference("chat").child(senderRoom);
        databaseReferencReciver= FirebaseDatabase.getInstance().getReference("chat").child(reciverRoom);
        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MessageModel messageModel=dataSnapshot.getValue(MessageModel.class);
                    messageAdapter.add(messageModel);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.sendMassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=binding.massageEd.getText().toString();
                if (message.trim().length()>0){
                    sendmessage(message);
                }
            }
        });

    }
    private void sendmessage (String message){
        String messageId= UUID.randomUUID().toString();
        MessageModel messageModel=new MessageModel(messageId,FirebaseAuth.getInstance().getUid(),message);

        messageAdapter.add(messageModel);
        databaseReferenceSender
                .child(messageId)
                .setValue(messageModel);
        databaseReferencReciver
                .child(messageId)
                .setValue(messageModel);


    }
}