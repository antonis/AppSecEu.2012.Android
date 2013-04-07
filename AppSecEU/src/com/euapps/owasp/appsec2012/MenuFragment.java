package com.euapps.owasp.appsec2012;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class MenuFragment extends ListFragment {

	private static MenuAdapter menuAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (menuAdapter == null)
			menuAdapter = new MenuAdapter(getActivity(), R.layout.menu_row,
					MenuItems.items);
		setListAdapter(menuAdapter);
		getListView().setDivider(
				this.getResources().getDrawable(android.R.color.white));
		getListView().setDividerHeight(2);
		getListView().setCacheColorHint(0);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		MainActivity.getInstance().setBackButtonIcon(false);
		MainActivity.getInstance().setView(position);
		((MenuActivity) getActivity()).getSlideoutHelper().close();
		setListAdapter(menuAdapter);
	}

	public static void setSelected(int position) {
		if (menuAdapter != null)
			menuAdapter.setSelected(position);
	}
}
