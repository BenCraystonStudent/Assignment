package com.example.assignmentpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BoundariesActivity extends AppCompatActivity {
    private Button increaseButton, decreaseButton;
    private TextView currentValueTextView, setIncrease, setDecrease;
    private CoinViewModel cvm;
    private String thresholdsCoin;
    private Double handledCurrentPrice;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boundaries);
        currentValueTextView = findViewById(R.id.currentValue);

        Bundle extras = getIntent().getExtras();
        thresholdsCoin = extras.getString("thresholdsCoin");

        cvm = new ViewModelProvider(this).get(CoinViewModel.class);

        setIncrease = findViewById(R.id.setPriceIncrease);
        setDecrease = findViewById(R.id.setPriceDecrease);
        toolbar = findViewById(R.id.boundariesToolbar);
        toolbar.setTitle(thresholdsCoin);

        increaseButton = findViewById(R.id.increaseButton);
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPriceIncrease si = new SetPriceIncrease();
                si.DisplaySetIncreaseDialog(BoundariesActivity.this, thresholdsCoin, (coin_name, amount) ->  {
                if (amount < handledCurrentPrice){
                    Toast toast = Toast.makeText(BoundariesActivity.this, "Cannot be less than what the currency is currently trading at", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
                else
                {
                    cvm.UpdatePriceIncrease(coin_name, amount);
                    finish();
                }
                });
            }
        });

        decreaseButton = findViewById(R.id.decreaseButton);
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPriceDecrease sd = new SetPriceDecrease();
                sd.DisplaySetDecreaseDialog(BoundariesActivity.this, thresholdsCoin, (coin_name, amount) ->
                {
                    if (amount > handledCurrentPrice){
                        Toast toast = Toast.makeText(BoundariesActivity.this, "Cannot be more than what the currency is currently trading at", Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    }
                    else
                    {
                        cvm.UpdatePriceDecrease(coin_name, amount);
                        finish();
                    }
                });
            }
        });

        cvm.currentValue(thresholdsCoin).observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                currentValueTextView.setText("Currently trading at £" + aDouble);
                handledCurrentPrice = aDouble;
            }
        });

        cvm.increaseValue(thresholdsCoin).observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                setIncrease.setText("Currently set at " + aDouble);
            }
        });

        cvm.decreaseValue(thresholdsCoin).observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                setDecrease.setText("Currently set at " + aDouble);
            }
        });
    }
}