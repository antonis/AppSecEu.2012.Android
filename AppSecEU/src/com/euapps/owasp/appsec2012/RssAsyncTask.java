package com.euapps.owasp.appsec2012;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;


import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

public class RssAsyncTask extends
		AsyncTask<RssAsyncTask.Payload, RssArticle, RssAsyncTask.Payload> {

	public static class Payload {
		String link;
		MainActivity reader;
		public ArrayList<RssArticle> result;

		public Payload(Object[] data) {
			link = (String) data[1];
			reader = (MainActivity) data[0];
		}
	}

	protected void onPostExecute(RssAsyncTask.Payload payload) {
		if (payload.result != null) {
			try {
				payload.reader.setArticles(payload.result);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		} else {// error
			payload.reader.setArticles(null);
		}
	}

	protected RssAsyncTask.Payload doInBackground(
			RssAsyncTask.Payload... params) {
		RssAsyncTask.Payload payload = params[0];
		try {
			URL url = new URL(payload.link);
			InputStream responseStream = url.openConnection().getInputStream();
			payload.result = parse(responseStream);
		} catch (Exception exception) {
			Log.e("RssAsyncTask", exception.getMessage());
			exception.printStackTrace();
			payload.result = null;
		}
		return payload;
	}

	public ArrayList<RssArticle> parse(InputStream inputStream) {
		Log.d("RssAsyncTask", "rss.parse.start");
		ArrayList<RssArticle> result = new ArrayList<RssArticle>();
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(inputStream, null);
			RssArticle current = null;
			String tag;
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				tag = parser.getName();
				if (eventType == XmlPullParser.START_TAG) {
					if (tag.equalsIgnoreCase("item")) {
						current = new RssArticle();
						Log.d("RssAsyncTask", "rss.parse.item");
					} else if (tag.equalsIgnoreCase("title")) {
						if (current != null)
							current.setTitle(parser.nextText());
						Log.d("RssAsyncTask", "rss.parse.title");
					} else if (tag.equalsIgnoreCase("link")) {
						if (current != null)
							current.setLink(parser.nextText());
						Log.d("RssAsyncTask", "rss.parse.link");
					} else if (tag.equalsIgnoreCase("description")) {
						if (current != null)
							current.setDescription(parser.nextText());
						Log.d("RssAsyncTask", "rss.parse.description");
					}
				} else if (eventType == XmlPullParser.END_TAG) {
					if (tag.equalsIgnoreCase("item")) {
						if (current != null)
							result.add(current);
						current = null;
					}
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			Log.e("RssAsyncTask", "rss.parse.exception");
			e.printStackTrace();
		}
		Log.d("RssAsyncTask", "rss.parse.end.count=" + result.size());
		return result;
	}

}
