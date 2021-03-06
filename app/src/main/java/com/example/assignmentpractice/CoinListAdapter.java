package com.example.assignmentpractice;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.CoinViewHolder> {

    public static final String INSERTED_COIN_NAME = "com.example.assignmentpractice.INSERTED_COIN_NAME";
    public static final String INSERTED_COIN_CURRENCY = "com.example.assignmentpractice.INSERTED_COIN_CURRENCY";
    public static final String INSERTED_COIN_VALUE = "com.example.assignmentpractice.INSERTED_COIN_VALUE";
    public static final int ADD_COIN_REQUEST = 1;
    private final LayoutInflater mInflater;
    private List<Coin> mCoins; // Cached copy of Coins
    Context context;
    CoinListAdapter adapter = this;
    private Activity activity;
    private Context mContext;

    public CoinListAdapter(Context context, List<Coin> mCoins) {
        super();
        mInflater = LayoutInflater.from(context);
        this.activity = activity;
        this.context = context;
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
            holder.InfoImage.setImageResource(R.drawable.ic_info);

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
        private final ImageButton InfoImage;

        private CoinViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            CoinItemView = itemView.findViewById(R.id.Coin);
            CoinCurrency = itemView.findViewById(R.id.CoinCurrency);
            CoinValue = itemView.findViewById(R.id.CoinValue);
            FavImage = itemView.findViewById(R.id.FavImage);
            InfoImage = itemView.findViewById((R.id.InfoImage));

            FavImage.setOnClickListener(this);
            InfoImage.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.FavImage:
                int position = getAdapterPosition();
                Coin coin = adapter.getCoin(position);

                Intent intent = new Intent("addCoin");
                intent.putExtra("coinName", coin.mCoin);
                intent.putExtra("coinCurrency", coin.mCurrency);
                String stringedDouble = coin.mValue.toString();
                intent.putExtra("coinValue", stringedDouble);

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                mCoins.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                break;

                case R.id.InfoImage:
                    int position_info = getAdapterPosition();
                    Coin coin_info = adapter.getCoin(position_info);

                    Intent startCoinInfo = new Intent(context, CoinInfo.class);
                    startCoinInfo.putExtra("coinName_info", coin_info.mCoin);
                    context.startActivity(startCoinInfo);

                  //  Intent intent_info = new Intent("getCoinInfo");
                  //  intent_info.putExtra("coinName_info", coin_info.mCoin);

                   // LocalBroadcastManager.getInstance((Activity) context).sendBroadcast(intent_info);

                   // ((Activity) context).startActivity(new Intent(context, CoinInfo.class));
                    break;

       }

        }
    }
}


