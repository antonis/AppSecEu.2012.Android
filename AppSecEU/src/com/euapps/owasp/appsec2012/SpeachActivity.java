package com.euapps.owasp.appsec2012;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class SpeachActivity extends Activity {

	public final static String TITLE = "title";
	public final static String DESCRIPTION = "desc";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speach);
		final Bundle bundle = this.getIntent().getExtras();

		((ImageButton) findViewById(R.id.back))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						SpeachActivity.this.finish();
					}
				});

		((ImageButton) findViewById(R.id.share))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						String txt = "I'm attending " + bundle.getString(TITLE)
								+ " at #appseceu appsecresearch.org";

						final Intent intent = new Intent(Intent.ACTION_SEND);

						intent.setType("text/plain");
						intent.putExtra(Intent.EXTRA_SUBJECT,
								bundle.getString(TITLE));
						intent.putExtra(Intent.EXTRA_TEXT, txt);

						startActivity(Intent.createChooser(intent, "Share"));

					}
				});

		TextView title = (TextView) findViewById(R.id.title);
		TextView description = (TextView) findViewById(R.id.description);
		title.setText(bundle.getString(TITLE));
		description.setText(bundle.getString(DESCRIPTION));
	}

}
