package com.example.assignmentpractice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    //private final LinkedList<Coin> mCoinList = new LinkedList<>();
    private final LinkedList<Coin> mCoins = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private CoinListAdapter zAdapter;
    private CoinViewModel mCoinViewModel;
    public ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tabs);
        TabLayout mainTabs = findViewById(R.id.all_currencies);
        mViewPager = findViewById(R.id.all_currencies_viewpager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mainTabs.setupWithViewPager(mViewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Fragment fragment = mPagerAdapter.getItem(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}