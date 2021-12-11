package com.example.assignmentpractice;

import static java.lang.String.format;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;
import java.util.Locale;

@Entity(tableName = "coin_table")
public class Coin {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "coin_name")
    String mCoin;

    @ColumnInfo(name = "currency")
    String mCurrency;

    @ColumnInfo(name = "value")
    Double mValue;

    public Coin(String coin_name,String currency, Double value){
        this.mCoin = coin_name;
        this.mCurrency = currency;
        this.mValue = value;
    }

    public Coin(){

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
