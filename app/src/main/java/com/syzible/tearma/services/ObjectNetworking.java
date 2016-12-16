package com.syzible.tearma.services;

import android.os.AsyncTask;

import com.syzible.tearma.interfaces.NetworkActivity;
import com.syzible.tearma.interfaces.ObjectNetworkActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by ed on 30/10/2016
 */

public class ObjectNetworking extends AsyncTask<Object, Object, JSONObject> {
    private ObjectNetworkActivity networkActivity;
    private String url;

    public ObjectNetworking(ObjectNetworkActivity networkActivity, String url) {
        this.networkActivity = networkActivity;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Object... params) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-length", "0");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.connect();

            int status = connection.getResponseCode();
            Writer writer = new StringWriter();

            switch (status) {
                case 200:
                case 201:
                    char[] buffer = new char[1024];
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    int n;
                    while ((n = br.read(buffer)) != -1) {
                        writer.write(buffer, 0, n);
                    }
                    br.close();
                    return new JSONObject(writer.toString());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        assert networkActivity != null;
        if(jsonObject != null)
            networkActivity.onSuccess(jsonObject);
        else networkActivity.onFailure();
    }
}
