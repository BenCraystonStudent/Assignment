package com.example.assignmentpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Insert;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.CoinViewHolder> implements CoinDAO {

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
            //TODO:StringUtils.capitalize(current.mCoin);
            holder.CoinItemView.setText(current.mCoin);
            holder.CoinCurrency.setText(current.mCurrency);
            holder.CoinValue.setText(current.mValue.toString());
            holder.BorderedFav.setImageResource(R.drawable.ic_favourite_border);
            AddFavouriteCurrency(holder, position);

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

    @Override
    public  void insert(Coin coin) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public LiveData<List<Coin>> getAllCoins() {
        return null;
    }


    class CoinViewHolder extends RecyclerView.ViewHolder {
        private final TextView CoinItemView;
        private final TextView CoinCurrency;
        private final TextView CoinValue;
        private final ImageButton BorderedFav;

        private CoinViewHolder(View itemView) {
            super(itemView);
            CoinItemView = itemView.findViewById(R.id.Coin);
            CoinCurrency = itemView.findViewById(R.id.CoinCurrency);
            CoinValue = itemView.findViewById(R.id.CoinValue);
            BorderedFav = itemView.findViewById(R.id.BorderedFav);
        }
    }

    public void AddFavouriteCurrency(CoinListAdapter.CoinViewHolder viewHolder, int position)
    {
        viewHolder.BorderedFav.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View row)
            {
                Coin coin = mCoins.get(position);
                insert(coin);
                notifyDataSetChanged();
                //TODO: Get reference to Database
            }

    });
    }
}
