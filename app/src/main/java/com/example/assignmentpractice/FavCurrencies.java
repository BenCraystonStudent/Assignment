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

public class FavCurrencies extends Fragment {
    private final LinkedList<String> mCoinList = new LinkedList<>();
    private final LinkedList<Coin> coins = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private PortfolioListAdapter pAdapter;
    private View pView;
    private Context pContext;

    public FavCurrencies() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        pView = inflater.inflate(R.layout.fragment_fav_currencies, container, false);
        pContext = container.getContext();
        mRecyclerView = pView.findViewById(R.id.recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(pContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        pAdapter = new PortfolioListAdapter((AppCompatActivity) pContext, coins);
        mRecyclerView.setAdapter(pAdapter);

        return pView;
    }
}




