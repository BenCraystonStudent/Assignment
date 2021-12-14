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
    private RecyclerView pRecyclerView;
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
        pRecyclerView = pView.findViewById(R.id.recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(pContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        pRecyclerView.setLayoutManager(llm);
        pAdapter = new PortfolioListAdapter( pContext, coins);
        pRecyclerView.setAdapter(pAdapter);

        return pView;
    }
}




