package com.example.assignmentpractice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.CoinViewHolder> {

    private final LayoutInflater mInflater;
    private List<Coin> mCoins; // Cached copy of Coins
    public CoinViewModel mCoinViewModel;
    Context context;
    CoinListAdapter adapter = this;

    public CoinListAdapter(Context context, List<Coin> mCoins) {
        super();
        mInflater = LayoutInflater.from(context);
    }

    public Coin getCoin(int position) {
        return mCoins.get(position);
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
            holder.CoinCurrency.setText(current.mCurrency);
            holder.CoinValue.setText(current.mValue.toString());
            holder.FavImage.setImageResource(R.drawable.ic_favourite);

            holder.FavImage.setTag(current);
        } else {
            // Covers the case of data not being ready yet.
            holder.CoinItemView.setText("No Coin");
        }
    }

    void setCoins(List<Coin> Coins) {
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


    class CoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView CoinItemView;
        private final TextView CoinCurrency;
        private final TextView CoinValue;
        private final ImageButton FavImage;

        private CoinViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            CoinItemView = itemView.findViewById(R.id.Coin);
            CoinCurrency = itemView.findViewById(R.id.CoinCurrency);
            CoinValue = itemView.findViewById(R.id.CoinValue);
            FavImage = itemView.findViewById(R.id.FavImage);

            FavImage.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            Coin coin = adapter.getCoin(position);

            Toast toast = Toast.makeText(context, coin.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}


