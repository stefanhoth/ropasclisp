package com.stefanhoth.ropasclisp.playing;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class HandSignAdapter extends FragmentPagerAdapter {

    public HandSignAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return HandSignFragment.with(HandSign.values()[position]);
    }

    @Override
    public int getCount() {
        return HandSign.values().length;
    }

}
