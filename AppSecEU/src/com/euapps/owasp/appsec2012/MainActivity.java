package com.euapps.owasp.appsec2012;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.longevitysoft.android.xml.plist.PListXMLHandler;
import com.longevitysoft.android.xml.plist.PListXMLParser;
import com.longevitysoft.android.xml.plist.domain.Array;
import com.longevitysoft.android.xml.plist.domain.Dict;
import com.longevitysoft.android.xml.plist.domain.PList;
import com.longevitysoft.android.xml.plist.domain.PListObject;
import com.readystatesoftware.maps.OnSingleTapListener;
import com.readystatesoftware.maps.TapControlledMapView;

public class MainActivity extends MapActivity {

	private static final int VISIBLE_LEFT_VIEW = 80;

	private ListView mList;
	private TextView empty;
	private TapControlledMapView mapView;

	private static MainActivity instance;

	public static MainActivity getInstance() {
		return instance;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		instance = this;
		mList = (ListView) findViewById(android.R.id.list);
		empty = (TextView) findViewById(android.R.id.empty);
		mapView = (TapControlledMapView) findViewById(R.id.mapview);
		final ImageButton b = (ImageButton) findViewById(R.id.back);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuButtonPressed();
			}
		});
		setView(0);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				menuButtonPressed();
			}
		}, 1000);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Log.d("onKeyDown", "MENU pressed");
			menuButtonPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void menuButtonPressed() {
		setBackButtonIcon(true);
		int width = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, VISIBLE_LEFT_VIEW, getResources()
						.getDisplayMetrics());
		SlideoutActivity.prepare(MainActivity.this, R.id.inner_content, width);
		startActivity(new Intent(MainActivity.this, MenuActivity.class));
		overridePendingTransition(0, 0);
	}

	public void setBackButtonIcon(boolean back) {
		((ImageButton) findViewById(R.id.back))
				.setBackgroundResource(back ? R.drawable.m_menu_back
						: R.drawable.m_menu);
	}

	private void setViewTitle(String title) {
		((TextView) findViewById(R.id.header)).setText(title);
	}

	public void setView(int id) {
		MenuFragment.setSelected(id);
		switch (id) {
		case MenuItems.iSchedule:
			setViewTitle(MenuItems.sSchedule);
			loadSchedule();
			break;
		case MenuItems.iNews:
			setViewTitle(MenuItems.sNews);
			loadRssView("http://www.appsecresearch.org/feed/");
			break;
		case MenuItems.iTraining:
			setViewTitle(MenuItems.sTraining);
			loadWebView("training.html");
			break;
		case MenuItems.iSocial:
			setViewTitle(MenuItems.sSocial);
			loadWebView("social.html");
			break;
		case MenuItems.iMap:
			setViewTitle(MenuItems.sMap);
			loadMapView();
			break;
		case MenuItems.iVenue:
			setViewTitle(MenuItems.sVenue);
			loadWebView("venue.png");
			break;
		case MenuItems.iTransport:
			setViewTitle(MenuItems.sTransport);
			loadWebView("transport.html");
			break;
		case MenuItems.iTravel:
			setViewTitle(MenuItems.sTravel);
			loadWebView("travel.html");
			break;
		case MenuItems.iSponsors:
			setViewTitle(MenuItems.sSponsors);
			loadWebView("sponsors.html");
			break;
		case MenuItems.iInfo:
			setViewTitle(MenuItems.sInfo);
			loadWebView("info.html");
			break;
		case MenuItems.iAbout:
			setViewTitle(MenuItems.sAbout);
			loadWebView("licenses.txt");
			break;
		default:
			setViewTitle("Error");
			setEmptyView();
			break;
		}
	}

	private void setEmptyView() {
		findViewById(R.id.viewEmpty).setVisibility(View.VISIBLE);
		findViewById(R.id.viewList).setVisibility(View.GONE);
		findViewById(R.id.viewWebView).setVisibility(View.GONE);
		findViewById(R.id.viewMap).setVisibility(View.GONE);
	}

	/**
	 * Map Content
	 */

	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private Drawable drawable2;
	private SimpleItemizedOverlay venuesOverlay;
	private SimpleItemizedOverlay transportOverlay;

	private void loadMapView() {
		findViewById(R.id.viewEmpty).setVisibility(View.GONE);
		findViewById(R.id.viewList).setVisibility(View.GONE);
		findViewById(R.id.viewWebView).setVisibility(View.GONE);
		findViewById(R.id.viewMap).setVisibility(View.VISIBLE);

		mapView.setBuiltInZoomControls(true);
		// dismiss balloon upon single tap of MapView (iOS behavior)
		mapView.setOnSingleTapListener(new OnSingleTapListener() {
			@Override
			public boolean onSingleTap(MotionEvent e) {
				venuesOverlay.hideAllBalloons();
				return true;
			}
		});

		mapOverlays = mapView.getOverlays();

		MyLocationOverlay myLocationOverlay = new MyLocationOverlay(this,
				mapView);
		myLocationOverlay.enableMyLocation();
		mapOverlays.add(myLocationOverlay);

		GeoPoint ditUoA = new GeoPoint((int) (37.96830 * 1E6),
				(int) (23.76675 * 1E6));
		GeoPoint metroEvagelismos = new GeoPoint((int) (37.97625 * 1E6),
				(int) (23.74708 * 1E6));
		GeoPoint evagelismos224 = new GeoPoint((int) (37.97646 * 1E6),
				(int) (23.74809 * 1E6));
		GeoPoint divaniCaravel = new GeoPoint((int) (37.973204 * 1E6),
				(int) (23.751390 * 1E6));
		GeoPoint kostisPalamas = new GeoPoint((int) (37.980367 * 1E6),
				(int) (23.735447 * 1E6));
		GeoPoint panepistimioStation = new GeoPoint((int) (37.980122 * 1E6),
				(int) (23.733205 * 1E6));
		GeoPoint yaCafe = new GeoPoint((int) (37.986710 * 1E6),
				(int) (23.774714 * 1E6));
		GeoPoint katechakiStation = new GeoPoint((int) (37.992984 * 1E6),
				(int) (23.776088 * 1E6));

		drawable = getResources().getDrawable(R.drawable.marker);
		venuesOverlay = new SimpleItemizedOverlay(drawable, mapView);
		venuesOverlay.setShowClose(false);
		venuesOverlay.setSnapToCenter(false);

		OverlayItem ditUoAItem = new OverlayItem(ditUoA,
				"University of Athens",
				"Department of Informatics and Telecommunications");
		venuesOverlay.addOverlay(ditUoAItem);
		OverlayItem divaniCaravelItem = new OverlayItem(divaniCaravel,
				"Divani Caravel", "Hotel");
		venuesOverlay.addOverlay(divaniCaravelItem);
		OverlayItem kostisPalamasItem = new OverlayItem(kostisPalamas,
				"Kostis Palamas", "Social Event");
		venuesOverlay.addOverlay(kostisPalamasItem);
		OverlayItem yaCafeItem = new OverlayItem(yaCafe, "Ya Cafe",
				"Social Event");
		venuesOverlay.addOverlay(yaCafeItem);

		mapOverlays.add(venuesOverlay);

		drawable2 = getResources().getDrawable(R.drawable.marker2);
		transportOverlay = new SimpleItemizedOverlay(drawable2, mapView);
		transportOverlay.setShowClose(false);
		transportOverlay.setSnapToCenter(false);

		OverlayItem metroEvagelismosItem = new OverlayItem(metroEvagelismos,
				"Evangelismos", "Metro station");
		transportOverlay.addOverlay(metroEvagelismosItem);
		OverlayItem evagelismos224Item = new OverlayItem(evagelismos224,
				"Bus stop", "Take 250 bus, 224 bus or 01 minibus");
		transportOverlay.addOverlay(evagelismos224Item);
		OverlayItem panepistimioStationItem = new OverlayItem(
				panepistimioStation, "Panepistimio", "Metro station");
		transportOverlay.addOverlay(panepistimioStationItem);
		OverlayItem katechakiStationItem = new OverlayItem(katechakiStation,
				"Katechaki", "Metro station");
		transportOverlay.addOverlay(katechakiStationItem);

		mapOverlays.add(transportOverlay);

		mapView.getController().animateTo(ditUoA);
		mapView.getController().setZoom(15);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Web Content
	 */

	private void loadWebView(String asset) {
		findViewById(R.id.viewEmpty).setVisibility(View.GONE);
		findViewById(R.id.viewList).setVisibility(View.GONE);
		findViewById(R.id.viewWebView).setVisibility(View.VISIBLE);
		findViewById(R.id.viewMap).setVisibility(View.GONE);
		WebView mWebView = (WebView) findViewById(R.id.webview);
		mWebView.loadUrl("file:///android_asset/" + asset);
	}

	/**
	 * Schedule
	 */
	private static ArrayList<PlistSpeach> plistSpeaches;

	private ArrayList<PlistSpeach> getSpeaches() {
		if (plistSpeaches == null) {
			plistSpeaches = new ArrayList<PlistSpeach>();
			PListXMLParser parser = new PListXMLParser();
			PListXMLHandler handler = new PListXMLHandler();
			parser.setHandler(handler);
			try {
				parser.parse(getResources().openRawResource(R.raw.appdata));
			} catch (Exception e) {
				e.printStackTrace();
			}

			PList plist = ((PListXMLHandler) parser.getHandler()).getPlist();
			PListObject root = plist.getRootElement();
			if (root instanceof Dict) {
				Dict rootDict = (Dict) plist.getRootElement();
				PListObject speaches = rootDict
						.getConfigurationObject("speaches");
				if (speaches instanceof Array) {
					Array speachesArray = (Array) speaches;
					for (int i = 0; i < speachesArray.size(); i++) {
						PListObject d = speachesArray.get(i);
						if (d instanceof Dict) {
							Dict speach = (Dict) d;
							PlistSpeach s = new PlistSpeach();
							if (speach
									.getConfigurationObject(PlistSpeach.TITLE) instanceof com.longevitysoft.android.xml.plist.domain.String) {
								s.setTitle(((com.longevitysoft.android.xml.plist.domain.String) speach
										.getConfigurationObject(PlistSpeach.TITLE))
										.getValue());
							}
							if (speach
									.getConfigurationObject(PlistSpeach.DESCRIPTION) instanceof com.longevitysoft.android.xml.plist.domain.String) {
								s.setDescription(((com.longevitysoft.android.xml.plist.domain.String) speach
										.getConfigurationObject(PlistSpeach.DESCRIPTION))
										.getValue());
							}
							if (speach
									.getConfigurationObject(PlistSpeach.START) instanceof com.longevitysoft.android.xml.plist.domain.Date) {
								s.setStart(((com.longevitysoft.android.xml.plist.domain.Date) speach
										.getConfigurationObject(PlistSpeach.START))
										.getValue());
							}
							if (speach.getConfigurationObject(PlistSpeach.END) instanceof com.longevitysoft.android.xml.plist.domain.Date) {
								s.setEnd(((com.longevitysoft.android.xml.plist.domain.Date) speach
										.getConfigurationObject(PlistSpeach.END))
										.getValue());
							}
							if (speach
									.getConfigurationObject(PlistSpeach.PLACE) instanceof com.longevitysoft.android.xml.plist.domain.String) {
								s.setPlace(((com.longevitysoft.android.xml.plist.domain.String) speach
										.getConfigurationObject(PlistSpeach.PLACE))
										.getValue());
							}
							if (speach
									.getConfigurationObject(PlistSpeach.TRACK) instanceof com.longevitysoft.android.xml.plist.domain.Integer) {
								s.setTrack(((com.longevitysoft.android.xml.plist.domain.Integer) speach
										.getConfigurationObject(PlistSpeach.TRACK))
										.getValue());
							}
							if (speach
									.getConfigurationObject(PlistSpeach.SPEAKERS) instanceof com.longevitysoft.android.xml.plist.domain.Array) {
								Array speakers = ((com.longevitysoft.android.xml.plist.domain.Array) speach
										.getConfigurationObject(PlistSpeach.SPEAKERS));
								String speakersString = "";
								for (int j = 0; j < speakers.size(); j++) {
									if (speakers.get(j) instanceof com.longevitysoft.android.xml.plist.domain.String) {
										String sp = ((com.longevitysoft.android.xml.plist.domain.String) speakers
												.get(j)).getValue();
										speakersString += sp + " ";
									}
								}
								s.setSpeakers(speakersString);
							}
							plistSpeaches.add(s);
						}
					}

				}
			}
		}
		return plistSpeaches;
	}

	private void loadSchedule() {
		findViewById(R.id.viewEmpty).setVisibility(View.GONE);
		findViewById(R.id.viewList).setVisibility(View.VISIBLE);
		findViewById(R.id.viewWebView).setVisibility(View.GONE);
		findViewById(R.id.viewMap).setVisibility(View.GONE);
		mList.setAdapter(null);
		empty.setText("Loading...");
		SpeachAdapter speachAdapter = new SpeachAdapter(this,
				R.layout.speach_row, getSpeaches());
		mList.setAdapter(speachAdapter);
	}

	/**
	 * RSS
	 */
	private void loadRssView(String rssUrl) {
		findViewById(R.id.viewEmpty).setVisibility(View.GONE);
		findViewById(R.id.viewList).setVisibility(View.VISIBLE);
		findViewById(R.id.viewWebView).setVisibility(View.GONE);
		findViewById(R.id.viewMap).setVisibility(View.GONE);
		mList.setAdapter(null);
		empty.setText("Loading...");
		new RssAsyncTask().execute(new RssAsyncTask.Payload(new Object[] {
				MainActivity.this, rssUrl }));
	}

	public void setArticles(ArrayList<RssArticle> articles) {
		if (articles != null) {
			RssAdapter articlesAdapter = new RssAdapter(this,
					R.layout.rss_item, articles);
			mList.setAdapter(articlesAdapter);
		} else {
			Log.e("MainActivity", "rss.parse.exception");
			empty.setText("Error fetching data");
		}
	}

}
