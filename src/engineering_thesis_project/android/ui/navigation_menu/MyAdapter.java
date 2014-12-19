package engineering_thesis_project.android.ui.navigation_menu;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import engineering_thesis_project.android.ui.activities.R;

public final class MyAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<NavDrawerItem> itemList;

	public MyAdapter(Context context, int namesArrayID, int iconsArrayID) {
		this.context = context;
		String[] names = context.getResources().getStringArray(namesArrayID);
		int[] iconsIDs = new int[names.length];
		int j = 0;
		for (String ic : context.getResources().getStringArray(iconsArrayID)) {
			iconsIDs[j++] = context.getResources().getIdentifier(ic,
					"drawable", context.getPackageName());
		}

		itemList = new ArrayList<NavDrawerItem>();
		for (int i = 0; i < names.length; i++) {
			itemList.add(new NavDrawerItem(iconsIDs[i], names[i]));
		}
	}

	@Override
	public boolean isEnabled(int position) {
		// this is for title to be disabled item
		if (position == 0) {
			return false;
		}
		return super.isEnabled(position);
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.custom_lists_row, null);
		}

		if (itemList.get(position).isVisible()) {
			TextView title = (TextView) convertView
					.findViewById(R.id.title_in_row);
			ImageView icon = (ImageView) convertView
					.findViewById(R.id.icon_in_row);

			title.setText(itemList.get(position).getTitle());
			icon.setImageResource(itemList.get(position).getIconID());

		}

		if (position == 0) {
			// this is title
			convertView.setBackgroundColor(convertView.getResources().getColor(
					android.R.color.holo_blue_dark));
		}

		return convertView;
	}

	public ArrayList<NavDrawerItem> getItemList() {
		return itemList;
	}
}
