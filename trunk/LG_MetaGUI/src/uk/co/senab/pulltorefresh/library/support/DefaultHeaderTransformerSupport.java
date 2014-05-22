/*
 * Copyright 2013 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.senab.pulltorefresh.library.support;

import uk.co.senab.pulltorefresh.library.DefaultHeaderTransformer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.meta.gui.R;

@SuppressLint("NewApi")
public class DefaultHeaderTransformerSupport extends DefaultHeaderTransformer {
	private Animation mHeaderInAnimation, mHeaderOutAnimation;

	@Override
	public void onViewCreated(Activity activity, View headerView) {
		super.onViewCreated(activity, headerView);

		mHeaderInAnimation = AnimationUtils.loadAnimation(activity,
				R.anim.meta__ptr_fade_in);
		mHeaderOutAnimation = AnimationUtils.loadAnimation(activity,
				R.anim.meta__ptr_fade_out);

		if (mHeaderOutAnimation != null || mHeaderInAnimation != null) {
			final AnimationCallback callback = new AnimationCallback();
			if (mHeaderOutAnimation != null) {
				mHeaderOutAnimation.setAnimationListener(callback);
			}
		}
	}

	@Override
	protected Drawable getActionBarBackground(Context context) {
		return context.getResources().getDrawable(
				R.drawable.meta__ptr_action_bar_background);
	}

	@Override
	protected int getActionBarSize(Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 46,
				context.getResources().getDisplayMetrics());
	}

	@Override
	protected int getActionBarTitleStyle(Context context) {
		return 0;
	}

	@Override
	public boolean showHeaderView() {
		final View headerView = getHeaderView();
		final boolean changeVis = headerView != null
				&& headerView.getVisibility() != View.VISIBLE;
		if (changeVis) {
			// Show Header
			if (mHeaderInAnimation != null) {
				// AnimationListener will call HeaderViewListener
				headerView.startAnimation(mHeaderInAnimation);
			}
			headerView.setVisibility(View.VISIBLE);
		}
		return changeVis;
	}

	@Override
	public boolean hideHeaderView() {
		final View headerView = getHeaderView();
		final boolean changeVis = headerView != null
				&& headerView.getVisibility() != View.GONE;
		if (changeVis) {
			// Hide Header
			if (mHeaderOutAnimation != null) {
				// AnimationListener will call HeaderTransformer and
				// HeaderViewListener
				headerView.startAnimation(mHeaderOutAnimation);
			} else {
				headerView.setVisibility(View.GONE);
				onReset();
			}
		}
		return changeVis;
	}

	@Override
	public void onRefreshMinimized() {
		// Here we fade out most of the header, leaving just the progress bar
		View contentLayout = getHeaderView().findViewById(R.id.ptr_content);
		if (contentLayout != null) {
			contentLayout.startAnimation(AnimationUtils.loadAnimation(
					contentLayout.getContext(), R.anim.meta__ptr_fade_out));
			contentLayout.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected int getMinimumApiLevel() {
		return Build.VERSION_CODES.ECLAIR_MR1;
	}

	class AnimationCallback implements Animation.AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			if (animation == mHeaderOutAnimation) {
				View headerView = getHeaderView();
				if (headerView != null) {
					headerView.setVisibility(View.GONE);
				}
				onReset();
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	}
}
