package com.example.assignmentpractice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

public class FavCurrencies extends Fragment {

    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdapter zAdapter;

    public FavCurrencies() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        for (int i = 0; i < 20; i++) {
            mWordList.addLast("Scribble " + i);
        }

        View rootView = inflater.inflate(R.layout.fragment_all_currencies, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        zAdapter = new WordListAdapter(getContext(), mWordList);
        recyclerView.setAdapter(zAdapter);

        return rootView;
    }
}