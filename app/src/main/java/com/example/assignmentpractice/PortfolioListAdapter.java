package com.example.assignmentpractice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PortfolioListAdapter extends RecyclerView.Adapter<PortfolioListAdapter.CoinViewHolder> {

    private final LayoutInflater mInflater;
    private CoinViewModel cvm;
    private List<Coin> mCoins = new ArrayList<>(); //new list for Favourites
    PortfolioListAdapter adapter = this;
    Context context;

    public PortfolioListAdapter(Context context, List<Coin> mCoins)
    {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public Coin getCoin(int position) {
        return mCoins.get(position);
    }

    @Override
    public CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.coin_list_items_port, parent, false);
        return new CoinViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CoinViewHolder holder, int position) {
        if (mCoins != null) {
            Coin current = mCoins.get(position);
            holder.CoinItemView.setText(current.mCoin);
            holder.AmountHeld.setText(current.mCurrencyHeld.toString());
            holder.CoinCurrency.setText(current.mCurrency);
            holder.CoinValue.setText(current.mValue.toString());
            holder.UnfavImage.setImageResource(R.drawable.ic_unfav);
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

    class CoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView CoinItemView;
        private final TextView AmountHeld;
        private final TextView CoinCurrency;
        private final TextView CoinValue;
        private final ImageButton UnfavImage;
        private final ImageButton InfoImage;


        private CoinViewHolder(View itemView) {
            super(itemView);
            CoinItemView = itemView.findViewById(R.id.Coin);
            AmountHeld = itemView.findViewById(R.id.AmountHeld);
            CoinCurrency = itemView.findViewById(R.id.CoinCurrency);
            CoinValue = itemView.findViewById(R.id.CoinValue);
            UnfavImage = itemView.findViewById(R.id.unfavImage);
            InfoImage = itemView.findViewById(R.id.infoImagePort);

            UnfavImage.setOnClickListener(this);
            InfoImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.unfavImage:
                int position = getAdapterPosition();
                Coin coin = adapter.getCoin(position);

                Intent removeCoinIntent = new Intent(context, RemoveCoinActivity.class);
                removeCoinIntent.putExtra("coinName_info_remove", coin.mCoin);
                context.startActivity(removeCoinIntent);
                break;

                case R.id.infoImagePort:
                    int position_port_info = getAdapterPosition();
                    Coin coin_port_info = adapter.getCoin(position_port_info);
                    Intent startCoinInfoPort = new Intent(context, CoinInfoPortfolio.class);
                    startCoinInfoPort.putExtra("coinName_port_info", coin_port_info.mCoin);
                    context.startActivity(startCoinInfoPort);
                    break;
            }
        }

    }
}

