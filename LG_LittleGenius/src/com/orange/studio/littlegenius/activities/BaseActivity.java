package com.orange.studio.littlegenius.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.meta.gui.activity.MetaSlidingFragmentActivity;
import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.adapters.MenuSlidingAdapter;
import com.orange.studio.littlegenius.fragments.AboutFragment;
import com.orange.studio.littlegenius.fragments.ContactFragment;
import com.orange.studio.littlegenius.fragments.HomeFragment;
import com.orange.studio.littlegenius.fragments.KMSFragment;
import com.orange.studio.littlegenius.fragments.PreviewFragment;
import com.orange.studio.littlegenius.fragments.ProgramFragment;
import com.orange.studio.littlegenius.fragments.TestimonialFragment;
import com.orange.studio.littlegenius.objects.SlidingMenuItem;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;
import com.slidingmenu.lib.SlidingMenu;

public class BaseActivity extends MetaSlidingFragmentActivity implements OnItemClickListener{

	private SlidingMenu sm = null;
	private ListView mListViewMenu = null;
	private MenuSlidingAdapter mMenuAdapter=null;
	private TextView mViewTitle=null;
	private ImageView mHomeMenuDrawer;
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
		selectItem(0,false);
		
	}
	private void initView(){
		mHomeMenuDrawer=(ImageView)findViewById(R.id.homeSlidingBtn);
		mViewTitle=(TextView)findViewById(R.id.viewTitle);
	}
	private void initListener(){
		mHomeMenuDrawer.setOnClickListener(this);
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
	public void selectItem(int position, boolean isToggleMenu) {
		Fragment fragment = null;
		FragmentManager fragmentManager = getSupportFragmentManager();
		String title="";
		switch (position) {
		case 0:
			title="HOME";
			fragment = HomeFragment.instantiate(getApplicationContext(),
					HomeFragment.class.getName());
			break;
		case 1:
			title="PROGRAM";
			fragment = ProgramFragment.instantiate(getApplicationContext(),
					ProgramFragment.class.getName());
			break;
		case 2:
			title="TESTIMONIALS";
			fragment = TestimonialFragment.instantiate(getApplicationContext(),
					TestimonialFragment.class.getName());
			break;
		case 3:
			title="PREVIEW";
			fragment = PreviewFragment.instantiate(getApplicationContext(),
					PreviewFragment.class.getName());
			break;
		case 4:
			title="KMS";
			fragment = KMSFragment.instantiate(getApplicationContext(),
					KMSFragment.class.getName());
			break;
		case 5:
			title="CONTACT";
			fragment = ContactFragment.instantiate(getApplicationContext(),
					ContactFragment.class.getName());
			break;
		case 6:
			title="ABOUT";
			fragment = AboutFragment.instantiate(getApplicationContext(),
					AboutFragment.class.getName());
			break;
		default:
			break;
		}
		if(title!=null && title.trim().length()>0){
			setViewTitle(title);
		}
		if (fragment != null) {
			if(isToggleMenu){
				toggle();
			}
			fragmentManager.beginTransaction()
					.replace(R.id.mainFrameLayout, fragment).commit();
		}
	}
	@Override
	public void onClick(View v) {
		int id=v.getId();
		switch (id) {
		case R.id.homeSlidingBtn:
			toggle();
			break;

		default:
			super.onClick(v);
			break;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		SlidingMenuItem item=mMenuAdapter.getItem(position);
		if(item!=null){
			switch (item.id) {
			case 111:
				selectItem(0,true);
				break;
			case 112:
				getIntent().putExtra(LG_CommonUtils.PROGRAM_PARAM, LG_CommonUtils.URL_PROGRAM);
				selectItem(1,true);
				break;
			case 113:
				selectItem(2,true);
				break;
			case 114:
				selectItem(3,true);
				break;
			case 115:
				selectItem(4,true);
				break;
			case 116:
				selectItem(5,true);
				break;
			case 117:
				selectItem(6,true);
				break;
			default:
				break;
			}
		}
	}
}