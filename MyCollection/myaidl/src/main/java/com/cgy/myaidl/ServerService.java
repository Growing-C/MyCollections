package com.cgy.myaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class ServerService extends Service {
    private final IRemoteService.Stub binder = new IRemoteService.Stub() {
        @Override
        public void askSomething(String content) throws RemoteException {

        }
    };

    public ServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}