package com.euapps.owasp.appsec2012;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpeachAdapter extends ArrayAdapter<PlistSpeach> implements
		View.OnClickListener {

	private ArrayList<PlistSpeach> speaches;

	private Context context;

	private static class ViewHolder {
		View row;
		TextView title;
		TextView date;
		TextView times;
		TextView place;
		TextView speakers;
		int position;
	}

	public SpeachAdapter(Context context, int textViewResourceId,
			ArrayList<PlistSpeach> objects) {
		super(context, textViewResourceId, objects);
		this.speaches = objects;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.speach_row, null);

			holder = new ViewHolder();
			holder.row = convertView.findViewById(R.id.row);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.times = (TextView) convertView.findViewById(R.id.times);
			holder.speakers = (TextView) convertView
					.findViewById(R.id.speakers);
			holder.place = (TextView) convertView.findViewById(R.id.place);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnClickListener(this);

		holder.title.setText(speaches.get(position).getTitle());
		holder.place.setText(speaches.get(position).getPlace());
		holder.speakers.setText(speaches.get(position).getSpeakers());
		holder.position = position;

		SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+6"));// TODO: FIND A SAFER WAY
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm", Locale.US);
		stf.setTimeZone(TimeZone.getTimeZone("GMT+6"));// TODO: FIND A SAFER WAY
		holder.date.setText(sdf.format(speaches.get(position).getStart()));
		holder.times.setText(stf.format(speaches.get(position).getStart())
				+ " - " + stf.format(speaches.get(position).getEnd()));

		switch (speaches.get(position).getTrack()) {
		case 4:
			holder.row.setBackgroundColor(Color.parseColor("#3A89C9"));
			holder.title.setTextColor(Color.parseColor("#000000"));
			holder.date.setTextColor(Color.parseColor("#150517"));
			holder.times.setTextColor(Color.parseColor("#150517"));
			holder.place.setTextColor(Color.parseColor("#150517"));
			holder.speakers.setTextColor(Color.parseColor("#000000"));
			break;
		case 3:
			holder.row.setBackgroundColor(Color.parseColor("#E9F2F9"));
			holder.title.setTextColor(Color.parseColor("#000000"));
			holder.date.setTextColor(Color.parseColor("#150517"));
			holder.times.setTextColor(Color.parseColor("#150517"));
			holder.place.setTextColor(Color.parseColor("#150517"));
			holder.speakers.setTextColor(Color.parseColor("#000000"));
			break;
		case 2:
			holder.row.setBackgroundColor(Color.parseColor("#9CC4E4"));
			holder.title.setTextColor(Color.parseColor("#000000"));
			holder.date.setTextColor(Color.parseColor("#150517"));
			holder.times.setTextColor(Color.parseColor("#150517"));
			holder.place.setTextColor(Color.parseColor("#150517"));
			holder.speakers.setTextColor(Color.parseColor("#000000"));
			break;
		case 1:
			holder.row.setBackgroundColor(Color.parseColor("#F26C4F"));
			holder.title.setTextColor(Color.parseColor("#FFFFFF"));
			holder.date.setTextColor(Color.parseColor("#DEDEDE"));
			holder.times.setTextColor(Color.parseColor("#DEDEDE"));
			holder.place.setTextColor(Color.parseColor("#DEDEDE"));
			holder.speakers.setTextColor(Color.parseColor("#FFFFFF"));
			break;
		default:
			holder.row.setBackgroundColor(Color.parseColor("#1B325F"));
			holder.title.setTextColor(Color.parseColor("#FFFFFF"));
			holder.date.setTextColor(Color.parseColor("#DEDEDE"));
			holder.times.setTextColor(Color.parseColor("#DEDEDE"));
			holder.place.setTextColor(Color.parseColor("#DEDEDE"));
			holder.speakers.setTextColor(Color.parseColor("#FFFFFF"));
			break;
		}

		return convertView;
	}

	public int getCount() {
		return speaches.size();
	}

	public PlistSpeach getItem(int position) {
		return speaches.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public void onClick(View view) {
		Integer position = ((ViewHolder) view.getTag()).position;
		if (position != null) {
			PlistSpeach s = this.getItem(position);

			Log.d("onClick",
					"title=" + s.getTitle() + " >track=" + s.getTrack());
			if (s.getTrack() == 0)
				return;
			final Intent intent = new Intent().setClass(context,
					SpeachActivity.class);
			intent.putExtra(SpeachActivity.TITLE, s.getTitle());
			intent.putExtra(SpeachActivity.DESCRIPTION, s.getDescription());
			context.startActivity(intent);
		}
	}

}
