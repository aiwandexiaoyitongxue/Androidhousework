package com.example.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chat.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button buttonRegister;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        databaseHelper = new DatabaseHelper(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    // 检查用户是否已存在
                    if (databaseHelper.checkUser(username)) {
                        Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        // 添加用户到数据库
                        long id = databaseHelper.addUser(username, password);
                        if (id > 0) {
                            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // 关闭当前注册页面
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}