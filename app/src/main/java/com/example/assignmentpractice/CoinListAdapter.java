package com.example.assignmentpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.CoinViewHolder>  {

    private final LinkedList<String> mCoinList;
    private LayoutInflater mInflater;
    private List<Coin> mCoins;

    public CoinListAdapter(Context context, LinkedList<String> CoinList) {
        mInflater = LayoutInflater.from(context);
        this.mCoinList = CoinList;
    }

    @Override
    public CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.coinlist_item, parent, false);
        return new CoinViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(CoinViewHolder holder, int position) {
        if (mCoinList != null) {
            String mCurrent = mCoinList.get(position);
            holder.CoinItemView.setText(mCurrent);
        } else {
            holder.CoinItemView.setText("No Word");
        }
    }

    void setCoins(List<Coin> coins){
        mCoins = coins;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCoins != null)
            return mCoins.size();
        else return 0;
    }

    class CoinViewHolder extends RecyclerView.ViewHolder {
        public final TextView CoinItemView;
        final CoinListAdapter mAdapter;

        public CoinViewHolder(View itemView, CoinListAdapter adapter) {
            super(itemView);
            CoinItemView = itemView.findViewById(R.id.Coin);
            this.mAdapter = adapter;
        }
    }
}
