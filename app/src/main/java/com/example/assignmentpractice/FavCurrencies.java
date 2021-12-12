package com.example.assignmentpractice;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class FavCurrencies extends Fragment {
    private final LinkedList<String> mCoinList = new LinkedList<>();
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
        pView = inflater.inflate(R.layout.fragment_all_currencies, container, false);

        mRecyclerView = pView.findViewById(R.id.recyclerview);
    }
}




