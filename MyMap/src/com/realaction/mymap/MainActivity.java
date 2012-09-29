package com.realaction.mymap;

import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKEvent;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MKLocationManager;
import com.baidu.mapapi.MKPlanNode;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.baidu.mapapi.PoiOverlay;
import com.baidu.mapapi.RouteOverlay;
import com.baidu.mapapi.TransitOverlay;

public class MainActivity extends MapActivity {
	BMapManager mBMapMan = null;	//百度地图API管理类
	MapView mMapView = null;		//百度地图显示地图的view
	MapController mMapController = null;	//用来控制mMapView
	Button mShowsatellite = null;	//显示卫星视图Button
	boolean showsatellite = false;	//是否显示卫星视图
	Button mShowtraffic = null;		//显示实时交通信息Button
	boolean showtraffic = false;	//是否显示实时交通信息
	Button mShowMylocation = null;	//定位Button
	Button mLocation = null;		//实时定位Button
	boolean mlocation = false;		//是否实时定位
	Button mSearchBtn = null;		//搜索Button
	EditText mSearchText = null;	//搜索框
	LinearLayout mLocationView = null;	//顶部工具按钮View
	RelativeLayout mSearchView = null;	//搜索View
	Button mShowSearch = null;		//显示搜索View Button
	Button mSearchClose = null;		//隐藏搜索View Button
	Button mShowTransitSearchViewBtn = null;	//显示路线搜索View
	LinearLayout mTransitsearch_view = null;	//路线搜索View
	Button mSearch_transit_close = null;		//应藏路线搜索View
	EditText mStart_site = null;	//输入路线搜索起始点
	EditText mEnd_site = null;		//输入路线搜索终点
	Button mSearch_transit_btn = null;	//公交搜索Button
	Button jc_search_transit_btn = null;	//驾车搜索Button
	Button bx_search_transit_btn = null;	//不行搜索Button
	String[] mStrSuggestions = null;
	//ListView mSuggestionList = null;
	MKLocationManager mLocationManager = null;
	MyLocationOverlay mLocationOverlay = null;
	final int RM_UPDATELISTENER = 1000;
	final int GET_CURRENT_CITY_NAME = 1001;
	MKSearch mSearchMode = null;	//百度地图搜索服务类
	private GetCurrentCityName mGetCurrentCityNameThread = null;	//获取当前地图中心点城市名的线程
	String mCurrentCityNameStr = null;	//当前地图中心点所在的城市
	
	OnClickListener mShowsatelliteListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mMapView != null) {
				if (!showsatellite) {
					mMapView.setSatellite(true);
					showsatellite = true;
					mShowsatellite.setText(R.string.hide_satellite);
				}
				else {
					mMapView.setSatellite(false);
					showsatellite = false;
					mShowsatellite.setText(R.string.show_satellite);
				}
			}
		}
	};
	OnClickListener mShowtrafficListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mMapView != null) {
				if (!showtraffic) {
					mMapView.setTraffic(true);
					showtraffic = true;
					mShowtraffic.setText(R.string.hide_traffic);
				}
				else {
					mMapView.setTraffic(false);
					showtraffic = false;
					mShowtraffic.setText(R.string.show_traffic);
				}
			}
		}
	};
	OnClickListener mShowmylocationListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mMapView != null) {
				Toast.makeText(getApplicationContext(), getText(R.string.wait_for_location), Toast.LENGTH_SHORT).show();
				mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
				handler.sendEmptyMessageDelayed(RM_UPDATELISTENER, 10000);
			}
		}
	};
	OnClickListener mShowSearchListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mLocationView.setVisibility(View.GONE);
			mSearchView.setVisibility(View.VISIBLE);
			Animation anim = new TranslateAnimation(300, 0, 0, 0);
			anim.setDuration(2000);
			OvershootInterpolator overshoot = new OvershootInterpolator();
			anim.setInterpolator(overshoot);
			mSearchView.startAnimation(anim);
		}
	};
	OnClickListener mSearchCloseListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mLocationView.setVisibility(View.VISIBLE);
			mSearchView.setVisibility(View.GONE);
			mSearchText.setText(null);
		}
	};
	OnClickListener mSearchListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SearchButtonProcess();
		}
	};
	void SearchButtonProcess() {
		String search_text = mSearchText.getText().toString();
		GeoPoint mMapCenter = mMapView.getMapCenter();
		int mLatitudeSpan = mMapView.getLatitudeSpan();
		int mLongitudeSpan = mMapView.getLongitudeSpan();
		Log.d("lm", "mLatitudeSpan = " + mLatitudeSpan + ", " + "mLongitudeSpan = " + mLongitudeSpan);
		Log.d("lm", "MapCenter = " + mMapCenter.getLatitudeE6() + ", " + mMapCenter.getLongitudeE6());
		int mCenterlatitude = mMapCenter.getLatitudeE6();
		int mCenterlongitude = mMapCenter.getLongitudeE6();
		GeoPoint mgpLD = new GeoPoint(mCenterlatitude - mLatitudeSpan / 2,
				mCenterlongitude - mLongitudeSpan / 2);
		GeoPoint mgpRB = new GeoPoint(mCenterlatitude + mLatitudeSpan / 2,
				mCenterlongitude + mLongitudeSpan / 2);
		//mMapView.getController().animateTo(mgpRB);
		mSearchMode.poiSearchInbounds(search_text, mgpLD, mgpRB);
	}
	OnClickListener mMyLocationListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mMapView != null) {
				if (!mlocation) {
					mlocation = true;
					Toast.makeText(getApplicationContext(), getText(R.string.wait_for_location), Toast.LENGTH_SHORT).show();
					mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
					mLocation.setText(R.string.rm_location);
				}
				else {
					mlocation = false;
					handler.sendEmptyMessage(RM_UPDATELISTENER);
					mLocation.setText(R.string.location);
				}
			}
		}
	};
	OnClickListener mSearchTransitListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mLocationView.setVisibility(View.GONE);
			mTransitsearch_view.setVisibility(View.VISIBLE);
			Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
			mTransitsearch_view.startAnimation(anim);
		}
	};
	OnClickListener mSearchTransitCloseListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mLocationView.setVisibility(View.VISIBLE);
			mTransitsearch_view.setVisibility(View.GONE);
		}
	};
	OnClickListener mSearchTransitBtnListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String start_site = mStart_site.getText().toString();
			String end_site = mEnd_site.getText().toString();
			MKPlanNode start = new MKPlanNode();
			start.name = start_site;
			MKPlanNode end = new MKPlanNode();
			end.name = end_site;
			mSearchMode.transitSearch(mCurrentCityNameStr, start, end);
		}
	};
	OnClickListener jc_search_transit_btn_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String start_site = mStart_site.getText().toString();
			String end_site = mEnd_site.getText().toString();
			MKPlanNode start = new MKPlanNode();
			start.name = start_site;
			MKPlanNode end = new MKPlanNode();
			end.name = end_site;
			mSearchMode.drivingSearch(mCurrentCityNameStr, start, mCurrentCityNameStr, end);
		}
	};
	OnClickListener bx_search_transit_btn_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String start_site = mStart_site.getText().toString();
			String end_site = mEnd_site.getText().toString();
			MKPlanNode start = new MKPlanNode();
			start.name = start_site;
			MKPlanNode end = new MKPlanNode();
			end.name = end_site;
			mSearchMode.walkingSearch(mCurrentCityNameStr, start, mCurrentCityNameStr, end);
		}
	};
	LocationListener mLocationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			if (location != null) {
				GeoPoint pt = new GeoPoint((int)(location.getLatitude() * 1E6), (int)(location.getLongitude() * 1E6));
				mMapView.getOverlays().add(mLocationOverlay);
				mMapView.getController().animateTo(pt);
			}
		}
	};
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case RM_UPDATELISTENER:
				mBMapMan.getLocationManager().removeUpdates(mLocationListener);
				break;
			case GET_CURRENT_CITY_NAME:
				if (mGetCurrentCityNameThread != null) {
					
				}
				else {
					mGetCurrentCityNameThread = new GetCurrentCityName(handler, mMapView);
				}
				mGetCurrentCityNameThread.run();
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
		//		WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);	//设置屏幕长亮
		mShowsatellite = (Button) findViewById(R.id.show_satellite);
		mShowtraffic = (Button) findViewById(R.id.show_traffic);
		mMapView = (MapView) findViewById(R.id.bmapsView);
		mShowMylocation = (Button) findViewById(R.id.my_location);
		mLocation = (Button) findViewById(R.id.location);
		mSearchBtn = (Button) findViewById(R.id.search_btn);
		mSearchText = (EditText) findViewById(R.id.search_text);
		mLocationView = (LinearLayout) findViewById(R.id.location_view);
		mSearchView = (RelativeLayout) findViewById(R.id.search_view);
		mShowSearch = (Button) findViewById(R.id.show_search);
		mSearchClose = (Button) findViewById(R.id.search_close);
		mShowTransitSearchViewBtn = (Button) findViewById(R.id.show_transitsearch_view);
		mTransitsearch_view = (LinearLayout) findViewById(R.id.transitsearch_view);
		mSearch_transit_close = (Button) findViewById(R.id.search_transit_close);
		mStart_site = (EditText) findViewById(R.id.start_site);
		mEnd_site = (EditText) findViewById(R.id.end_site);
		mSearch_transit_btn = (Button) findViewById(R.id.search_transit_btn);
		jc_search_transit_btn = (Button) findViewById(R.id.jc_search_transit_btn);
		bx_search_transit_btn = (Button) findViewById(R.id.bx_search_transit_btn);

		mBMapMan = new BMapManager(this);
		mBMapMan.init(getText(R.string.baidumap_key).toString(),// 输入百度地图授权key
				new MKGeneralListener() {
					@Override
					public void onGetNetworkState(int arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), getText(R.string.network_error), Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onGetPermissionState(int arg0) {
						// TODO Auto-generated method stub
						if (arg0 == MKEvent.ERROR_PERMISSION_DENIED) {
							Toast.makeText(getApplicationContext(), getText(R.string.permission_error), Toast.LENGTH_SHORT).show();
						}
					}
		});
		mBMapMan.getLocationManager().setNotifyInternal(8, 3);
		mBMapMan.start();
		super.initMapActivity(mBMapMan);
		mShowsatellite.setOnClickListener(mShowsatelliteListener);
		mShowtraffic.setOnClickListener(mShowtrafficListener);
		mShowMylocation.setOnClickListener(mShowmylocationListener);
		mLocation.setOnClickListener(mMyLocationListener);
		mSearchBtn.setOnClickListener(mSearchListener);
		mShowSearch.setOnClickListener(mShowSearchListener);
		mSearchClose.setOnClickListener(mSearchCloseListener);
		mShowTransitSearchViewBtn.setOnClickListener(mSearchTransitListener);
		mSearch_transit_close.setOnClickListener(mSearchTransitCloseListener);
		mSearch_transit_btn.setOnClickListener(mSearchTransitBtnListener);
		bx_search_transit_btn.setOnClickListener(bx_search_transit_btn_listener);
		jc_search_transit_btn.setOnClickListener(jc_search_transit_btn_listener);
		mMapView.setBuiltInZoomControls(true);
		mMapView.setDrawOverlayWhenZooming(true);
		mMapController = mMapView.getController();
		GeoPoint point = new GeoPoint((int) (59.915 * 1E6),	//初始化时地图默认的中心点
				(int) (136.404 * 1E6));
		mMapController.setCenter(point);
		mMapController.setZoom(12);
		mLocationOverlay = new MyLocationOverlay(getApplicationContext(), mMapView);
		mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
		
		mSearchMode = new MKSearch();
		mSearchMode.init(mBMapMan, new MKSearchListener() {

			@Override
			public void onGetAddrResult(MKAddrInfo result, int iError) {
				// TODO Auto-generated method stub
				if (iError == 0) {
					Log.d("lm", "当前地图中心点地址为:" + result.strAddr);
					mCurrentCityNameStr = getCurrentcityname(result.strAddr);
					Log.d("lm", mCurrentCityNameStr);
				}
				else if (iError == 100) {
					Log.d("lm", "结果正确但无相关地址信息");
				}
				else {
					Log.d("lm", "结果不正确");
				}
			}

			@Override
			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				// TODO Auto-generated method stub
				if (error != 0 || res == null) {
					Toast.makeText(getApplicationContext(), getText(R.string.search_noresult),
							Toast.LENGTH_SHORT).show();
				} else {
					// 将地图移动到第一个POI中心点
					if (res.getCurrentNumPois() > 0) {
						PoiOverlay poiOverlay = new PoiOverlay(
								MainActivity.this, mMapView);
						poiOverlay.setData(res.getAllPoi());
						mMapView.getOverlays().clear();
						mMapView.getOverlays().add(poiOverlay);
						mMapView.invalidate();
						mMapView.getController().animateTo(res.getPoi(0).pt);
					} else if (res.getCityListNum() > 0) {// 在多个城市找到搜索结果
						String strInfo = "in";
						for (int i = 0; i < res.getCityListNum(); i++) {
							strInfo += res.getCityListInfo(i).city;
							strInfo += ",";
						}
						strInfo += "find the search result";
						Toast.makeText(getApplicationContext(), strInfo,
								Toast.LENGTH_LONG).show();
					}
				}
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult res,
					int arg1) {
				// TODO Auto-generated method stub
				if (arg1 != 0 || res == null) {
					Toast.makeText(getApplicationContext(), getText(R.string.search_noresult), Toast.LENGTH_SHORT).show();
					return;
				}
				
				int nSize = res.getSuggestionNum();
				mStrSuggestions = new String[nSize];
				
				for (int i = 0; i < nSize; i++) {
					mStrSuggestions[i] = res.getSuggestion(i).city + res.getSuggestion(i).key;
				}
				ArrayAdapter<String> suggestionString = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, mStrSuggestions);
				//mSuggestionList.setAdapter(suggestionString);
			}

			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
				// TODO Auto-generated method stub
				if (error != 0 || res == null) {
					Toast.makeText(getApplicationContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(MainActivity.this, mMapView);
				//仅展示第一个驾车方案作为演示
				routeOverlay.setData(res.getPlan(0).getRoute(0));
				mMapView.getOverlays().clear();
				mMapView.getOverlays().add(routeOverlay);
				mMapView.invalidate();
				mMapView.getController().animateTo(res.getStart().pt);
			}

			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult res, int error) {
				// TODO Auto-generated method stub
				if (error != 0 || res == null) {
					Toast.makeText(getApplicationContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				TransitOverlay routeOverlay = new TransitOverlay(MainActivity.this, mMapView);
				//仅展示第一个公交方案作为演示
				routeOverlay.setData(res.getPlan(0));
				mMapView.getOverlays().clear();
				mMapView.getOverlays().add(routeOverlay);
				mMapView.invalidate();
				mMapView.getController().animateTo(res.getStart().pt);
			}

			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
				// TODO Auto-generated method stub
				if (error != 0 || res == null) {
					Toast.makeText(getApplicationContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(MainActivity.this, mMapView);
				//仅展示第一个步行方案作为演示
				routeOverlay.setData(res.getPlan(0).getRoute(0));
				mMapView.getOverlays().clear();
				mMapView.getOverlays().add(routeOverlay);
				mMapView.invalidate();
				mMapView.getController().animateTo(res.getStart().pt);
			}
		});
		
		if (mGetCurrentCityNameThread != null) {
			mGetCurrentCityNameThread.start();
		} else {
			mGetCurrentCityNameThread = new GetCurrentCityName(handler, mMapView);
			mGetCurrentCityNameThread.start();
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (mGetCurrentCityNameThread != null) {
			mGetCurrentCityNameThread = null;
		}
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		if (mBMapMan != null) {
			mBMapMan.getLocationManager().removeUpdates(mLocationListener);
			mLocationOverlay.disableMyLocation();
			mLocationOverlay.disableCompass();
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (mBMapMan != null) {
			mLocationOverlay.enableMyLocation();
			mLocationOverlay.enableCompass();
			mBMapMan.start();
			handler.sendEmptyMessageDelayed(RM_UPDATELISTENER, 5000);
		}
		super.onResume();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {	//屏幕旋转时不重新创建Activity
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// Nothing need to be done here
		}
		else {
			// Nothing need to be done here
		}
	}
	
	class GetCurrentCityName extends Thread {
		private Handler handler;
		private MapView mv;
		
		public GetCurrentCityName(Handler handler, MapView mv) {
			this.handler = handler;
			this.mv = mv;
		}
		
		public void run() {
			// test
			GeoPoint pt_test = mv.getMapCenter();
			if (mSearchMode.reverseGeocode(pt_test) == 0) {
				Log.d("lm", "get address info");
			}
			handler.sendEmptyMessageDelayed(GET_CURRENT_CITY_NAME, 2000);
		}
	}
	
	String getCurrentcityname(String str) {
		String cityname = null;
		String temp = str;
		int length = temp.length();
		int num = 0;
		for (int i = 0; i < length; i++) {
			if ((temp.substring(i, i + 1)).equals("市")) {
				num = i;
				break;
			}
		}
		cityname = temp.substring(0, num + 1);
		length = num + 1;
		for (int i = 0; i < length; i++) {
			if ((i + 1 <= length) && ((cityname.substring(i, i + 1)).equals("省") || (cityname.substring(i, i + 1)).equals("区"))) {
				temp = cityname.substring(i + 1, length);
				cityname = temp;
				break;
			}
		}
		return cityname;
	}
}
