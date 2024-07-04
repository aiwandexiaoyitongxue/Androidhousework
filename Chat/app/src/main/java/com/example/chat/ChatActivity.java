package com.example.chat;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat.Adapter.MessageAdapter;
import com.example.chat.Message.Message;
import com.example.chat.SessionManager.SessionManager;
import com.example.chat.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private Button buttonSend;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private int senderId; // ID of the logged-in user
    private int receiverId; // ID of the chat recipient
    private DatabaseHelper databaseHelper;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // 初始化 SessionManager
        sessionManager = new SessionManager(this);

        // 从 SessionManager 获取当前登录的用户ID
       // senderId = sessionManager.getCurrentUserId();

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);
        // 获取传递过来的senderId和receiverId
        senderId = getIntent().getIntExtra("senderId", 0);
        receiverId = getIntent().getIntExtra("receiverId", 0); // 获取接收者ID

        Log.v("test","senderId 1:"+senderId);
        Log.v("test","receiverId 1:"+receiverId);

        // Initialize UI components
        recyclerViewMessages = findViewById(R.id.recycler_chat_messages);

        editTextMessage = findViewById(R.id.edit_text_message);
        buttonSend = findViewById(R.id.button_send);

        // Set up RecyclerView
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList,this);
        recyclerViewMessages.setAdapter(messageAdapter);

        // Load messages from database
        loadMessages();

        // Set click listener for send button
        buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendMessage();
            }
        });

    }
    private void loadMessages() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        // 只查询当前对话的消息
        String[] columns = { "id", "sender_id", "receiver_id", "content", "timestamp" };
        String selection = "(sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?)";
        String[] selectionArgs = new String[] {
                String.valueOf(senderId),
                String.valueOf(receiverId),
                String.valueOf(receiverId),
                String.valueOf(senderId)
        };
        Cursor cursor = db.query("messages", columns, selection, selectionArgs, null, null, "timestamp DESC");
        messageList.clear();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int messageId = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int senderId = cursor.getInt(cursor.getColumnIndex("sender_id"));
                @SuppressLint("Range") int receiverId = cursor.getInt(cursor.getColumnIndex("receiver_id"));
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
                @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex("timestamp"));

                Message message = new Message(messageId, senderId, receiverId, content, timestamp);
                messageList.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        messageAdapter.notifyDataSetChanged();
        recyclerViewMessages.scrollToPosition(messageList.size() - 1); // Scroll to bottom
    }

    private void sendMessage() {
        String messageContent = editTextMessage.getText().toString().trim();
        if (!messageContent.isEmpty()) {
            // 确保发送消息时使用正确的senderId和receiverId
            long messageId = databaseHelper.addMessage(senderId, receiverId, messageContent);
            if (messageId != -1) {
                // Successfully added message to database
                long currentTimestamp = System.currentTimeMillis();
                Message message = new Message((int) messageId, senderId, receiverId, messageContent, null);

                Log.v("test",senderId+"-"+message.getSenderId());
                messageList.add(0, message); // 插入到列表开头
                messageAdapter.notifyItemInserted(0);
                recyclerViewMessages.scrollToPosition(0);
                editTextMessage.setText("");
            } else {
                Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }
}