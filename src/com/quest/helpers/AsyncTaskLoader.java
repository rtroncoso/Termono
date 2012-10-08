package com.quest.helpers;

import android.os.AsyncTask;

import com.quest.helpers.interfaces.IAsyncCallback;

public class AsyncTaskLoader extends AsyncTask<IAsyncCallback, Integer, Boolean> {

    // ===========================================================
    // Fields
    // ===========================================================

    IAsyncCallback[] _params;

    // ===========================================================
    // Inherited Methods
    // ===========================================================

    @Override
    protected Boolean doInBackground(IAsyncCallback... params) {
        this._params = params;
        int count = params.length;
        for(int i = 0; i < count; i++){
            params[i].workToDo();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        int count = this._params.length;
        for(int i = 0; i < count; i++){
            this._params[i].onComplete();
        }
    }
}