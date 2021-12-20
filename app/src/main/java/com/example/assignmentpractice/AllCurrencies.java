package com.example.assignmentpractice;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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

public class AllCurrencies extends Fragment {
    private final LinkedList<Coin> mCoinList = new LinkedList<>();
    private final LinkedList<Coin> coins = new LinkedList<>();
    private RecyclerView aRecyclerView;
    private CoinListAdapter aAdapter;
    private View aView;
    private Context aContext;
    private CoinViewModel mCoinViewModel;
    public CoinDAO mCoinDAO;

    public AllCurrencies() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        aContext = container.getContext();
        aView = inflater.inflate(R.layout.fragment_all_currencies, container, false);
        aRecyclerView = aView.findViewById(R.id.recyclerview);
        try {
            getHTTPData();
        } catch (IOException e) {
            e.printStackTrace();
            mCoinList.add(new Coin("Failed to add coins!", "Currency", 0.0, false));
        }
        LinearLayoutManager llm = new LinearLayoutManager(aContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        aAdapter = new CoinListAdapter((AppCompatActivity) aContext, mCoinList);
        aRecyclerView.setLayoutManager(llm);
        aRecyclerView.setAdapter(aAdapter);
        aAdapter.setCoins(mCoinList);

        return aView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity();
    }

    void getHTTPData() throws IOException {
        /* https://www.coingecko.com/api/documentations/v3#/ */
        OkHttpClient client = new OkHttpClient();
        /* Set API URL */
        HttpUrl.Builder urlBuilder =
                Objects.requireNonNull(HttpUrl.parse("https://api.coingecko.com/api/v3/simple/price"))
                        .newBuilder();
        /* Add coin list to be fetched could be a string resource */
        urlBuilder.addQueryParameter("ids",
                "ampleforth,ankr,apollo,bancor,binancecoin,bitcoin,bitcoin-cash,cardano,chainlink,"
                        +"dash,ethereum,tether,polkadot,uniswap,litecoin,internet-computer,eos,"
                        +"the-graph,maker,numeraire,decentraland,sushi,filecoin");

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
                    mCoinList.clear();

                    double CoinValue;
                    /* Build the list of coins from API Data */
                    for (Iterator<String> it = oJSON.keys(); it.hasNext(); ) {
                        String coinName = it.next();
                        CoinValue = oJSON.getJSONObject(coinName).getDouble("gbp");
                        mCoinList.add(new Coin(coinName, "gbp", CoinValue, false));
                    }

                } catch (JSONException e) {
                    Log.d("OkHTTPResponse", "JSON Format Problem");
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(() -> {
                    Log.d("OkHTTPResponse", myResponse);
                    /* Update spinner with new coin data */
                    mCoinList.sort(new SortByCoinName());
                    aAdapter.notifyDataSetChanged();
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
}




