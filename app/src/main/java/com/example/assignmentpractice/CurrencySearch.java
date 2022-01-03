package com.example.assignmentpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    private LinkedList<Coin> searchedCoins;
    private SearchView sView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_search);


       // sView.setQueryHint("Search...");

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);


        LinearLayoutManager llm = new LinearLayoutManager(this);
        sAdapter  = new CoinListAdapter((AppCompatActivity) this, searchedCoins);
        searchRecycler = findViewById(R.id.recyclerview);
        searchRecycler.setLayoutManager(llm);
        searchRecycler.setAdapter(sAdapter);



        }
    }
}
