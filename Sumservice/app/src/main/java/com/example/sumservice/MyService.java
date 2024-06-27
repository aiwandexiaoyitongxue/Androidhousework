package com.example.sumservice;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;

public class MyService extends Service {

    private LocalBinder binder = new LocalBinder();

    public MyService service() {
        return MyService.this;
    }
    // 内部类，用于提供与客户端通信的接口
    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    public IBinder onBind(Intent intent) {
        return  binder; // 不需要绑定，所以返回 null
    }

    // 自定义方法：实现两个数相加
    public int add(int num1, int num2) {
        return num1 + num2;
    }
    public void onCreate() {
        super.onCreate();
        //在服务创建的时候就创建方法
      //  binder = new LocalBinder();
    }

}

