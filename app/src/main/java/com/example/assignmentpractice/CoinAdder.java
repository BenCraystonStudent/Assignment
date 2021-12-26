package com.example.assignmentpractice;

import static com.example.assignmentpractice.CoinListAdapter.INSERTED_COIN_CURRENCY;
import static com.example.assignmentpractice.CoinListAdapter.INSERTED_COIN_VALUE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class CoinAdder extends AppCompatActivity {
    private CoinViewModel cvm;
    public int ADD_COIN_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_all_currencies);
        Intent data = getIntent();
        cvm = new ViewModelProvider(this).get(CoinViewModel.class);
    }

  // private void AddCoin(){
  //      Intent data = new Intent();
  //      data.putExtra(INSERTED_COIN_NAME, coin.mCoin);
  //      data.putExtra(INSERTED_COIN_CURRENCY, coin.mCurrency);
  //      data.putExtra(INSERTED_COIN_VALUE, coin.mValue);
  //  }

}
