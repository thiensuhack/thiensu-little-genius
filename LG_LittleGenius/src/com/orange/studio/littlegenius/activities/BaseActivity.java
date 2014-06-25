package com.orange.studio.littlegenius.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.meta.gui.activity.MetaSlidingFragmentActivity;
import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.adapters.MenuSlidingAdapter;
import com.orange.studio.littlegenius.fragments.AboutFragment;
import com.orange.studio.littlegenius.fragments.HomeFragment;
import com.orange.studio.littlegenius.fragments.KMSFragment;
import com.orange.studio.littlegenius.objects.SlidingMenuItem;
import com.slidingmenu.lib.SlidingMenu;

public class BaseActivity extends MetaSlidingFragmentActivity implements OnItemClickListener{

	private SlidingMenu sm = null;
	private ListView mListViewMenu = null;
	private MenuSlidingAdapter mMenuAdapter=null;
	private TextView mViewTitle=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBehindContentView(R.layout.sliding_menu_layout);
		sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		enableDrawerButton(true);
		
		createMenuLeft();
		setMetaContentView(R.layout.activity_base_layout);
		initView();
		initListener();
		selectItem(0);
		
	}
	private void initView(){
		mViewTitle=(TextView)findViewById(R.id.viewTitle);
	}
	private void initListener(){
		mListViewMenu.setOnItemClickListener(this);
	}
	private void setViewTitle(String title){
		mViewTitle.setText(title);
	}
 	private void createMenuLeft(){
		mListViewMenu=(ListView)sm.findViewById(R.id.menuList);
		List<SlidingMenuItem> mListItem=new ArrayList<SlidingMenuItem>();
		SlidingMenuItem menuHome=new SlidingMenuItem(111,"HOME");
		SlidingMenuItem menuProgram=new SlidingMenuItem(112,"PROGRAM");
		SlidingMenuItem menuTestimonials=new SlidingMenuItem(113,"TESTIMONIALS");
		SlidingMenuItem menuPreview=new SlidingMenuItem(114,"PREVIEW");
		SlidingMenuItem menuKms=new SlidingMenuItem(115,"KMS");
		SlidingMenuItem menuContact=new SlidingMenuItem(116,"CONTACT");
		SlidingMenuItem menuAbout=new SlidingMenuItem(117,"ABOUT");
		
		mListItem.add(menuHome);
		mListItem.add(menuProgram);
		mListItem.add(menuTestimonials);
		mListItem.add(menuPreview);
		mListItem.add(menuKms);
		mListItem.add(menuContact);
		mListItem.add(menuAbout);	
		
		mMenuAdapter=new MenuSlidingAdapter(this);
		mListViewMenu.setAdapter(mMenuAdapter);
		mMenuAdapter.updateFriendList(mListItem);		
	}
	private void selectItem(int position) {
		Fragment fragment = null;
		FragmentManager fragmentManager = getSupportFragmentManager();
		String title="";
		switch (position) {
		case 0:
			title="HOME";
			fragment = HomeFragment.instantiate(getApplicationContext(),
					HomeFragment.class.getName());
			break;
		case 4:
			title="KMS";
			fragment = KMSFragment.instantiate(getApplicationContext(),
					KMSFragment.class.getName());
			break;
		case 6:
			title="KMS";
			fragment = AboutFragment.instantiate(getApplicationContext(),
					AboutFragment.class.getName());
			break;
		default:
			break;
		}
		setViewTitle(title);
		if (fragment != null) {
			toggle();
			fragmentManager.beginTransaction()
					.replace(R.id.mainFrameLayout, fragment).commit();
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		SlidingMenuItem item=mMenuAdapter.getItem(position);
		if(item!=null){
			switch (item.id) {
			case 111:
				selectItem(0);
				break;
			case 112:
				selectItem(1);
				break;
			case 113:
				selectItem(2);
				break;
			case 114:
				selectItem(3);
				break;
			case 115:
				selectItem(4);
				break;
			case 116:
				selectItem(5);
				break;
			case 117:
				selectItem(6);
				break;
			default:
				break;
			}
		}
	}
}
