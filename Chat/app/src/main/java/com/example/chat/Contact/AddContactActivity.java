package com.example.chat.Contact;

import android.os.Bundle;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.chat.Contact.Contact;
import com.example.chat.R;
import com.example.chat.SessionManager.SessionManager;
import com.example.chat.database.DatabaseHelper;

public class AddContactActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private EditText editTextName, editTextPhone;
    private Button buttonSave;
    private SQLiteDatabase database;
    private SessionManager sessionManager; // 管理用户Session的类
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // 获取 EditText 控件的引用
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);

        // 获取保存按钮的引用，并设置点击事件监听器
        buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 在这里处理保存操作
                String username =editTextName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                if (!username.isEmpty()) {
                    if (isUserRegistered(username)) {
                        // 用户已注册，执行添加联系人的逻辑
                        Contact contact = new Contact();
                        contact.setName(username);
                        contact.setPhone(phone);
                        addContact(contact);
                    } else {
                        // 用户未注册，显示提示
                        Toast.makeText(AddContactActivity.this, "该用户未注册", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // 初始化数据库相关变量
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase(); // 获取可写的数据库实例
    }
    private boolean isUserRegistered(String username) {
        // 调用DatabaseHelper中的方法检查用户是否存在
        return dbHelper.checkUser(username);
    }
    private void addContact(Contact contact) {
        long contactId = dbHelper.addContact(contact);
        if (dbHelper.wasInsertSuccessful(contactId)) {

            if (contactId == -1) {
                Toast.makeText(this, "添加联系人失败", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                // 这里假设当前用户的联系人 ID 是 1
                int currentUserId = sessionManager.getCurrentUserId();
                // 将联系人和自己建立好友关系
                establishFriendship(contactId, currentUserId);

                // 提示用户联系人添加成功，并清除输入框内容
                Toast.makeText(this, "联系人添加成功", Toast.LENGTH_SHORT).show();
                clearFields();
            }
        }
    }

    private void establishFriendship(long contactId1, long contactId2){


        ContentValues values = new ContentValues();
        values.put("contact1_id", contactId1);
        values.put("contact2_id", contactId2);

        long newRowId = database.insert("friends", null, values);

        // 创建 ContentValues 对象并插入第二对联系人（反向插入）
        ContentValues values2 = new ContentValues();
        values2.put("contact1_id", contactId2);
        values2.put("contact2_id", contactId1);

        // 插入第二对联系人到friends表
        long newRowId2 = database.insert("friends", null, values2);

        // 检查两次插入操作是否成功
        if (newRowId == -1 || newRowId2 == -1) {
            Toast.makeText(this, "Error establishing friendship", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Friendship established successfully", Toast.LENGTH_SHORT).show();
        }
    }
    private void clearFields() {
        // 清除输入框内容
        editTextName.getText().clear();
        editTextPhone.getText().clear();
    }
}