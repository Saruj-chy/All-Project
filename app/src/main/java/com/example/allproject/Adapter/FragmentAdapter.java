package com.example.allproject.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.allproject.Fragment.Fragment01;
import com.example.allproject.Fragment.Fragment02;
import com.example.allproject.Fragment.Fragment03;
import com.example.allproject.Fragment.Fragment04;

public class FragmentAdapter extends FragmentStatePagerAdapter {


    int mNumOfTabs1;

    public FragmentAdapter(FragmentManager fm, int NumOfTabs1) {
        super(fm);
        this.mNumOfTabs1 = NumOfTabs1;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment01 tab0 = new Fragment01();
                return tab0;
            case 1:
                Fragment02 tab1 = new Fragment02();
                return tab1;
            case 2:
                Fragment03 tab2 = new Fragment03();
                return tab2;
            case 3:
                Fragment04 tab3 = new Fragment04();
                return tab3;


            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumOfTabs1;
    }
}