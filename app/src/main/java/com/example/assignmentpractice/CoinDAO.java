package com.example.assignmentpractice;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
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

   // @Update
   // void update(Coin coin);

}