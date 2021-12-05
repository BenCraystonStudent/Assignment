package com.example.assignmentpractice;

import static java.lang.String.format;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.util.Comparator;
import java.util.Locale;

@Entity(tableName = "coin_table")
public class Coin {
    String mCoin;
    String mCurrency;
    Double mValue;

    public Coin(String Name,String Currency, Double Value){
        mCoin = Name;
        mCurrency = Currency;
        mValue = Value;
    }

    public Coin(String coin) {
    }


    public String CoinName(){ return mCoin;}

    @NonNull
    @Override
    public String toString(){
        return format(Locale.UK, "%1$-20s %2$.8f %3$s", mCoin, mValue, mCurrency);
    }

}

class SortByCoinName implements Comparator<Coin> {
    // Used for sorting in ascending order of
    // name
    public int compare(Coin a, Coin b)
    {
        return a.CoinName().compareTo(b.CoinName());
    }
}
