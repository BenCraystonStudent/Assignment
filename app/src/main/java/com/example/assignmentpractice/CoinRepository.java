package com.example.assignmentpractice;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CoinRepository {
    private CoinDAO mCoinDao;
    private LiveData<List<Coin>> mAllCoins;

    CoinRepository(Application application) {
        CoinRoomDatabase db = CoinRoomDatabase.getDatabase(application);
        mCoinDao = db.coinDao();
        mAllCoins = mCoinDao.getAllCoins();

    }

    public LiveData<List<Coin>> getAllCoins() {
        return mAllCoins;
    }

    public void insert (Coin coin) {
        new insertAsyncTask(mCoinDao).execute(coin);
    }

   // public void update(Coin coin) {
   //     mCoinDao.update(coin);
    //    new updateAsyncTask(mCoinDao).execute(coin);
  //  }

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
        updateAsyncTask.UpdateParams params = new updateAsyncTask.UpdateParams(c_held, coin_name);
        new updateAsyncTask(mCoinDao).execute((Runnable) params);
    }



    private static class updateAsyncTask extends AsyncTask<Coin, Void, Void>
    {
        public CoinDAO uAsyncTaskDao;

        private static class UpdateParams{
            Double c_held;
            String coin_name;

            public UpdateParams(Double c_held, String coin_name){
                this.c_held = c_held;
                this.coin_name = coin_name;
            }

        }

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
}
