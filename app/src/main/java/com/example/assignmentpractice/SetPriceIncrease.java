package com.example.assignmentpractice;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

public class SetPriceIncrease {
    private CoinViewModel cvm;

    public interface SetIncreaseInterface {
        public void PassValues (String coin_name, Double amount);
    }
    public AlertDialog DisplaySetIncreaseDialog(Context context, String coin_name, final SetIncreaseInterface setIncreaseInterface)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        builder.setTitle(coin_name);
        builder.setMessage("Please Enter Increase Cap");
        View xml = inflater.inflate(R.layout.buycurrency_dialog, null);
        builder.setView(xml);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView coin = (TextView) xml.findViewById(R.id.currencyName);
                coin.setText(coin_name);
                EditText amount = (EditText)xml.findViewById(R.id.amountEntered);

                setIncreaseInterface.PassValues(coin.getText().toString(), Double.parseDouble(amount.getText().toString()));
                dialog.dismiss();
            }

        });
        builder.show();
        return builder.create();
    }
}

