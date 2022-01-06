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

    public void removeCoin(String coin_name){ mRepository.RemoveCoin(coin_name);}

    public void UpdateCurrencyHeld(String coin_name, Double c_held) { mRepository.UpdateCurrencyHeld(c_held, coin_name); }

}
