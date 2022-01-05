package com.example.assignmentpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PortfolioListAdapter extends RecyclerView.Adapter<PortfolioListAdapter.CoinViewHolder> {

    private final LayoutInflater mInflater;
    private List<Coin> mCoins = new ArrayList<>(); //new list for Favourites

    public PortfolioListAdapter(Context context, List<Coin> mCoins)
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
            holder.AmountHeld.setText(current.mCurrencyHeld.toString());
            holder.CoinCurrency.setText(current.mCurrency);
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
        private final TextView AmountHeld;
        private final TextView CoinCurrency;


        private CoinViewHolder(View itemView) {
            super(itemView);
            CoinItemView = itemView.findViewById(R.id.Coin);
            AmountHeld = itemView.findViewById(R.id.AmountHeld);
            CoinCurrency = itemView.findViewById(R.id.CoinCurrency);
        }
    }
}
