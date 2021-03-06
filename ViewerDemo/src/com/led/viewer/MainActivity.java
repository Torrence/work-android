package com.led.viewer;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ViewPager awesomePager;

	private MyPagerAdapter awesomeAdapter;

	private Drawable[] imageS = new Drawable[4];

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initPageContent();
		awesomeAdapter = new MyPagerAdapter();
		awesomePager = (ViewPager) findViewById(R.id.awesomepager);
		awesomePager.setAdapter(awesomeAdapter);
	}

	public void initPageContent() {
		imageS[0] = getResources().getDrawable(R.drawable.a1);
		imageS[1] = getResources().getDrawable(R.drawable.a2);
		imageS[2] = getResources().getDrawable(R.drawable.a3);
		imageS[3] = getResources().getDrawable(R.drawable.a4);
	}

	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageS.length;
		}

		/**
		 * Create the page for the given position. The adapter is responsible
		 * for adding the view to the container given here, although it only
		 * must ensure this is done by the time it returns from
		 * {@link #finishUpdate()}.
		 * 
		 * @param container
		 *            The containing View in which the page will be shown.
		 * @param position
		 *            The page position to be instantiated.
		 * @return Returns an Object representing the new page. This does not
		 *         need to be a View, but can be some other container of the
		 *         page.
		 */
		@Override
		public Object instantiateItem(View collection, int position) {

			View view = getLayoutInflater().inflate(R.layout.page, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.imageId);
			imageView.setImageDrawable(imageS[position]);

			((ViewPager) collection).addView(view, 0);

			return view;
		}

		/**
		 * Remove a page for the given position. The adapter is responsible for
		 * removing the view from its container, although it only must ensure
		 * this is done by the time it returns from {@link #finishUpdate()}.
		 * 
		 * @param container
		 *            The containing View from which the page will be removed.
		 * @param position
		 *            The page position to be removed.
		 * @param object
		 *            The same object that was returned by
		 *            {@link #instantiateItem(View, int)}.
		 */
		@Override
		public void destroyItem(View collection, int position, Object view) {
			((ViewPager) collection).removeView((View) view);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((View) object);
		}

		/**
		 * Called when the a change in the shown pages has been completed. At
		 * this point you must ensure that all of the pages have actually been
		 * added or removed from the container as appropriate.
		 * 
		 * @param container
		 *            The containing View which is displaying this adapter's
		 *            page views.
		 */
		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

	}
}