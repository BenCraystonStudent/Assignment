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
    //TODO: Add Unique Primary Key which will allow users to add and remove a coin as many times at will, otherwise the program will throw UNIQUE_CONSTRAINT_FAILED error on remove and insert same coin
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "coin_name")
    String mCoin;

    @ColumnInfo(name = "currency")
    String mCurrency;

    @ColumnInfo(name = "value")
    Double mValue;

    @ColumnInfo(name = "currency_held")
    Double mCurrencyHeld;

    @ColumnInfo(name = "price_increase")
    Double mPriceIncrease;

    @ColumnInfo(name = "price_decrease")
    Double mPriceDecrease;

    @ColumnInfo(name = "current_price")
    Double mCurrentPrice;

    @ColumnInfo(name = "holdDate")
    String mDate;

    public Coin(String coin_name,String currency, Double value, Double c_held, Double price_increase, Double price_decrease, Double current_price, String date){
        this.mCoin = coin_name;
        this.mCurrency = currency;
        this.mValue = value;
        this.mCurrencyHeld = c_held;
        this.mPriceIncrease = price_increase;
        this.mPriceDecrease = price_decrease;
        this.mCurrentPrice = current_price;
        this.mDate = date;
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
