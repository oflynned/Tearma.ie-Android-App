package com.syzible.tearma.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ed on 13/12/2016
 */

public class Helper {
    public static boolean hasInternet(Context context) {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        assert activeNetwork != null;
        return activeNetwork.getState() == NetworkInfo.State.CONNECTED;
    }
}
