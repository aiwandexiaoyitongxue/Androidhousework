package com.example.twoactivity;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        //通过intent传递
        Button btnStudentinfo=findViewById(R.id.btn_student_info);
        btnStudentinfo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra("名字","姚利青");
                intent.putExtra("班级","软工2202");
                intent.putExtra("学号","2022011118");
                startActivity(intent);
            }
        });

        /*
        通过bundle传递
          Intent intent=new Intent();
          intent.setClass(this,SecondActivity.class)
          Bundle bundle=new Bundle();
          bundle.putString("名字","姚利青");
          bundle.putString("班级","软工2202");
          bundle.putInt("学号","2022011118");
          intent.putExtras(bundle);
           startActivity(bundle);
         */
    }
}