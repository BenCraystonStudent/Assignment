package com.example.assignmentpractice;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CoinRepository {
    private CoinDAO mCoinDao;
    private LiveData<List<Coin>> mAllCoins;
    private Coin templateCoin;
    private LiveData<Double> mTotalInvested;
    private LiveData<Double> mVATOP;

    CoinRepository(Application application) {
        CoinRoomDatabase db = CoinRoomDatabase.getDatabase(application);
        mCoinDao = db.coinDao();
        mAllCoins = mCoinDao.getAllCoins();
        mTotalInvested = mCoinDao.totalInvestments();
    }

    public LiveData<Double> getVATOP(String coin_name){
        return mCoinDao.valueAtTimeOfPurchase(coin_name);
    }

    public LiveData<Double>currentValue(String coin_name)
    {
        return mCoinDao.currentValue(coin_name);
    }


    public LiveData<Double> returnTotalInvestments(){return mTotalInvested;};

    public LiveData<List<Coin>> getAllCoins() {
        return mAllCoins;
    }

    public void insert (Coin coin) {
        new insertAsyncTask(mCoinDao).execute(coin);
    }

    public void updatePriceIncrease(String coin_name, Double increase_price){
        templateCoin = new Coin();
        templateCoin.mCoin = coin_name;
        templateCoin.mPriceIncrease = increase_price;
        new updatePriceIncreaseAsyncTask(mCoinDao).execute(templateCoin);
    }

    private static class updatePriceIncreaseAsyncTask extends AsyncTask<Coin, Void, Void>
    {
        public CoinDAO upiAsyncTaskDao;

        public updatePriceIncreaseAsyncTask(CoinDAO dao){upiAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Coin... params)
        {
            String coin_name = params[0].mCoin;
            Double increase_price = params[0].mPriceIncrease;
            upiAsyncTaskDao.updatePrices(coin_name, increase_price);
            return null;
        }

    }

    public void updatePrices(String coin_name, Double current_price){
        templateCoin = new Coin();
        templateCoin.mCoin = coin_name;
        templateCoin.mCurrentPrice = current_price;
        new updatePricesAsyncTask(mCoinDao).execute(templateCoin);
    }



    private static class updatePricesAsyncTask extends AsyncTask<Coin, Void, Void>
    {
        public CoinDAO upAsyncTaskDao;

        public updatePricesAsyncTask(CoinDAO dao){upAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Coin... params)
        {
            String coin_name = params[0].mCoin;
            Double current_price = params[0].mCurrentPrice;
            upAsyncTaskDao.updatePrices(coin_name, current_price);
            return null;
        }

    }



    private static class insertAsyncTask extends AsyncTask<Coin, Void, Void> {

        private CoinDAO mAsyncTaskDao;

        insertAsyncTask(CoinDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Coin... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void UpdateCurrencyHeld(Double c_held, String coin_name){
        templateCoin = new Coin();
        templateCoin.mCoin = coin_name;
        templateCoin.mCurrencyHeld = c_held;
        //updateAsyncTask.UpdateParams params = new updateAsyncTask.UpdateParams(c_held, coin_name);
        new updateAsyncTask(mCoinDao).execute(templateCoin);
    }


    private static class updateAsyncTask extends AsyncTask<Coin, Void, Void>
    {
        public CoinDAO uAsyncTaskDao;

        public updateAsyncTask(CoinDAO dao){uAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Coin... params)
        {
            Double amount = params[0].mCurrencyHeld;
            String name = params[0].mCoin;
            uAsyncTaskDao.update(amount, name);
            return null;
        }

    }

    public void RemoveCoin(String coin_name){
        templateCoin = new Coin();
        templateCoin.mCoin = coin_name;
        new removeAsyncTask(mCoinDao).execute(templateCoin);
    }

    private static class removeAsyncTask extends AsyncTask<Coin, Void, Void>
    {
        public CoinDAO rAsyncTaskDao;

        public removeAsyncTask(CoinDAO dao){rAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Coin... params)
        {
            rAsyncTaskDao.deleteCoin(params[0]);
            return null;
        }
    }


}
