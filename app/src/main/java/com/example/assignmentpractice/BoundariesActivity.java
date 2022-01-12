package com.example.assignmentpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BoundariesActivity extends AppCompatActivity {
    private Button increaseButton, decreaseButton;
    private TextView currentValueTextView;
    private CoinViewModel cvm;
    private String thresholdsCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boundaries);
        currentValueTextView = findViewById(R.id.currentValue);

        Bundle extras = getIntent().getExtras();
        thresholdsCoin = extras.getString("thresholdsCoin");

        cvm = new ViewModelProvider(this).get(CoinViewModel.class);
        cvm.currentValue(thresholdsCoin).observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                currentValueTextView.setText("Currently trading at £" + aDouble);
            }
        });
    }
}