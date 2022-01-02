package com.example.assignmentpractice;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CoinViewModel extends AndroidViewModel {
    private CoinRepository mRepository;
    private LiveData<List<Coin>> mAllCoins;

    public CoinViewModel (Application application) {
        super(application);
        mRepository = new CoinRepository(application);
        mAllCoins = mRepository.getAllCoins();
    }

    public LiveData<List<Coin>> getAllCoins() { return mAllCoins; }

    public void insert(Coin coin) { mRepository.insert(coin); }

    public void update(String coin_name,Double c_held) { mRepository.update(coin_name, c_held); }

}
