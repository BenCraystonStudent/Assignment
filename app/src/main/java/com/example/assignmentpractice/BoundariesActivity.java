package com.example.assignmentpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BoundariesActivity extends AppCompatActivity {
    private Button increaseButton, decreaseButton;
    private TextView currentValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boundaries);

    }
}