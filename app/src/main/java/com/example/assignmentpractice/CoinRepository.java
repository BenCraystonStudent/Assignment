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

    public void update (Double c_held, String coin_name)

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
}
