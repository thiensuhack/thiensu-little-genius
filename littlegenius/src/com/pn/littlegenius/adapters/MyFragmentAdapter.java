package com.pn.littlegenius.adapters;

import java.util.List;

import com.pn.littlegenius.MainActivity.DoAction;
import com.pn.littlegenius.fragments.ImageGuideFragment2;
import com.pn.littlegenius.utils.SlideItemData;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentAdapter extends FragmentPagerAdapter{

    private List<SlideItemData> fragments;
    private DoAction mDoAction;
    public MyFragmentAdapter(FragmentManager fm, List<SlideItemData> fragments,DoAction _action){
        super(fm);
        this.fragments = fragments;
        mDoAction=_action;
    }

    @Override
    public Fragment getItem(int position) {
    	switch (position) {		
		default:
			return ImageGuideFragment2.newInstance("",fragments.get(position).mImage,mDoAction);
		}            
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
