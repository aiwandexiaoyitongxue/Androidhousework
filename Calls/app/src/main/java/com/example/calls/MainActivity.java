package com.example.calls;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Build;
public class MainActivity extends AppCompatActivity {
    private EditText phoneNum;
    private ImageButton btnCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(shouldAskPermissions())
        {
            askPermissions();
        }
        phoneNum= (EditText) findViewById(R.id.phoneNum);
        btnCall = findViewById(R.id.btn_call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+phoneNum.getText()));
                startActivity(intent);
            }
        });

    }
    protected boolean shouldAskPermissions(){
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    protected void askPermissions(){
        String[] permissions = {
                "android.permission.CALL_PHONE"
        };
        int requestCode = 200;
        requestPermissions(permissions,requestCode);
    }

}