package com.orange.studio.littlegenius.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meta.gui.activity.MetaSlidingFragmentActivity;
import com.orange.studio.littlegenius.R;
import com.orange.studio.littlegenius.adapters.MenuSlidingAdapter;
import com.orange.studio.littlegenius.dialogs.ExitDialog;
import com.orange.studio.littlegenius.dialogs.LoginDialog;
import com.orange.studio.littlegenius.dialogs.RegisterDialog;
import com.orange.studio.littlegenius.fragments.AboutFragment;
import com.orange.studio.littlegenius.fragments.ContactFragment;
import com.orange.studio.littlegenius.fragments.HomeFragment;
import com.orange.studio.littlegenius.fragments.KMSFragment;
import com.orange.studio.littlegenius.fragments.PreviewFragment;
import com.orange.studio.littlegenius.fragments.ProgramFragment;
import com.orange.studio.littlegenius.fragments.TestimonialFragment;
import com.orange.studio.littlegenius.objects.SlidingMenuItem;
import com.orange.studio.littlegenius.utils.AppConfig;
import com.orange.studio.littlegenius.utils.AppConfig.PushNotificationKey;
import com.orange.studio.littlegenius.utils.LG_CommonUtils;
import com.slidingmenu.lib.SlidingMenu;
import com.todddavies.components.progressbar.ProgressWheel;

public class BaseActivity extends MetaSlidingFragmentActivity implements
		OnItemClickListener {

	private SlidingMenu sm = null;
	private ListView mListViewMenu = null;
	private MenuSlidingAdapter mMenuAdapter = null;
	private TextView mViewTitle = null;
	private ImageView mHomeMenuDrawer;
	private ExitDialog mExitDialog = null;
	private View mContainerView = null;
	private View mLoadingView = null;
	private View mNotFoundDataView = null;
	private ProgressWheel mProgressView = null;

	public interface DoAction {
		public void DissmissDialog();

		public void Go2KMS();

		public void ExitApplication();
	}

	public DoAction mDoAction = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBehindContentView(R.layout.sliding_menu_layout);
		sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		enableDrawerButton(true);

		createMenuLeft();
		setMetaContentView(R.layout.activity_base_layout);
		initView();
		initListener();

	}

	private void initView() {
		mHomeMenuDrawer = (ImageView) findViewById(R.id.homeSlidingBtn);
		mViewTitle = (TextView) findViewById(R.id.viewTitle);

		mDoAction = new DoAction() {
			@Override
			public void DissmissDialog() {
				showRegisterDialog();
			}

			@Override
			public void Go2KMS() {
				selectItem(4, false);
			}

			@Override
			public void ExitApplication() {
				finish();
			}
		};

		mExitDialog = new ExitDialog(BaseActivity.this, mDoAction);

		mContainerView = (FrameLayout) findViewById(R.id.mainFrameLayout);
		mLoadingView = (LinearLayout) findViewById(R.id.loadingView);
		mNotFoundDataView = (RelativeLayout) findViewById(R.id.notFoundDataView);
		mProgressView = (ProgressWheel) findViewById(R.id.progressWheel);

		switchView(false,true,false);

		selectItem(0, false);
	}

	private void initListener() {
		mHomeMenuDrawer.setOnClickListener(this);
		mListViewMenu.setOnItemClickListener(this);
	}

	private void setViewTitle(String title) {
		mViewTitle.setText(title);
	}

	private void createMenuLeft() {
		mListViewMenu = (ListView) sm.findViewById(R.id.menuList);
		List<SlidingMenuItem> mListItem = new ArrayList<SlidingMenuItem>();
		SlidingMenuItem menuHome = new SlidingMenuItem(111, "Trang chủ");
		SlidingMenuItem menuProgram = new SlidingMenuItem(112, "Chương trình");
		SlidingMenuItem menuTestimonials = new SlidingMenuItem(113, "Nhận xét");
		SlidingMenuItem menuPreview = new SlidingMenuItem(114, "Liên hệ");
		SlidingMenuItem menuKms = new SlidingMenuItem(115, "Thành viên");
		SlidingMenuItem menuContact = new SlidingMenuItem(116, "Địa chỉ");
		SlidingMenuItem menuAbout = new SlidingMenuItem(117, "Giới thiệu");

		mListItem.add(menuHome);
		mListItem.add(menuProgram);
		mListItem.add(menuTestimonials);
		mListItem.add(menuPreview);
		mListItem.add(menuKms);
		mListItem.add(menuContact);
		mListItem.add(menuAbout);

		mMenuAdapter = new MenuSlidingAdapter(this);
		mListViewMenu.setAdapter(mMenuAdapter);
		mMenuAdapter.updateFriendList(mListItem);
	}

	public void selectItem(int position, boolean isToggleMenu) {
		// FragmentManager fragmentManager = getSupportFragmentManager();
		// Fragment mFragment =
		// fragmentManager.findFragmentById(R.id.mainFrameLayout);
		// String fragmentName = "";
		// if (mFragment != null) {
		// fragmentName=mFragment.getClass().getName();
		// }
		Fragment fragment = null;
		String title = "";
		switch (position) {
		case 0:
			title = "TRANG CHỦ";
			fragment = HomeFragment.instantiate(getApplicationContext(),
					HomeFragment.class.getName());
			break;
		case 1:
			title = "CHƯƠNG TRÌNH";
			// if(fragmentName!=null && fragmentName.length()>0 &&
			// fragmentName.equals(HomeFragment.class.getName())){
			//
			// }
			fragment = ProgramFragment.instantiate(getApplicationContext(),
					ProgramFragment.class.getName());
			break;
		case 2:
			title = "NHẬN XÉT";
			fragment = TestimonialFragment.instantiate(getApplicationContext(),
					TestimonialFragment.class.getName());
			break;
		case 3:
			title = "LIÊN HỆ";
			fragment = PreviewFragment.instantiate(getApplicationContext(),
					PreviewFragment.class.getName());
			break;
		case 4:
			title = "THÀNH VIÊN";
			LG_CommonUtils.checkUserInfo();
			if (AppConfig.mUser == null) {
				if (isToggleMenu) {
					toggle();
				}
				showLoginDialog();
			} else {
				title = "THÀNH VIÊN";
				fragment = KMSFragment.instantiate(getApplicationContext(),
						KMSFragment.class.getName());
			}

			break;
		case 5:
			title = "ĐỊA CHỈ";
			fragment = ContactFragment.instantiate(getApplicationContext(),
					ContactFragment.class.getName());
			break;
		case 6:
			title = "GIỚI THIỆU";
			fragment = AboutFragment.instantiate(getApplicationContext(),
					AboutFragment.class.getName());
			break;
		default:
			break;
		}
		if (title != null && title.trim().length() > 0) {
			setViewTitle(title);
		}
		if (fragment != null) {
			if (isToggleMenu) {
				toggle();
			}
			// fragmentManager.beginTransaction()
			// .replace(R.id.mainFrameLayout, fragment).commit();
			replaceFragment(fragment);
		}
	}

	private void replaceFragment(Fragment fragment) {
		if (fragment == null) {
			return;
		}
		String backStateName = fragment.getClass().getName();
		String fragmentTag = backStateName;
		FragmentManager fragmentManager = getSupportFragmentManager();
		boolean fragmentPopped = fragmentManager.popBackStackImmediate(
				backStateName, 0);

		if (!fragmentPopped
				&& fragmentManager.findFragmentByTag(fragmentTag) == null) {
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.replace(R.id.mainFrameLayout, fragment, fragmentTag);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(backStateName);
			ft.commit();
		}
	}

	public void showLoginDialog() {
		LoginDialog mLoginDialog = new LoginDialog(this, mDoAction);
		mLoginDialog.show();
	}

	public void showRegisterDialog() {
		RegisterDialog mRegisterDialog = new RegisterDialog(this);
		mRegisterDialog.show();
	}

	public void switchView(boolean isShowMainView, boolean isShowLoadingView,
			boolean isNotFound) {
		try {
			mLoadingView.setVisibility(isShowLoadingView ? View.VISIBLE
					: View.GONE);
			if (isShowLoadingView) {
				mProgressView.spin();
			} else {
				mProgressView.stopSpinning();
			}
			mNotFoundDataView.setVisibility(isNotFound ? View.VISIBLE
					: View.GONE);
			mContainerView.setVisibility(isShowMainView ? View.VISIBLE
					: View.INVISIBLE);
		} catch (Exception e) {

		}

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SlidingMenuItem item = mMenuAdapter.getItem(position);
		if (item != null) {
			switch (item.id) {
			case 111:
				selectItem(0, true);
				break;
			case 112:
				getIntent().putExtra(LG_CommonUtils.PROGRAM_PARAM,
						LG_CommonUtils.URL_PROGRAM);
				selectItem(1, true);
				break;
			case 113:
				selectItem(2, true);
				break;
			case 114:
				selectItem(3, true);
				break;
			case 115:
				selectItem(4, true);
				break;
			case 116:
				selectItem(5, true);
				break;
			case 117:
				selectItem(6, true);
				break;
			default:
				break;
			}
		}
	}

	public void showExitDialog() {
		if (mExitDialog != null) {
			mExitDialog.show();
		}
	}

	@Override
	public void onBackPressed() {
		showExitDialog();
	}

	private boolean getPushNotificationFlag() {
		boolean result = false;
		try {
			Bundle param = getIntent().getExtras();
			if (param != null) {
				result = param.getBoolean(PushNotificationKey.PROGRAM_KEY);
				getIntent().putExtra(PushNotificationKey.PROGRAM_KEY, false);
			}
			return result;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (getPushNotificationFlag()) {
			selectItem(1, false);
		}
		// else{
		// selectItem(0,false);
		// }
	}
}
