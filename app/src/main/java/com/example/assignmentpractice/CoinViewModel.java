package com.example.assignmentpractice;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CoinViewModel extends AndroidViewModel {
    private CoinRepository mRepository;
    private LiveData<List<Coin>> mAllCoins;
    private LiveData<Double> mTotalInvested;
    private LiveData<Double> mTotalAtTOP;
    private String coin_name;

    public CoinViewModel (Application application) {
        super(application);
        mRepository = new CoinRepository(application);
        mAllCoins = mRepository.getAllCoins();
        mTotalInvested = mRepository.returnTotalInvestments();

    }

    public LiveData<List<Coin>> getAllCoins() { return mAllCoins; }

    public LiveData<Double> getVATOP(String coin_name) {return mRepository.getVATOP(coin_name);}

    public void insert(Coin coin) { mRepository.insert(coin); }

    public void removeCoin(String coin_name){ mRepository.RemoveCoin(coin_name);}

    public void UpdateCurrencyHeld(String coin_name, Double c_held) { mRepository.UpdateCurrencyHeld(c_held, coin_name); }

    public LiveData<Double> returnTotalInvestments() {return mTotalInvested;}

    public void updatePrices(String coin_name, Double current_price){mRepository.updatePrices(coin_name, current_price);}

    public LiveData<Double> currentValue(String coin_name) {return mRepository.currentValue(coin_name);}

    public void UpdatePriceIncrease(String coin_name, Double increase_price) { mRepository.updatePriceIncrease(coin_name, increase_price); }

    public void UpdatePriceDecrease(String coin_name, Double decrease_price) { mRepository.updatePriceDecrease(coin_name, decrease_price); }

    public LiveData<Double> increaseValue(String coin_name) {return mRepository.increaseValue(coin_name);}

    public LiveData<Double> decreaseValue(String coin_name) {return mRepository.decreaseValue(coin_name);}


}
