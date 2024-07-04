package com.example.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chat.Contact.UpdateContactActivity;
import com.example.chat.SessionManager.SessionManager;
import com.example.chat.database.DatabaseHelper;

public class PersonalActivity extends AppCompatActivity {
    private ImageView imageViewProfile;
    private TextView textViewNote, textViewCircle;
    private TextView unitphone;
    private Button buttonsendInfo, buttonModifyInfo;
    private String contactName; // 添加一个成员变量来保存联系人的名字
    private String contactPhone; // 添加一个成员变量来保存联系人的电话号码
    private int contactId;//保存联系人Id
    private int senderId;
    private static SessionManager sessionManager;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        // Initialize views
        imageViewProfile = findViewById(R.id.imageViewProfile);
        textViewNote = findViewById(R.id.textViewNote);
        sessionManager = new SessionManager(this);
       // textViewCircle = findViewById(R.id.textViewCircle);
        unitphone = findViewById(R.id.unitphone);
        buttonsendInfo = findViewById(R.id.buttonsendInfo);
        buttonModifyInfo = findViewById(R.id.buttonModifyInfo);

        // Get contact info from intent extras
        contactName = getIntent().getStringExtra("contactName");
        // Assume contactPhone can also be passed from intent extras if needed
        contactPhone = getIntent().getStringExtra("contactPhone");
        // Set data to views
        // Set contact name to appropriate TextView
        textViewNote.setText(contactName);

        // Set contact phone to EditText
        unitphone.setText(contactPhone);
        DatabaseHelper db = new DatabaseHelper(this);
        buttonsendInfo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // 获取所选联系人的信息，例如使用SessionManager或其他方式
                senderId = sessionManager.getCurrentUserId();
                contactId=db.findContactIdByNameAndPhone(contactName, contactPhone);
                //tring contactName = getContactName(); // 同上

                Log.v("test","senderId:"+senderId);
                Log.v("test","receiverId:"+contactId);

                // 创建Intent跳转到ChatActivity
                Intent intent = new Intent(PersonalActivity.this, ChatActivity.class);
                intent.putExtra("senderId",  senderId);
                intent.putExtra("receiverId", contactId);
                startActivity(intent);
            }
        });
        // Set click listener for modify info button
        buttonModifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open modify contact activity or handle modification
                // Example:
                openModifyContactActivity();
            }
        });

    }
    public void openModifyContactActivity() {
        DatabaseHelper db = new DatabaseHelper(this);
        int contactId = db.findContactIdByNameAndPhone(contactName, contactPhone);
        if (contactId != -1) {
            Intent intent = new Intent(this, UpdateContactActivity.class);
            intent.putExtra("contact_id", contactId);
            startActivity(intent);
        } else {
            Toast.makeText(this, "未找到联系人，请检查姓名和电话号码是否正确", Toast.LENGTH_LONG).show();
        }
    }
}