package com.firstprojects.chatappinjava_v2.views;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firstprojects.chatappinjava_v2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public EditText signInWithYourNickName;
    public Button signInButton;
    public FirebaseDatabase db;
    public DatabaseReference ref;
    public String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing
        signInWithYourNickName = findViewById(R.id.signInWithYourNickName);
        signInButton = findViewById(R.id.signInButton);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();

    }


    public void signInButton (View view) {
        String kAdi = signInWithYourNickName.getText().toString();
        if(kAdi.length() > 0){
            addUser(kAdi);
            Log.i(TAG,"Gotten user name");
        }else{
            Log.w(TAG,"Enter user name");
        }
    }

    public void addUser(final String kAdi){
        Log.i(TAG,"kAdi opened");
        if(3 <= signInWithYourNickName.length() && signInWithYourNickName.length() <= 15){
        ref.child("Users").child(kAdi).child("kullanici").setValue(kAdi).addOnCompleteListener(task -> {
            Log.i(TAG,"kAdi listeners opened");
            if(task.isSuccessful()){
                Toast.makeText(MainActivity.this,kAdi + " joined succesfull",Toast.LENGTH_SHORT).show();
                Log.i(TAG,"The users signed in succesfully");
                Intent intent = new Intent(MainActivity.this, ChatScreen.class);
                   intent.putExtra("kAdi",kAdi);
                   startActivity(intent);
                   finish();
                //send user to chat activity
            }else if(task.isCanceled()){
                Log.i(TAG,"DONE BUT THERE IS A PROBLEM");
                String exception = Objects.requireNonNull(task.getException()).getLocalizedMessage();

                if(exception != null){
                    Toast.makeText(MainActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    Log.w(TAG,"There is a problem " + exception);
                }
            }else if(task.isComplete()){
                Log.i(TAG,"COMPLETED");
            }
        });
        }else{
            signInButton.setTextColor(ContextCompat.getColor(this,R.color.red_700));
        }
    }
}