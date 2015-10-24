package com.probuing.runremote;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.probuing.remote.ServiceInterface;

public class MainActivity extends Activity {

    private Intent intent;
    private ServiceConn conn;
    ServiceInterface si;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent();
        intent.setAction("com.probuing.REMOTE");
        intent.setPackage("com.probuing.remote");
        conn = new ServiceConn();
    }

    /**
     * 按钮点击事件
     */
    public void startRemote(View v) {
        startService(intent);
    }

    public void stopRemote(View v) {
        stopService(intent);
    }

    public void bindRemote(View v) {
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    public void unbindRemote(View v) {
        unbindService(conn);
    }

    /**
     * 调用远程服务的方法
     */
    public void callRemoteMethod(View v){
        try {
            si.callServiceMethod();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    class ServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("成功绑定");
            //直接从aidl获取对象
            si = ServiceInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}
