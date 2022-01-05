package com.example.assignmentpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

public class RemoveCoinActivity extends AppCompatActivity {
    private String receivedCoinNameInfo;
    private CoinViewModel cvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        receivedCoinNameInfo = extras.getString("coinName_info");
        cvm = new ViewModelProvider(this).get(CoinViewModel.class);
        RemoveCoin rc = new RemoveCoin();
        rc.RemoveCurrency(this, receivedCoinNameInfo, coin_name1 -> {
            cvm.removeCoin(coin_name1);
            Intent i = new Intent(this, MainActivity.class);
            this.startActivity(i);
        });

    }
}