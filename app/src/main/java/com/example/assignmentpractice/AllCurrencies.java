package com.example.assignmentpractice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

public class AllCurrencies extends Fragment {

    private final LinkedList<String> mCoinList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private CoinListAdapter mAdapter;
    private List<Coin> mCoins = new LinkedList<>();

    public AllCurrencies() {
        // Required empty public constructorbruh
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        for (int i = 0; i < 20; i++) {
            mCoinList.addLast("Coin " + i);
        }

        View rootView = inflater.inflate(R.layout.fragment_all_currencies, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CoinListAdapter(getContext(), (LinkedList<Coin>) mCoins);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }
}