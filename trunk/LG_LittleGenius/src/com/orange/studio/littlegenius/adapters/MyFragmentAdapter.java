package com.orange.studio.littlegenius.adapters;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.orange.studio.littlegenius.activities.BaseActivity.DoAction;
import com.orange.studio.littlegenius.fragments.ImageGuideFragment2;
import com.orange.studio.littlegenius.objects.HomeSliderDTO;

public class MyFragmentAdapter extends FragmentPagerAdapter{

    private List<HomeSliderDTO> fragments;
    private DoAction mDoAction;
    public MyFragmentAdapter(FragmentManager fm, List<HomeSliderDTO> fragments,DoAction _action){
        super(fm);
        this.fragments = fragments;
        mDoAction=_action;
    }
    public void updateData(List<HomeSliderDTO> mData){
    	if(fragments!=null){
    		fragments.clear();
    	}
    	fragments=mData;
    	notifyDataSetChanged();
    }
    @Override
    public Fragment getItem(int position) {
    	switch (position) {		
		default:
			return ImageGuideFragment2.newInstance("",fragments.get(position).imageURL,mDoAction);
		}            
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
