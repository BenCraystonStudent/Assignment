package com.example.assignmentpractice;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
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
    private LiveData<List<Coin>> dbCoins;
    private RecyclerView aRecyclerView;
    private Activity activity;
    private CoinListAdapter aAdapter;
    private View aView;
    private View aCoinListItem;
    private Context aContext;
    private CoinViewModel aCoinViewModel;
    private ImageButton aFavButton;
    public int ADD_COIN_REQUEST = 1;
    private String receivedCoinName;

    public AllCurrencies() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        CoinViewModel aCoinViewModel = new ViewModelProvider(this).get(CoinViewModel.class);

       /* aFavButton = aCoinListItem.findViewById(R.id.FavImage);
        aFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(aContext, "Hello", Toast.LENGTH_SHORT);
                toast.show();
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        aContext = container.getContext();
        aView = inflater.inflate(R.layout.fragment_all_currencies, container, false);
        aRecyclerView = aView.findViewById(R.id.all_recyclerview);
        try {
            getHTTPData();
        } catch (IOException e) {
            e.printStackTrace();
            mCoinList.add(new Coin("Failed to add coins!", "Currency", 0.0, 0.0, 0.0, 0.0, 0.0));
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

  //  public void InsertCoinToRepo()
    {
   //     aCoinViewModel.insert(aAdapter.getCoin(0));
    }

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == ADD_COIN_REQUEST && resultCode == RESULT_OK) {
            String receivedCoinName = intent.getStringExtra(CoinListAdapter.INSERTED_COIN_NAME);
            String receivedCoinCurrency = intent.getStringExtra(CoinListAdapter.INSERTED_COIN_CURRENCY);
            Double receivedValue = intent.getDoubleExtra(CoinListAdapter.INSERTED_COIN_VALUE, 1);

            Coin coin = new Coin(receivedCoinName, receivedCoinCurrency, receivedValue);
           // aCoinViewModel.insert(coin);
            Toast toast = Toast.makeText(this.getContext(), receivedCoinName, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this.getContext(), "WTF RICHARD", Toast.LENGTH_SHORT);
            toast.show();
            }
        }*/






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
                        mCoinList.add(new Coin(coinName, "gbp", CoinValue, 0.0, 0.0, 0.0, CoinValue));
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




