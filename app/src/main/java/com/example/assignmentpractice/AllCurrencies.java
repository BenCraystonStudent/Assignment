package com.example.assignmentpractice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

public class AllCurrencies extends Fragment {

    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

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
            mWordList.addLast("Word " + i);
        }

        View rootView = inflater.inflate(R.layout.fragment_all_currencies, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WordListAdapter(getContext(), mWordList);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }
}