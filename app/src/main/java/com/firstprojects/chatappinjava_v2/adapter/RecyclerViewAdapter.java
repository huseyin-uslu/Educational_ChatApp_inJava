package com.firstprojects.chatappinjava_v2.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstprojects.chatappinjava_v2.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<String> arrayList;
    private RecyclerViewAdapter.onClickUserToChatting listener;

    public interface onClickUserToChatting{
         default void setOnClickListener(String targetUser,View view){}
    }

    public RecyclerViewAdapter(ArrayList<String> arrayList,
                               RecyclerViewAdapter.onClickUserToChatting listener){

        this.arrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public @NotNull ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item,parent,false);
        return new RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.@NotNull ViewHolder holder, int position) {
        holder.Binding(listener,arrayList,position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

     public static class ViewHolder extends RecyclerView.ViewHolder {
         private final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView_inRecyclerViewItem);
        }
        public void Binding(onClickUserToChatting listener,
                            ArrayList<String> arrayList,
                            Integer position){
            textView.setText(arrayList.get(position));
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.setOnClickListener(arrayList.get(position),v);
                }
            });



        }
         public TextView getTextView() {
             return textView;
         }
    }
}
