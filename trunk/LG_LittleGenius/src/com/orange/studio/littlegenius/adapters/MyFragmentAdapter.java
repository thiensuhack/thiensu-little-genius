package com.orange.studio.littlegenius.adapters;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.orange.studio.littlegenius.fragments.HomeFragment.DoAction;
import com.orange.studio.littlegenius.fragments.ImageGuideFragment2;
import com.orange.studio.littlegenius.objects.SlideItemData;

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
