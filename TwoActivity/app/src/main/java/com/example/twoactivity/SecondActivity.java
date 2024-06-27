package com.example.twoactivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //通过intent传递
        Intent intent=getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("名字");
            String classnum = intent.getStringExtra("班级");
            String Sno = intent.getStringExtra("学号");


            TextView tvStudentInfo = findViewById(R.id.tv_student_info);
            String studentInfo = "姓名：" + name + "\n"
                    + "班级：" + classnum + "\n"
                    + "学号：" + Sno;
            tvStudentInfo.setText(studentInfo);
        }else {
            Toast.makeText(this, "未接收到有效的Intent", Toast.LENGTH_SHORT).show();
        }
         /*
        通过bundle传递
          Bundle bundle=getIntent().getExtras();
          String name = bundle.getStringExtra("名字");
          String classnum = bundle.getStringExtra("班级");
          int Sno = bundle.getIntExtra("学号", 0);
           Toast.makeText(this, "名字:" + name + "\n班级:" + classnum + "\n学号:" + Sno, Toast.LENGTH_LONG).show();
         */
    }
}