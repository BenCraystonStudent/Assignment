package com.example.assignmentpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.android.material.tabs.TabLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

//TODO: Add selected currency to the URI

public class CoinInfo extends AppCompatActivity {
    private TextView currencyDescription;
    private String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_info);
        try {
            getHTTPData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void getHTTPData() throws IOException {
        /* https://www.coingecko.com/api/documentations/v3#/ */
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(HttpUrl.parse("https://api.coingecko.com/api/v3/coins/bitcoin?localization=false&tickers=false&market_data=false&community_data=false&developer_data=false&sparkline=false"))
                        .newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                JSONObject graphData;

                final String myResponse = Objects.requireNonNull(response.body(), "Invalid Null API Response Received").string();

                Log.d("OkHTTPResponse", "API Call Successful");
                response.close();

                try {
                    JSONObject JSONData = new JSONObject(myResponse);
                    JSONObject data = (JSONObject) JSONData.get("description");
                    desc = data.getString("en");

                } catch (JSONException e) {
                    Log.d("OkHTTPResponse", "JSON Format Problem");
                    e.printStackTrace();
                }
                runOnUiThread(() -> {
                    Log.d("OkHTTPResponse", myResponse);
                    currencyDescription = findViewById(R.id.currencyDescription);
                    currencyDescription.setText(desc);
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("OkHTTPResponse","The API call for the coins failed: "
                        + e.getMessage());
                call.cancel();
            }


        });
    }
}

