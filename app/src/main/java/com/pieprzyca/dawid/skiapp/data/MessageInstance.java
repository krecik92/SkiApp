package com.pieprzyca.dawid.skiapp.data;

import java.util.Date;

/**
 * Created by Dawid on 30.07.2017.
 * Class containts every information about message in chat.
 */

public class MessageInstance {
    private String textMessage;
    private String messageUser;
    private long messsageTime;

    public MessageInstance(String textMessage, String messageUser) {
        this.textMessage = textMessage;
        this.messageUser = messageUser;
        messsageTime = new Date().getTime();
    }

    public MessageInstance() {
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMesssageTime() {
        return messsageTime;
    }

    public void setMesssageTime(long messsageTime) {
        this.messsageTime = messsageTime;
    }
}
