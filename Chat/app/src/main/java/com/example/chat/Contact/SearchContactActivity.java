package com.example.chat.Contact;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chat.database.DatabaseHelper;
import com.example.chat.R;

public class SearchContactActivity extends AppCompatActivity {
    private EditText searchEditText;
    private Button searchButton;
    private TextView textViewContactInfo;
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);

        // 初始化组件
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        textViewContactInfo = findViewById(R.id.textViewContactInfo);
        db = new DatabaseHelper(this);

        // 设置搜索按钮的点击事件
        searchButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    int contactId = Integer.parseInt(searchEditText.getText().toString());
                    Contact contact = db.getContact(contactId);
                    if (contact != null) {
                        textViewContactInfo.setText("联系人ID: " + contact.getId() + "\n姓名: " + contact.getName() + "\n电话: " + contact.getPhone());
                    } else {
                        textViewContactInfo.setText("未找到联系人");
                    }
                } catch (NumberFormatException e) {
                    textViewContactInfo.setText("请输入有效的ID");
                }
            }
        });
    }

    private Contact searchContactById(int contactId) {
        // 使用ID查询数据库
        return db.getContact(contactId);
    }
}