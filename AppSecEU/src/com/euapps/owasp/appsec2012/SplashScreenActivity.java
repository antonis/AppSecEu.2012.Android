package com.euapps.owasp.appsec2012;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class SplashScreenActivity extends Activity {

	private static final int SPLASH = R.drawable.splashscreen;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.splashscreen);

		ImageView image = (ImageView) findViewById(R.id.splashimage);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		image.setImageResource(SPLASH);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				finishedLoading();
			}
		}, 100);
	}

	public void finishedLoading() {
		final Intent intent = new Intent().setClass(this,
				MainActivity.class);
		SplashScreenActivity.this.startActivity(intent);
		SplashScreenActivity.this.finish();
	}

}
