package com.example.assignmentpractice;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CoinDAO {
    @Insert
    void insert (Coin coin);

    @Query("DELETE FROM coin_table")
    void deleteAll();

    @Query("SELECT * from coin_table ORDER BY coin ASC")
    List<Coin> getAllCoins();
}
