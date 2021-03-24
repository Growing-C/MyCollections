package com.cgy.myaidl;

import android.os.Bundle;
import android.os.RemoteException;

import androidx.appcompat.app.AppCompatActivity;

public class ServerActivity extends AppCompatActivity {

    private final IRemoteService.Stub binder = new IRemoteService.Stub() {
        @Override
        public void askSomething(String content) throws RemoteException {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
    }
}