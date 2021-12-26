package com.example.assignmentpractice;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CoinAdder extends AppCompatActivity {
    private CoinViewModel cvm;
    public int ADD_COIN_REQUEST = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == ADD_COIN_REQUEST && resultCode == RESULT_OK) {
            String receivedCoinName = intent.getStringExtra(CoinListAdapter.INSERTED_COIN_NAME);
            String receivedCoinCurrency = intent.getStringExtra(CoinListAdapter.INSERTED_COIN_CURRENCY);
            Double receivedValue = intent.getDoubleExtra(CoinListAdapter.INSERTED_COIN_VALUE, 1);

            Coin coin = new Coin(receivedCoinName, receivedCoinCurrency, receivedValue);
            cvm.insert(coin);
            Toast toast = Toast.makeText(this, receivedCoinName, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, "WTF RICHARD", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
