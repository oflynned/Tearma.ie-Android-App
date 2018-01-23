package com.syzible.tearma.Deprecated.Services;

import android.os.AsyncTask;

import com.syzible.tearma.Common.Parser;
import com.syzible.tearma.Deprecated.Interfaces.NetworkActivity;

import org.json.JSONArray;
import org.json.JSONException;

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

public class Networking extends AsyncTask<Object, Object, JSONArray> {
    private NetworkActivity networkActivity;
    private HashMap<String, String> parameters;

    public Networking(NetworkActivity networkActivity, HashMap<String, String> parameters) {
        this.networkActivity = networkActivity;
        this.parameters = parameters;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONArray doInBackground(Object... params) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) (new URL(Parser.getURL(parameters))).openConnection();
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
                    return new JSONArray(writer.toString());
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
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        assert networkActivity != null;
        System.out.println(jsonArray);
        if (jsonArray != null)
            networkActivity.onSuccess(jsonArray);
        else networkActivity.onFailure();
    }
}
