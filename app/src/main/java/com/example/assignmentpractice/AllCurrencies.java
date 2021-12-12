package com.example.assignmentpractice;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class AllCurrencies extends Fragment {
    private final LinkedList<String> mCoinList = new LinkedList<>();
    private RecyclerView mRecyclerView;
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

        mRecyclerView = aView.findViewById(R.id.recyclerview);
    }
}




