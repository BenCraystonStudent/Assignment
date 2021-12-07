package com.example.assignmentpractice;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

public class FavCurrencies extends Fragment {

    private final LinkedList<String> mCoinList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private CoinListAdapter zAdapter;
    private CoinViewModel mCoinViewModel;

    public FavCurrencies() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       /* for (int i = 0; i < 20; i++) {
            mCoinList.addLast("Scribble " + i);
        }
        */
        mCoinViewModel = new ViewModelProvider(this).get(CoinViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_all_currencies, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        zAdapter = new CoinListAdapter(getContext(), );
        recyclerView.setAdapter(zAdapter);

        mCoinViewModel.getAllCoins().observe(getViewLifecycleOwner(), new Observer<List<Coin>>() {
            @Override
            public void onChanged(@Nullable final List<Coin> coins) {
                // Update the cached copy of the words in the adapter.
                zAdapter.setCoins(coins);
            }
        });

        return rootView;
    }
}