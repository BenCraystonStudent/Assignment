package com.example.assignmentpractice;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

    public AllCurrencies() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        aView = inflater.inflate(R.layout.fragment_all_currencies, container, false);
        aContext = container.getContext();
        aRecyclerView = aView.findViewById(R.id.recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(aContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        aRecyclerView.setLayoutManager(llm);
        aAdapter = new CoinListAdapter((AppCompatActivity) aContext, coins);
        aRecyclerView.setAdapter(aAdapter);

        return aView;
    }
}




