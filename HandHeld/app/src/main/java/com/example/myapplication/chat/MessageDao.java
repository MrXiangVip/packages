package com.example.myapplication.chat;
// MessageDao.java
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    List<Message> getAllMessages();

    @Insert
    void insertMessage(Message message);

    @Query("DELETE FROM messages")
    void clearAllMessages();
}