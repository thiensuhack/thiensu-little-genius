package com.pn.littlegenius.adapters;

import java.util.List;

import com.pn.littlegenius.fragments.ImageGuideFragment2;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentAdapter extends FragmentPagerAdapter{

    private List<Integer> fragments;

    public MyFragmentAdapter(FragmentManager fm, List<Integer> fragments){
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
    	switch (position) {		
		default:
			return ImageGuideFragment2.newInstance("",fragments.get(position),doAction);
		}            
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
