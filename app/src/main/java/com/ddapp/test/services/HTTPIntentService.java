package com.ddapp.test.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.ddapp.test.Constants;
import com.ddapp.test.StudentsManager;

public class HTTPIntentService extends IntentService {


    public HTTPIntentService() {
        super("HTTPIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(Constants.TAG, "onHandleIntent");
        StudentsManager.getInstance(this).loadSyncDataFromURL();

        Intent resultIntent = new Intent(Constants.ACTION);
        resultIntent.putExtra(Constants.STATUS, Constants.STATUS_COMPLETE);
        sendBroadcast(resultIntent);
        Log.d(Constants.TAG, "end");
    }


}
