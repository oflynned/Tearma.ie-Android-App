package com.syzible.tearma.interfaces;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ed on 30/10/2016
 */

public interface ObjectNetworkActivity {
    void onSuccess(JSONObject object);
    void onFailure();
}
