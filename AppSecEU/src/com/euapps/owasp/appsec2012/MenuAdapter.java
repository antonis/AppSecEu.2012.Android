package com.euapps.owasp.appsec2012;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends ArrayAdapter<String> {

	private int selected = 0;

	public void setSelected(int selected) {
		this.selected = selected;
	}

	public MenuAdapter(Context context, int textViewResourceId, String[] objects) {
		super(context, textViewResourceId, objects);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.menu_row, null);
		}
		String item = this.getItem(position);
		if (item != null) {
			TextView title = (TextView) v.findViewById(R.id.title);
			ImageView icon = (ImageView) v.findViewById(R.id.icon);
			ImageView glow = (ImageView) v.findViewById(R.id.glow);
			glow.setVisibility(position == selected ? View.VISIBLE
					: View.INVISIBLE);
			title.setText(item);
			icon.setImageResource(MenuItems.getResourceDrawable(position));
		}
		return v;
	}
}
