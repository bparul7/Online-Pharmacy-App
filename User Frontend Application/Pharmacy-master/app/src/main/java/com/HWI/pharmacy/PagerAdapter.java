package com.HWI.pharmacy;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int num;
    public PagerAdapter(FragmentManager fm, int number) {
        super(fm);
        num = number;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                PrescriptionFragment pres = new PrescriptionFragment();
                return pres;
            case 1:
                IdFragment id = new IdFragment();
                return id;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
