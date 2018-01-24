package com.syzible.tearma.Common.Network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class RestClient {
    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        new AsyncHttpClient().get(url, null, responseHandler);
    }
}
