package com.example.assignmentpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    private CoinViewModel cvm;
    private TextView amountInvestedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        amountInvestedTextView = findViewById(R.id.investmentsTextView);

        cvm = new ViewModelProvider(this).get(CoinViewModel.class);
        cvm.returnTotalInvestments().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                String doubleString = aDouble.toString();
                amountInvestedTextView.setText(doubleString);
            }
        });
    }
}