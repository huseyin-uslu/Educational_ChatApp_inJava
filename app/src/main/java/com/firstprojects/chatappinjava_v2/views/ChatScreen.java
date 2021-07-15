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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firstprojects.chatappinjava_v2.R;
import com.firstprojects.chatappinjava_v2.adapter.RecyclerViewAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.println;

public class ChatScreen extends AppCompatActivity {

    protected String TAG = "ChatScreen";
    FirebaseDatabase db;
    DatabaseReference ref;
    Intent intent;
    ArrayList<String> list;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        //initialization
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        intent = getIntent();
        userName = intent.getStringExtra("kAdi");
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(list, new RecyclerViewAdapter.onClickUserToChatting() {
            @Override
            public void setOnClickListener(String targetUser, View view) {

                Intent intentToChatting = new Intent(ChatScreen.this,ChattingActivity.class);
                intentToChatting.putExtra("kAdi",userName);
                intentToChatting.putExtra("tUser",targetUser);
                startActivity(intentToChatting);
            }
        });
        getList();
        recyclerView.setAdapter(adapter);
    }


    public void getList() {
        ref.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Log.i(TAG, userName + " joined the chat!");
                if (!snapshot.getKey().equals(userName)) {
                    recyclerView.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    list.add(snapshot.getKey());
                    Log.i(TAG, snapshot.getKey() + " joined the chat!");
                    recyclerView.setVisibility(View.VISIBLE);
                }
                if(list.isEmpty()){
                    TextView textView = findViewById(R.id.textView_inChatScreen);
                    ProgressBar progressBar = findViewById(R.id.progressbar_inChatScreen);
                    textView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    TextView textView = findViewById(R.id.textView_inChatScreen);
                    ProgressBar progressBar = findViewById(R.id.progressbar_inChatScreen);
                    textView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onChildChanged(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
            }
        });
    }



}