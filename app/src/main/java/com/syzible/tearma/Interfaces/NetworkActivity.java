package com.syzible.tearma.interfaces;

import org.json.JSONArray;

/**
 * Created by ed on 30/10/2016
 */

public interface NetworkActivity {
    void onSuccess(JSONArray array);
    void onFailure();
}
