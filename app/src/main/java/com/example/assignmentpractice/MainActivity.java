package com.example.assignmentpractice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //private final LinkedList<Coin> mCoinList = new LinkedList<>();
    private final LinkedList<Coin> mCoins = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private CoinListAdapter zAdapter;
    private CoinViewModel mCoinViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoinViewModel = new ViewModelProvider(this).get(CoinViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CoinListAdapter zAdapter = new CoinListAdapter(this, mCoins);
        recyclerView.setAdapter(zAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCoinViewModel.getAllCoins().observe(this, new Observer<List<Coin>>()
        {
            @Override
            public void onChanged(@Nullable final List<Coin> coins) {
                // Update the cached copy of the words in the adapter.
                zAdapter.setCoins(coins);
            }
        });
    }
}