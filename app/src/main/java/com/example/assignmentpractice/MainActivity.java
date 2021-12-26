package com.example.assignmentpractice;

import static com.example.assignmentpractice.CoinListAdapter.ADD_COIN_REQUEST;
import static com.example.assignmentpractice.CoinListAdapter.INSERTED_COIN_NAME;
import static com.example.assignmentpractice.CoinListAdapter.INSERTED_COIN_CURRENCY;
import static com.example.assignmentpractice.CoinListAdapter.INSERTED_COIN_VALUE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    //private final LinkedList<Coin> mCoinList = new LinkedList<>();
    private final LinkedList<Coin> mCoins = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private CoinListAdapter zAdapter;
    private CoinViewModel cvm;
    public ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    public static final int ADD_COIN_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tabs);
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
            Coin coin = new Coin(receivedCoinName, receivedCoinCurrency, coinValueDouble);
            cvm.insert(coin);
            Toast toast = Toast.makeText(MainActivity.this, receivedCoinName, Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == ADD_COIN_REQUEST && resultCode == RESULT_OK) {
            String receivedCoinName = getIntent().getStringExtra(CoinListAdapter.INSERTED_COIN_NAME);
            String receivedCoinCurrency = getIntent().getStringExtra(INSERTED_COIN_CURRENCY);
            Double receivedValue = getIntent().getDoubleExtra(INSERTED_COIN_VALUE, 1);

            Coin coin = new Coin(receivedCoinName, receivedCoinCurrency, receivedValue);
            cvm.insert(coin);
            Toast toast = Toast.makeText(getLayoutInflater().getContext(), intent.getStringExtra(INSERTED_COIN_NAME), Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(getLayoutInflater().getContext(), "WTF RICHARD", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}