package com.software.acuity.splick.utils;

import android.os.AsyncTask;

import com.software.acuity.splick.interfaces.IVolleyResponseWithRequestId;

public class AsyncTaskHandlerClass extends AsyncTask implements IVolleyResponseWithRequestId {

    String timePeriod = "";
    String type = "";

    public AsyncTaskHandlerClass(String timePeriod, String type) {

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    @Override
    public void networkResponse(String response, int requestId) {

    }
}
