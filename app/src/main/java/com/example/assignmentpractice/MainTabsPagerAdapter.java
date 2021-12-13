package com.example.assignmentpractice;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new AllCurrencies();
            case 1: return new FavCurrencies();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}