package com.firstprojects.chatappinjava_v2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstprojects.chatappinjava_v2.R;
import com.firstprojects.chatappinjava_v2.models.MessageModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChattingViewAdapter extends RecyclerView.Adapter<ChattingViewAdapter.MessageHolder> {

    private ArrayList<MessageModel> messagesArray;
    private String userName;
    private String targetName;

    public ChattingViewAdapter(ArrayList<MessageModel> messagesArray,
                               String userName,
                               String targetName){
        this.messagesArray = messagesArray;
        this.userName = userName;
        this.targetName = targetName;
    }


    @NonNull
    @NotNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == 0){ //user messages
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usermessages_item,parent,false);
        }else{             //target user messages
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.targetmessages_item,parent,false);
        }
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChattingViewAdapter.MessageHolder holder, int position) {
        holder.binding(position,getItemViewType(position),messagesArray);
    }

    @Override
    public int getItemViewType(int position) {
        String sender = messagesArray.get(position).getFrom();
        if(sender.equals(userName)){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return messagesArray.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        TextView text;

        public MessageHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        public void binding(int position,int viewType,ArrayList<MessageModel> model){
            if(viewType == 0){
                //users messages
                text = itemView.findViewById(R.id.usersMessageTextView);
            }else{
                //target users messages
                text = itemView.findViewById(R.id.targetUsersMessageTextView);
            }
            text.setText(model.get(position).getText());
        }
    }

}

