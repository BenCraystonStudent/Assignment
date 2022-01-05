package com.example.assignmentpractice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class RemoveCoin {

    public interface RemoveCoinInterface {
        public void PassValues (String coin_name);
    }
    public AlertDialog RemoveCurrency (Context context, String coin_name, final RemoveCoinInterface removeCoinInterface)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        builder.setTitle(coin_name);
        builder.setMessage("Are you sure? All assets will be sold, all debt recovered.");
        View xml = inflater.inflate(R.layout.remove_coin_dialog, null);
        builder.setView(xml);
        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView coin = (TextView) xml.findViewById(R.id.currencyNameRemove);
                coin.setText(coin_name);
                removeCoinInterface.PassValues(coin.getText().toString());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
        return builder.create();
    }
}
