package com.firstprojects.chatappinjava_v2.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firstprojects.chatappinjava_v2.R;
import com.firstprojects.chatappinjava_v2.adapter.ChattingViewAdapter;
import com.firstprojects.chatappinjava_v2.adapter.RecyclerViewAdapter;
import com.firstprojects.chatappinjava_v2.models.MessageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class ChattingActivity extends AppCompatActivity {
    //declaration
    protected Intent getData,intent;
    protected TextView targetUserName;
    protected ImageButton backButton,sendButton;
    protected EditText typing;
    protected String uName;
    protected String tName;
    protected FirebaseDatabase  firebaseDatabase;
    protected DatabaseReference reference;
    protected ArrayList<MessageModel> messagesList;
    protected RecyclerView recyclerView;
    protected ChattingViewAdapter adapter;


    protected void initialization() {
        //data intent
        getData = getIntent();
        //Log.i("ChattingActivity",getData.getStringExtra("kAdi") + getData.getStringExtra("tUser"));
        //view
        targetUserName = findViewById(R.id.targetUserNameOfChatting);
        //define target Username
        backButton = findViewById(R.id.backButtonOfChatting);
        sendButton = findViewById(R.id.sendButtonOfChatting);
        typing = findViewById(R.id.editTextOfChatting);
        //variables
        uName = getData.getExtras().getString("kAdi","");
        tName = getData.getExtras().getString("tUser","");
        //firebase & database
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        //adapter && recycler
        loadMessages();
        recyclerView = findViewById(R.id.recyclerViewOfChatting);
        messagesList = new ArrayList<MessageModel>();
        adapter = new ChattingViewAdapter(messagesList,uName,tName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        initialization();

        //binding
        targetUserName.setText(tName);

        //setOnBindingButton
        backButton.setOnClickListener(v ->
                finish()
        );
        sendButton.setOnClickListener(v -> {

            String text = typing.getText().toString();
            if(text.length() != 0){
                texting(text);
            }
        }
        );
    }

    protected void texting(String text) {
        String id = KeyMakerForMessages();
        HashMap<String,String> messagingMap = new HashMap<>();
        messagingMap.put("Text",text);
        messagingMap.put("From",uName);
        reference.child("Messages")
                .child(uName)
                .child(tName)
                .child(id)
                .setValue(messagingMap).addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()){ //first step
                        Log.i("ChattingActivity","first step is success for messaging." + tName + uName);
                        reference
                                .child("Messages")
                                .child(tName)
                                .child(uName)
                                .child(id)
                                .setValue(messagingMap).addOnCompleteListener(task2 -> {
                                    if(task2.isSuccessful()){ //last step
                                        Log.i("ChattingActivity","messaging is okey");
                                        typing.setText("");

                                    }
                                });
                    }
                });

    }
    protected void loadMessages() {
        reference.child("Messages").child(uName).child(tName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                MessageModel message = snapshot.getValue(MessageModel.class);
                messagesList.add(message);
                adapter.notifyDataSetChanged();
                assert message != null;
                Log.i("ChattingActivity","LastMessages = " + message.getText().toString());
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    protected String KeyMakerForMessages(){
        Random random = new Random();
        Integer a = random.nextInt();
        Integer b = random.nextInt();
        return  "from"+
                uName +
                "to"  +
                tName +
                "_date="+
                Calendar.getInstance().getTimeInMillis() + "r=" + a * b;
    }
}
