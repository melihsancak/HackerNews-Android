package com.example.hackernews_api;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchData extends AsyncTask<String, Float, List<String>> {
    //input,

    public List<Integer> fetchId(String type) {
        String jsonContent = "";
        ArrayList<Integer> ıdList = new ArrayList<Integer>();
        try {
            URL url = new URL("https://hacker-news.firebaseio.com/v0/" + type + ".json?print=pretty");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                jsonContent += line;
            }


            JSONArray jsonArray = new JSONArray(jsonContent);
            for (int i = 0; i < jsonArray.length(); i++) {
                ıdList.add(i, jsonArray.getInt(i));

            }
        } catch (MalformedURLException malformedUrlException) {
            malformedUrlException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return ıdList;

    }

    public String fetchItem(int itemId) {

        String jsonContent = "";
        try {
            URL url = new URL("https://hacker-news.firebaseio.com/v0/item/"+itemId+".json?print=pretty");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                jsonContent += line;
            }

            return new JSONObject(jsonContent).getString("title");

        } catch (MalformedURLException malformedUrlException) {
            malformedUrlException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        return "$ error";
    }


    @Override
    public List<String> doInBackground(String... str) {

        List<String> data = new ArrayList<>();
        int counter=5;
        for (int id : fetchId(str[0])) {
            Log.d(getClass().getSimpleName(), "doInBackground: "+id);
            data.add(fetchItem(id));
            counter--;
            if (counter<0)
                break;
        }
        return data;

    }


    @Override
    protected void onProgressUpdate(Float... values) {
        super.onProgressUpdate(values);
    }
}
