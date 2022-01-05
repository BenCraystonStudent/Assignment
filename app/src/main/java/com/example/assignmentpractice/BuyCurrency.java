package com.example.assignmentpractice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class BuyCurrency {

    public interface BuyCurrencyInterface {
        public void PassValues (String coin_name, Double amount);
    }
    public AlertDialog DisplayBuyCurrency (Context context, String coin_name, final BuyCurrencyInterface buyInterface)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        builder.setTitle(coin_name);
        builder.setMessage("Please Enter Amount");
        View xml = inflater.inflate(R.layout.buycurrency_dialog, null);
        builder.setView(xml);
        builder.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView coin = (TextView) xml.findViewById(R.id.currencyName);
                coin.setText(coin_name);
                EditText amount = (EditText)xml.findViewById(R.id.amountEntered);

                buyInterface.PassValues(coin.getText().toString(), Double.parseDouble(amount.getText().toString()));
                dialog.dismiss();
            }

        });
        builder.show();
        return builder.create();
    }
}
