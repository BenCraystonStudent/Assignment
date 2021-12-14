package com.example.assignmentpractice;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class AllCurrencies extends Fragment {
    private final LinkedList<String> mCoinList = new LinkedList<>();
    private final LinkedList<Coin> coins = new LinkedList<>();
    private RecyclerView aRecyclerView;
    private CoinListAdapter aAdapter;
    private View aView;
    private Context aContext;
    private CoinViewModel mCoinViewModel;

    public AllCurrencies() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        CoinViewModel mCoinViewModel = new ViewModelProvider(this).get(CoinViewModel.class);
        
        aView = inflater.inflate(R.layout.fragment_all_currencies, container, false);
        aContext = container.getContext();
        aRecyclerView = aView.findViewById(R.id.recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(aContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        aRecyclerView.setLayoutManager(llm);
        aAdapter = new CoinListAdapter((AppCompatActivity) aContext, coins);
        aRecyclerView.setAdapter(aAdapter);

        mCoinViewModel.getAllCoins().observe(getViewLifecycleOwner(), new Observer<List<Coin>>()
        {
            @Override
            public void onChanged(@Nullable final List<Coin> coins) {
                // Update the cached copy of the words in the adapter.
                aAdapter.setCoins(coins);
            }
        });

        return aView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity();
    }
}




