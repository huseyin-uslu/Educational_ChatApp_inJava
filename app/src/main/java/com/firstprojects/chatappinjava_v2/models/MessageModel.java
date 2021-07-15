package com.firstprojects.chatappinjava_v2.models;

public class MessageModel {
    private String From,Text;

    public MessageModel(){

    }

    public MessageModel(String from, String text) {
        From = from;
        Text = text;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
