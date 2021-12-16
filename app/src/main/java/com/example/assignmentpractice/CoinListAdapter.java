package com.example.assignmentpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.CoinViewHolder> {

    private final LayoutInflater mInflater;
    private List<Coin> mCoins; // Cached copy of Coins

    public CoinListAdapter(Context context, List<Coin> mCoins)
    {
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.coinlist_item, parent, false);
        return new CoinViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CoinViewHolder holder, int position) {
        if (mCoins != null) {
            Coin current = mCoins.get(position);
            holder.CoinItemView.setText(current.mCoin);
        } else {
            // Covers the case of data not being ready yet.
            holder.CoinItemView.setText("No Coin");
        }
    }

    void setCoins(List<Coin> Coins){
        mCoins = Coins;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mCoins has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCoins != null)
            return mCoins.size();
        else return 0;
    }

    class CoinViewHolder extends RecyclerView.ViewHolder {
        private final TextView CoinItemView;

        private CoinViewHolder(View itemView) {
            super(itemView);
            CoinItemView = itemView.findViewById(R.id.Coin);
        }
    }
}
