/**
 * @author rongfzh
 * @version 1.0.0  
 */
package com.android.fzmap;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fzmap.map.FzLocationManager;
import com.android.fzmap.map.FzLocationManager.LocationCallBack;
import com.android.fzmap.map.LongPressOverlay;
import com.android.fzmap.map.MyItemizedOverlay;
import com.android.fzmap.map.SearchSuggestionProvider;
import com.android.fzmap.utils.CommonHelper;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mobclick.android.MobclickAgent;

/**
 * 
 * @author rongfzh 2011-6-29
 */

public class FzMapActivity extends MapActivity implements LocationCallBack,
		OnClickListener {
	/** Called when the activity is first created. */
	private final String TAG = "FzMapActivity";
	private MapView mapView;
	private MapController mMapCtrl;
	private View popView;
	private Drawable myLocationDrawable;
	private Drawable mylongPressDrawable;
	private FzLocationManager fzLocation;
	private MyItemizedOverlay myLocationOverlay;// 我的位置 层
	private MyItemizedOverlay mLongPressOverlay; // 长按时间层
	private List<Overlay> mapOverlays;
	private OverlayItem overlayitem = null;
	private String query;
	public GeoPoint locPoint;

	ImageButton loction_Btn;
	ImageButton layer_Btn;
	// ImageButton pointwhat_Btn;
	Button search_btn;

	Dialog layer_dialog = null;
	Button btn_ssjt = null;
	Button btn_wxst = null;
	Button btn_jjst = null;
	Button btn_layer_cancel = null;
	Boolean bool_ssjt = false;
	Boolean bool_wxst = false;
	Boolean bool_jjst = false;

	public final int MSG_VIEW_LONGPRESS = 10001;
	public final int MSG_VIEW_ADDRESSNAME = 10002;
	public final int MSG_VIEW_ADDRESSNAME_FAIL = 10004;
	public final int MSG_VIEW_LOCATIONLATLNG = 10003;
	public final int MSG_VIEW_LOCATIONLATLNG_FAIL = 10005;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		loction_Btn = (ImageButton) findViewById(R.id.loction);
		layer_Btn = (ImageButton) findViewById(R.id.layer);
		// pointwhat_Btn = (ImageButton) findViewById(R.id.pointwhat);
		loction_Btn = (ImageButton) findViewById(R.id.loction);
		search_btn = (Button) findViewById(R.id.search);

		loction_Btn.setOnClickListener(this);
		layer_Btn.setOnClickListener(this);
		// pointwhat_Btn.setOnClickListener(this);
		search_btn.setOnClickListener(this);

		myLocationDrawable = getResources().getDrawable(R.drawable.point_where);
		mylongPressDrawable = getResources()
				.getDrawable(R.drawable.point_start);

		mapView = (MapView) findViewById(R.id.map_view);
		mapView.setBuiltInZoomControls(true);
		mapView.setClickable(true);
		initPopView();
		mMapCtrl = mapView.getController();
		myLocationOverlay = new MyItemizedOverlay(myLocationDrawable, this,
				mapView, popView, mMapCtrl);
		mLongPressOverlay = new MyItemizedOverlay(mylongPressDrawable, this,
				mapView, popView, mMapCtrl);
		mapOverlays = mapView.getOverlays();
		mapOverlays
				.add(new LongPressOverlay(this, mapView, mHandler, mMapCtrl));
		// 以北京市中心为中心
		GeoPoint cityLocPoint = new GeoPoint(39909230, 116397428);
		mMapCtrl.animateTo(cityLocPoint);
		mMapCtrl.setZoom(12);
		FzLocationManager.init(FzMapActivity.this.getApplicationContext(),
				FzMapActivity.this);
		fzLocation = FzLocationManager.getInstance();

		createLayerDialog();
	}

	private void initPopView() {
		if (null == popView) {
			popView = getLayoutInflater().inflate(R.layout.overlay_popup, null);
			mapView.addView(popView, new MapView.LayoutParams(
					MapView.LayoutParams.WRAP_CONTENT,
					MapView.LayoutParams.WRAP_CONTENT, null,
					MapView.LayoutParams.BOTTOM_CENTER));
			popView.setVisibility(View.GONE);
		}

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCurrentLocation(Location location) {
		Log.d(TAG, "onCurrentLocationy");

		GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6),
				(int) (location.getLongitude() * 1E6));
		overlayitem = new OverlayItem(point, "我的位置", "");
		mMapCtrl.setZoom(16);
		if (myLocationOverlay.size() > 0) {
			myLocationOverlay.removeOverlay(0);
		}
		myLocationOverlay.addOverlay(overlayitem);
		mapOverlays.add(myLocationOverlay);
		Log.d("lm", "call onCurrentLocation");
		mMapCtrl.animateTo(point);
	}

	/**
	 * 通过经纬度获取地址
	 * 
	 * @param point
	 * @return
	 */
	private String getLocationAddress(GeoPoint point) {
		String add = "";
		Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
		try {
			List<Address> addresses = geoCoder.getFromLocation(
					point.getLatitudeE6() / 1E6, point.getLongitudeE6() / 1E6,
					1);
			Address address = addresses.get(0);
			int maxLine = address.getMaxAddressLineIndex();
			if (maxLine >= 2) {
				add = address.getAddressLine(1) + address.getAddressLine(2);
			} else {
				add = address.getAddressLine(1);
			}
		} catch (IOException e) {
			add = "";
			e.printStackTrace();
		}
		return add;
	}

	private Address searchLocationByName(String addressName) {
		Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.CHINA);
		try {
			List<Address> addresses = geoCoder.getFromLocationName(addressName,
					1);
			Address address_send = null;
			for (Address address : addresses) {
				locPoint = new GeoPoint((int) (address.getLatitude() * 1E6),
						(int) (address.getLongitude() * 1E6));
				address.getAddressLine(1);
				address_send = address;
			}
			return address_send;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_VIEW_LONGPRESS:// 处理长按时间返回位置信息
			{
				if (null == locPoint)
					return;
				new Thread(new Runnable() {
					@Override
					public void run() {
						String addressName = "";

						int count = 0;
						while (true) {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							count++;
							addressName = getLocationAddress(locPoint);
							Log.d(TAG, "获取地址名称");
							// 请求五次获取不到结果就返回
							if ("".equals(addressName) && count > 5) {
								Message msg1 = new Message();
								msg1.what = MSG_VIEW_ADDRESSNAME_FAIL;
								mHandler.sendMessage(msg1);
								break;
							} else if ("".equals(addressName)) {
								continue;
							} else {
								break;
							}

						}
						if (!"".equals(addressName) || count < 5) {
							Message msg = new Message();
							msg.what = MSG_VIEW_ADDRESSNAME;
							msg.obj = addressName;
							mHandler.sendMessage(msg);
						}
					}
				}).start();
				overlayitem = new OverlayItem(locPoint, "地址名称", "正在地址加载...");
				if (mLongPressOverlay.size() > 0) {
					mLongPressOverlay.removeOverlay(0);
				}
				popView.setVisibility(View.GONE);
				mLongPressOverlay.addOverlay(overlayitem);
				mLongPressOverlay.setFocus(overlayitem);
				mapOverlays.add(mLongPressOverlay);
				mMapCtrl.animateTo(locPoint);
				mapView.invalidate();
			}
				break;
			case MSG_VIEW_ADDRESSNAME: {
				// 获取到地址后显示在泡泡上
				TextView desc = (TextView) popView
						.findViewById(R.id.map_bubbleText);
				desc.setText((String) msg.obj);
				popView.setVisibility(View.VISIBLE);
			}
				break;
			case MSG_VIEW_ADDRESSNAME_FAIL: {
				TextView desc = (TextView) popView
						.findViewById(R.id.map_bubbleText);
				desc.setText("获取地址失败");
				popView.setVisibility(View.VISIBLE);
			}
				break;
			case MSG_VIEW_LOCATIONLATLNG: {
				CommonHelper.closeProgress();
				Address address = (Address) msg.obj;
				locPoint = new GeoPoint((int) (address.getLatitude() * 1E6),
						(int) (address.getLongitude() * 1E6));
				overlayitem = new OverlayItem(locPoint, "地址名称",
						address.getAddressLine(1));
				if (mLongPressOverlay.size() > 0) {
					mLongPressOverlay.removeOverlay(0);
				}
				popView.setVisibility(View.GONE);
				mLongPressOverlay.addOverlay(overlayitem);
				mLongPressOverlay.setFocus(overlayitem);
				mapOverlays.add(mLongPressOverlay);
				mMapCtrl.animateTo(locPoint);
				mapView.invalidate();
			}
				break;
			case MSG_VIEW_LOCATIONLATLNG_FAIL: {
				CommonHelper.closeProgress();
				Toast.makeText(FzMapActivity.this, "搜索地址失败", Toast.LENGTH_SHORT)
						.show();
			}
				break;
			}
		}
	};

	// 处理三个button的事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loction: {
			Location locat = fzLocation.getMyLocation();
			if (locat != null) {
				GeoPoint cityLocPoint = new GeoPoint(
						(int) (locat.getLatitude() * 1E6),
						(int) (locat.getLongitude() * 1E6));
				mMapCtrl.animateTo(cityLocPoint);
			}
		}
			break;
		case R.id.search: {
			onSearchRequested();
		}
			break;

		case R.id.layer:
			layer_dialog.show();
			break;

		default:
			break;
		}
	}

	public void createLayerDialog() {
		layer_dialog = new Dialog(FzMapActivity.this);
		layer_dialog.setContentView(R.layout.layer_dialog);
		layer_dialog.setTitle("图层");
		btn_ssjt = (Button) layer_dialog.findViewById(R.id.ssjt);
		btn_wxst = (Button) layer_dialog.findViewById(R.id.wxst);
		btn_jjst = (Button) layer_dialog.findViewById(R.id.jjst);
		btn_layer_cancel = (Button) layer_dialog
				.findViewById(R.id.layer_cancel);

		btn_ssjt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bool_ssjt) {
					bool_ssjt = false;
					mapView.setTraffic(false);
					btn_ssjt.setText("显示实时交通");
				} else {
					bool_ssjt = true;
					mapView.setTraffic(true);
					btn_ssjt.setText("隐藏实时交通");
				}
				layer_dialog_dismiss();
			}
		});

		btn_wxst.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bool_wxst) {
					bool_wxst = false;
					mapView.setSatellite(false);
					btn_wxst.setText("显示卫星视图");
				} else {
					bool_wxst = true;
					mapView.setSatellite(true);
					btn_wxst.setText("隐藏卫星视图");
				}
				layer_dialog_dismiss();
			}
		});

		btn_jjst.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bool_jjst) {
					bool_jjst = false;
					mapView.setStreetView(false);
					btn_jjst.setText("显示街景视图");
				} else {
					bool_jjst = true;
					mapView.setStreetView(true);
					btn_jjst.setText("隐藏街景视图");
				}
				layer_dialog_dismiss();
			}
		});

		btn_layer_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layer_dialog_dismiss();
			}
		});
	}

	public void layer_dialog_dismiss() {
		if (layer_dialog != null) {
			layer_dialog.dismiss();
		}
	}

	@Override
	public boolean onSearchRequested() {
		// 打开浮动搜索框（第一个参数默认添加到搜索框的值）
		startSearch(null, false, null, false);
		return true;
	}

	// 得到搜索结果
	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		// 获得搜索框里值
		query = intent.getStringExtra(SearchManager.QUERY);
		// 保存搜索记录
		SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
				SearchSuggestionProvider.AUTHORITY,
				SearchSuggestionProvider.MODE);
		suggestions.saveRecentQuery(query, null);
		CommonHelper.showProgress(this, "正在搜索: " + query);
		new Thread(new Runnable() {
			@Override
			public void run() {
				Address address;
				int count = 0;
				while (true) {
					count++;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					address = searchLocationByName(query);
					Log.d(TAG, "获取经纬度");
					if (address == null && count > 5) {
						Message msg1 = new Message();
						msg1.what = MSG_VIEW_LOCATIONLATLNG_FAIL;
						mHandler.sendMessage(msg1);
						break;
					} else if (address == null) {
						continue;
					} else {
						break;
					}

				}

				if (address != null || count <= 5) {
					Message msg = new Message();
					msg.what = MSG_VIEW_LOCATIONLATLNG;
					msg.obj = address;
					mHandler.sendMessage(msg);
				}
			}
		}).start();
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	// 关闭程序也关闭定位
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		fzLocation.destoryLocationManager();
	}
}