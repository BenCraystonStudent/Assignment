package com.example.assignmentpractice;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CoinDAO {

    @Insert
    void insert (Coin coin);

    @Query("DELETE FROM coin_table")
    void deleteAll();

    @Query("SELECT * from coin_table ORDER BY coin_name ASC")
    LiveData<List<Coin>> getAllCoins();

    @Query("UPDATE coin_table SET currency_held =:c_held WHERE coin_name =:coin_name")
    void update(Double c_held, String coin_name);

    @Delete
    void deleteCoin(Coin coin);

    @Query("SELECT SUM(currency_held) FROM coin_table")
    LiveData<Double> totalInvestments();

    @Query("SELECT value FROM coin_table WHERE coin_name = :coin_name")
    LiveData<Double> valueAtTimeOfPurchase(String coin_name);

    @Query("UPDATE coin_table SET current_price=:c_price WHERE coin_name=:coin_name")
    void updatePrices(String coin_name, Double c_price);

    @Query("SELECT current_price FROM coin_table WHERE coin_name = :coin_name")
    LiveData<Double> currentValue(String coin_name);

    @Query("UPDATE coin_table SET price_increase=:price_increase WHERE coin_name=:coin_name")
    void updatePriceIncrease(String coin_name, Double price_increase);

    @Query("UPDATE coin_table SET price_decrease=:price_decrease WHERE coin_name=:coin_name")
    void updatePriceDecrease(String coin_name, Double price_decrease);

    @Query("SELECT price_increase FROM coin_table WHERE coin_name = :coin_name")
    LiveData<Double> increaseValue(String coin_name);

    @Query("SELECT price_decrease FROM coin_table WHERE coin_name = :coin_name")
    LiveData<Double> decreaseValue(String coin_name);


}