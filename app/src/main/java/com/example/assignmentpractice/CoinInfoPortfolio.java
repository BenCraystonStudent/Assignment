package com.example.assignmentpractice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import java.util.Calendar;

public class CoinInfoPortfolio extends AppCompatActivity {
    private Button buyButton, setButton;
    private TextView currencyHeader, currencyDescription, profit, now, difference;
    private ScrollView currencyDescriptionScrollable;
    private String desc;
    private String receivedCoinNameInfo;
    private String img;
    private ImageView coinImage;
    private Context context;
    private Drawable largeImg;
    private Toolbar coininfotoolbar;
    private EditText dialogInput;
    private CoinViewModel cvm;
    private BuyCurrency bc;
    private CoinDAO dao;
    public double gbpPrice, gotValue;
    private Double gbpPriceDbl, aDouble, theDifference;
    private Handler mHandler;


    //  public CoinInfo(Context context){
    //      this.context = context;
    //  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_info_portfolio);

//
        Bundle extras = getIntent().getExtras();
        receivedCoinNameInfo = extras.getString("coinName_port_info");
        cvm = new ViewModelProvider(this).get(CoinViewModel.class);
        coininfotoolbar = findViewById(R.id.coin_info_toolbar);
        currencyDescription = findViewById(R.id.currencyDescription);
        currencyHeader = findViewById(R.id.currencyHeader);
        currencyDescriptionScrollable = findViewById(R.id.currencyDescriptionScrollable);

        setButton = findViewById(R.id.setBtn);
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setThresholdsIntent = new Intent(CoinInfoPortfolio.this, BoundariesActivity.class);
                setThresholdsIntent.putExtra("thresholdsCoin", receivedCoinNameInfo);
                startActivity(setThresholdsIntent);
            }
        });


        buyButton = findViewById(R.id.buyBtn);

            buyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BuyCurrency bc = new BuyCurrency();
                    bc.DisplayBuyCurrency(CoinInfoPortfolio.this, receivedCoinNameInfo, (coin, amount) -> {
                        cvm.UpdateCurrencyHeld(coin, gbpPrice * amount);
                        finish();
                        Log.d("Show Coins", cvm.getAllCoins().toString());
                    });
                }
            });

        coinImage = (ImageView)findViewById(R.id.coinImagexml);
        setSupportActionBar(coininfotoolbar);
        // LocalBroadcastManager.getInstance(this).registerReceiver(InfoReceiver, new IntentFilter("getCoinInfo"));
        // Toast toast = Toast.makeText(CoinInfo.this, receivedCoinNameInfo, Toast.LENGTH_SHORT);
        // toast.show();

        profit = findViewById(R.id.profit);
        now = findViewById(R.id.nowPrice);
        difference = findViewById(R.id.difference);
        cvm.getVATOP(receivedCoinNameInfo).observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                profit.setText("Was: ??" + String.valueOf(aDouble) + " on purchase");
                mHandler = new Handler(Looper.getMainLooper())
                {
                    @Override
                    public void handleMessage(Message passedPrice){
                        gotValue = aDouble;
                        difference.setText("The difference is " + String.valueOf(gotValue - gbpPrice));
                        cvm.updatePrices(receivedCoinNameInfo, gbpPrice);
                    }
                };
            }
        });
        try {
            getHTTPData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // public BroadcastReceiver InfoReceiver = new BroadcastReceiver() {
    //     @Override
    //     public void onReceive(Context context, Intent intent_info) {
    //         receivedCoinNameInfo = intent_info.getStringExtra("coinName_info");
    //     }
    //  };


    void getHTTPData() throws IOException {
        /* https://www.coingecko.com/api/documentations/v3#/ */
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(HttpUrl.parse("https://api.coingecko.com/api/v3/coins/"+receivedCoinNameInfo+"?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false"))
                        .newBuilder();

        // urlBuilder.addQueryParameter("ids", receivedCoinName);

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                JSONObject graphData;

                final String myResponse = Objects.requireNonNull(response.body(), "Invalid Null API Response Received").string();

                Log.d("OkHTTPResponse", "API Call Successful");
                response.close();

                try {
                    JSONObject JSONData = new JSONObject(myResponse);
                    JSONObject data = (JSONObject) JSONData.get("description");
                    JSONObject imagedata = (JSONObject) JSONData.get("image");
                    JSONObject priceData = (JSONObject) JSONData.get("market_data");
                    JSONObject JSONPrices = (JSONObject) priceData.get("current_price");
                    gbpPrice = JSONPrices.getDouble("gbp");
                   // gbpPriceDbl = Double.parseDouble(gbpPrice);
                    img = imagedata.getString("large");
                    URL ImgURL = new URL(imagedata.getString("large"));
                    largeImg = Drawable.createFromStream(ImgURL.openStream(), img);

                    desc = data.getString("en");

                } catch (JSONException e) {
                    Log.d("OkHTTPResponse", "JSON Format Problem");
                    e.printStackTrace();
                }
                runOnUiThread(() -> {
                    Log.d("OkHTTPResponse", myResponse);
                    currencyDescription.setText(desc);
                    // currencyDescriptionScrollable.addView(currencyDescription);
                    coinImage.setImageDrawable(largeImg);
                    currencyHeader.setText(StringUtils.capitalize(receivedCoinNameInfo));
                    now.setText("Now: ??" + String.valueOf(gbpPrice));
                    Message message = mHandler.obtainMessage();
                    mHandler.handleMessage(message);
                });
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("OkHTTPResponse","The API call for the coins failed: "
                        + e.getMessage());
                call.cancel();
            }


        });
    }

    public void AddCrypto(View view) {
        Intent intent = new Intent("addCoin");
        intent.putExtra("coinName", receivedCoinNameInfo);
        intent.putExtra("coinCurrency", "gbp");
        // String stringedDouble = coin.mValue.toString();
        intent.putExtra("coinValue", gbpPrice);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        Toast toast = Toast.makeText(CoinInfoPortfolio.this, receivedCoinNameInfo + " added to Portfolio", Toast.LENGTH_SHORT);
        toast.show();

        finish();


    }
}

