package com.example.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
public class CoverPageActivity extends AppCompatActivity {

    private Button btnExperience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_page);

        // 找到 "立即体验" 按钮并设置点击事件
        findViewById(R.id.button_experience).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 创建一个意图启动 RegisterActivity
                Intent intent = new Intent(CoverPageActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}