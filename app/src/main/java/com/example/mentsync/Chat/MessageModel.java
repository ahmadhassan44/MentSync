package com.example.mentsync.Chat;

public class MessageModel {
    private String messageId;
    private String message;
    private String receiverid;
    private String senderid;
    private String timestamp;

    public MessageModel() {
        // Default constructor required for calls to DataSnapshot.getValue(MessageModel.class)
    }

    public MessageModel(String messageId, String message, String receiverid, String senderid, String timestamp) {
        this.messageId = messageId;
        this.message = message;
        this.receiverid = receiverid;
        this.senderid = senderid;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public String getSenderid() {
        return senderid;
    }

    public String getTimestamp() {
        return timestamp;
    }
    @Override
    public String toString() {
        return "MessageModel{" +
                "messageId='" + messageId + '\'' +
                ", message='" + message + '\'' +
                ", senderid='" + senderid + '\'' +
                ", receiverid='" + receiverid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
