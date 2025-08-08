package com.example.myapplication.chat;// Message.java
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "messages")
public class Message {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String content;
    private String role; // "user" 或 "ai"
    private long timestamp;

    // 构造方法
    public Message(String content, String role, long timestamp) {
        this.content = content;
        this.role = role;
        this.timestamp = timestamp;
    }

    // Getter和Setter
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getContent() { return content; }
    public String getRole() { return role; }
    public long getTimestamp() { return timestamp; }
}
