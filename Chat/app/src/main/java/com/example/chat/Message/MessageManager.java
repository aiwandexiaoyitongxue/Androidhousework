package com.example.chat.Message;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chat.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
public class MessageManager {
    private DatabaseHelper dbHelper;

    public MessageManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // 添加消息到数据库
    public long addMessage(int senderId, int receiverId, String content) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SENDER_ID, senderId);
        values.put(DatabaseHelper.COLUMN_RECEIVER_ID, receiverId);
        values.put(DatabaseHelper.COLUMN_MESSAGE_CONTENT, content);

        long id = db.insert(DatabaseHelper.TABLE_MESSAGES, null, values);
        db.close();
        return id;
    }

    // 获取指定用户的所有消息
    public List<String> getAllMessages(int userId) {
        List<String> messages = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_MESSAGES
                + " WHERE " + DatabaseHelper.COLUMN_SENDER_ID+ " = " + userId
                + " OR " + DatabaseHelper.COLUMN_RECEIVER_ID + " = " + userId;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String messageContent = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MESSAGE_CONTENT));
                messages.add(messageContent);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return messages;
    }

    // 删除消息
    public void deleteMessage(long messageId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_MESSAGES, DatabaseHelper.COLUMN_MESSAGE_ID+ " = ?",
                new String[] { String.valueOf(messageId) });
        db.close();
    }

    // 撤回消息（本地删除）
    public void recallMessage(long messageId) {
        deleteMessage(messageId);
        // 同步更新对方聊天界面
        // 这里可以实现你的具体逻辑，比如发送一条撤回通知给对方
    }
}
