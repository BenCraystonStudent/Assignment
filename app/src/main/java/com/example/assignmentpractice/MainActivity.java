package com.example.assignmentpractice;

import static com.example.assignmentpractice.CoinListAdapter.ADD_COIN_REQUEST;
import static com.example.assignmentpractice.CoinListAdapter.INSERTED_COIN_NAME;
import static com.example.assignmentpractice.CoinListAdapter.INSERTED_COIN_CURRENCY;
import static com.example.assignmentpractice.CoinListAdapter.INSERTED_COIN_VALUE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private final LinkedList<Coin> searchedCoins = new LinkedList<>();
    private final LinkedList<Coin> mCoins = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private CoinListAdapter sAdapter;
    private CoinViewModel cvm;
    public ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    public static final int ADD_COIN_REQUEST = 1;
    public SearchView searchView;
    public SearchManager searchManager;
    private ImageButton userProfile;
    private Handler geckoHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tabs);


        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.search_view);
        searchView.setQueryHint("Search Currencies....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), CurrencySearch.class);
                intent.putExtra("query", query);
                intent.setAction(Intent.ACTION_SEARCH);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        userProfile = findViewById(R.id.userProfileBtn);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UserProfile.class);
                startActivity(i);
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(CoinReceiver, new IntentFilter("addCoin"));
        cvm = new ViewModelProvider(this).get(CoinViewModel.class);
        TabLayout mainTabs = findViewById(R.id.all_currencies);
        mViewPager = findViewById(R.id.all_currencies_viewpager);
        mainTabs.addTab(mainTabs.newTab().setText(R.string.tab_label1));
        mainTabs.addTab(mainTabs.newTab().setText(R.string.tab_label2));
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mainTabs.getTabCount());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
        mainTabs.setupWithViewPager(mViewPager);
        mainTabs.setSelectedTabIndicatorColor(Color.BLUE);

        geckoHandler.post(geckoCall);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Fragment fragment = mPagerAdapter.getItem(position);
        fragment.onResume();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public BroadcastReceiver CoinReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receivedCoinName = intent.getStringExtra("coinName");
            String receivedCoinCurrency = intent.getStringExtra("coinCurrency");
            String receivedCoinValue = intent.getStringExtra("coinValue");
            Double coinValueDouble = Double.parseDouble(receivedCoinValue);
            Coin coin = new Coin(receivedCoinName, receivedCoinCurrency, coinValueDouble, 0.0, 0.0, 0.0, coinValueDouble, "");
            cvm.insert(coin);
            Toast toast = Toast.makeText(MainActivity.this, receivedCoinName + " added to Portfolio", Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    public Runnable geckoCall = new Runnable() {
        @Override
        public void run() {

                /* https://www.coingecko.com/api/documentations/v3#/ */
                OkHttpClient client = new OkHttpClient();
                /* Set API URL */
                HttpUrl.Builder urlBuilder =
                        Objects.requireNonNull(HttpUrl.parse("https://api.coingecko.com/api/v3/simple/price"))
                                .newBuilder();
                /* Add coin list to be fetched could be a string resource */
                urlBuilder.addQueryParameter("ids",
                        "ampleforth,ankr,apollo,bancor,binancecoin,bitcoin,bitcoin-cash,cardano,chainlink,"
                                + "dash,ethereum,tether,polkadot,uniswap,litecoin,internet-computer,eos,"
                                + "the-graph,maker,numeraire,decentraland,sushi,filecoin");

                /* Add the returned currency parameter */
                urlBuilder.addQueryParameter("vs_currencies", "gbp");

                /* Build the URL with params */
                String url = urlBuilder.build().toString();

                /* Create an OkHTTP request object */
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                /* Add the request to the call queue for sending */
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        JSONObject oCoin;

                        final String myResponse = Objects.requireNonNull(response.body(), "Invalid Null API Response Received").string();
                        Log.d("OkHTTPResponse", "API Call Successful");
                        response.close();
                        /*
                        Format Array of key value pairs
                        Key = Coin Name
                        Value = Coin data object

                            Coin data object has one key value pair
                            Key   = the currency
                            Value = the value of the coin in that currency

                        Sample JSON
                        {
                        "filecoin":{"gbp":40.32},
                        "uniswap":{"gbp":14.89},
                        "ethereum":{"gbp":3303.62},
                        "chainlink":{"gbp":18.37},
                        "numeraire":{"gbp":29.89}
                        }
                         */
                        try {
                            JSONObject oJSON = new JSONObject(myResponse);

                            double CoinValue;
                            /* Build the list of coins from API Data */
                            for (Iterator<String> it = oJSON.keys(); it.hasNext(); ) {
                                String coinName = it.next();
                                CoinValue = oJSON.getJSONObject(coinName).getDouble("gbp");
                                cvm.updatePrices(coinName, CoinValue);
                            }

                        } catch (JSONException e) {
                            Log.d("OkHTTPResponse", "JSON Format Problem");
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d("OkHTTPResponse", "The API call for the coins failed: "
                                + e.getMessage());
                        call.cancel();
                    }
                });
            geckoHandler.postDelayed(geckoCall, 5000);
            }
        };




    };
