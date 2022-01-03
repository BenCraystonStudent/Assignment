package com.example.assignmentpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrencySearch extends AppCompatActivity {
    private RecyclerView searchRecycler;
    private CoinListAdapter sAdapter;
    private LinkedList<Coin> searchedCoins = new LinkedList<>();
    private SearchView sView;
    private String received_query;
    public SearchManager searchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_search);

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        sAdapter  = new CoinListAdapter((AppCompatActivity) this, searchedCoins);
        searchRecycler = findViewById(R.id.recyclerview);
        searchRecycler.setLayoutManager(llm);
        searchRecycler.setAdapter(sAdapter);


        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            received_query = intent.getStringExtra(searchManager.QUERY);
        }

            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("https://api.coingecko.com/api/v3/simple/price?ids=" + received_query + "&vs_currencies=gbp").newBuilder());

            String url = urlBuilder.build().toString();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("OkHTTPResponse", "Nothing sent back from CoinGecko");
                    call.cancel();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String myResponse = Objects.requireNonNull(response.body(), "Response Received").string();
                    Log.d("OkHTTPResponse", "API Call Successful");
                    response.close();

                    try {
                        JSONObject oJSON = new JSONObject(myResponse);
                        searchedCoins.clear();

                        double CoinValue;
                        /* Build the list of coins from API Data */
                        for (Iterator<String> it = oJSON.keys(); it.hasNext(); ) {
                            String coinName = it.next();
                            CoinValue = oJSON.getJSONObject(coinName).getDouble("gbp");
                            searchedCoins.add(new Coin(coinName, "gbp", CoinValue, 0.0));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("OkHTTPResponse", "JSON Format Problem");
                    }

                    runOnUiThread(() -> {
                        Log.d("OkHTTPResponse", myResponse);
                        searchedCoins.sort(new SortByCoinName());
                        sAdapter.setCoins(searchedCoins);
                    });
                }
            });



        }
    }
