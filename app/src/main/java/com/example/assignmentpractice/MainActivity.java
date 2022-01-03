package com.example.assignmentpractice;

import static com.example.assignmentpractice.CoinListAdapter.ADD_COIN_REQUEST;
import static com.example.assignmentpractice.CoinListAdapter.INSERTED_COIN_NAME;
import static com.example.assignmentpractice.CoinListAdapter.INSERTED_COIN_CURRENCY;
import static com.example.assignmentpractice.CoinListAdapter.INSERTED_COIN_VALUE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private final LinkedList<Coin> searchedCoins = new LinkedList<>();
    private final LinkedList<Coin> mCoins = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private CoinListAdapter sAdapter;
    private CoinViewModel cvm;
    public ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    public static final int ADD_COIN_REQUEST = 1;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tabs);
        searchView = findViewById(R.id.search_view);
        searchView.setQueryHint("Search Currencies....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, CurrencySearch.class);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(CoinReceiver, new IntentFilter("addCoin"));
        cvm = new ViewModelProvider(this).get(CoinViewModel.class);
        TabLayout mainTabs = findViewById(R.id.all_currencies);
        mViewPager = findViewById(R.id.all_currencies_viewpager);
        mainTabs.addTab(mainTabs.newTab().setText(R.string.tab_label1));
        mainTabs.addTab(mainTabs.newTab().setText(R.string.tab_label2));
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mainTabs.getTabCount());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
        mainTabs.setupWithViewPager(mViewPager);
        mainTabs.setSelectedTabIndicatorColor(Color.BLUE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Fragment fragment = mPagerAdapter.getItem(position);
        fragment.onResume();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public BroadcastReceiver CoinReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receivedCoinName = intent.getStringExtra("coinName");
            String receivedCoinCurrency = intent.getStringExtra("coinCurrency");
            String receivedCoinValue = intent.getStringExtra("coinValue");
            Double coinValueDouble = Double.parseDouble(receivedCoinValue);
            Coin coin = new Coin(receivedCoinName, receivedCoinCurrency, coinValueDouble, 0.0);
            cvm.insert(coin);
            Toast toast = Toast.makeText(MainActivity.this, receivedCoinName, Toast.LENGTH_SHORT);
            toast.show();
        }
    };


}
