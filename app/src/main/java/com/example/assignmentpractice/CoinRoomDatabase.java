package com.example.assignmentpractice;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.List;

@Database(entities = {Coin.class}, version = 3, exportSchema = false)
public abstract class CoinRoomDatabase extends RoomDatabase {
    public abstract CoinDAO coinDao();
    private static CoinRoomDatabase INSTANCE;

    static CoinRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CoinRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CoinRoomDatabase.class, "Coin_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

                private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

                    private final CoinDAO mDao;
                    Coin[] coins;

                    PopulateDbAsync(CoinRoomDatabase db) {
                        mDao = db.coinDao();
                    }

                    @Override
                    protected Void doInBackground(final Void... params) {
                        // Start the app with a clean database every time.
                        // Not needed if you only populate the database
                        // when it is first created
                        if(mDao.getAllCoins() == null)
                            for (int i = 0; i <= coins.length - 1; i++)
                            {
                                Coin coin = new Coin("bdf", "fsdfsf", 0.1, 0.0);
                                mDao.insert(coin);
                            }
                        return null;
                        }
                    }
                }


