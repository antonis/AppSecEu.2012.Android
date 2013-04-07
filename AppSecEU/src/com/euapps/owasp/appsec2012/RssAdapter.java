package com.euapps.owasp.appsec2012;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.euapps.owasp.appsec2012.R;

public class RssAdapter extends ArrayAdapter<RssArticle> implements
		View.OnClickListener {

	private int textViewLayoutId;
	private ArrayList<RssArticle> articles;
	
	private Context context;

	private static class ViewHolder {
		TextView title;
		TextView description;
		int position;
	}
	
	public RssAdapter(Context context, int textViewResourceId,
			ArrayList<RssArticle> objects) {
		super(context, textViewResourceId, objects);
		this.articles = objects;
		this.context = context;
		this.textViewLayoutId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(textViewLayoutId, null);

			holder = new ViewHolder();
			holder.title = (TextView) convertView
					.findViewById(R.id.ArticleTitle);
			holder.description = (TextView) convertView
					.findViewById(R.id.ArticleDescription);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnClickListener(this);

		holder.title.setText(articles.get(position).getTitle());
		holder.description.setText(articles.get(position)
				.getNoHtmlDescription());
		holder.position = position;

		return convertView;
	}

	public int getCount() {
		return articles.size();
	}

	public RssArticle getItem(int position) {
		return articles.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public void onClick(View view) {
		Integer position = ((ViewHolder) view.getTag()).position;
		if (position != null) {
			Uri uri = Uri.parse(this.getItem(position).getLink());
			Intent webViewIntent = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(Intent
					.createChooser(webViewIntent, "Open in"));
		}
	}

}