package com.example.sumservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText num1;
    private EditText num2;
    private Button btnSum;
    private MyService MyService;
    private boolean isBound = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (EditText) findViewById(R.id.etNum1);
        num2 = (EditText) findViewById(R.id.etNum2);
        btnSum = (Button) findViewById(R.id.btnSum);

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClick(v);  // 调用 add 方法
            }
        });
        ServiceConnection serviceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.LocalBinder binder = (MyService.LocalBinder) service;
                MyService = binder.getService();
                isBound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                isBound = false;
            }
        };
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    private void onAddButtonClick(View v) {
        if (isBound && MyService != null) {
            String strNum1 = num1.getText().toString();
            String strNum2 = num2.getText().toString();

            if (!strNum1.isEmpty() && !strNum2.isEmpty()) {
                int number1 = Integer.parseInt(strNum1);
                int number2 = Integer.parseInt(strNum2);
                int result = MyService.add(number1, number2);

                Toast.makeText(this, "计算结果: " + result, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "请输入两个数值", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Service not bound", Toast.LENGTH_SHORT).show();
        }
    }
}