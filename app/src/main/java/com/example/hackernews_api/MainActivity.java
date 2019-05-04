package com.example.hackernews_api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button click;
    ListView data;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = (Button) findViewById(R.id.button);
        data = (ListView) findViewById(R.id.dataViewId);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.item_layout);
        data.setAdapter(adapter);


        click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                FetchData process = new FetchData(progressBar);
                try {
                    List<String> dataList = process.execute("topstories").get();
                    for (String x : dataList) {
                        adapter.add(x);
                    }
                } catch (Exception e) {

                }

            }
        });
    }
}
