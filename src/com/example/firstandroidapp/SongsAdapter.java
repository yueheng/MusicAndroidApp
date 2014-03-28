package com.example.firstandroidapp;

import java.util.List;
import com.example.firstandroidapp.SongsParse.SongsListViewItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SongsAdapter extends BaseAdapter {

	LayoutInflater inflater;
	List<SongsListViewItem> items;

	public SongsAdapter(Activity context, List<SongsListViewItem> items) {
		super();
		this.items = items;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		SongsListViewItem item = items.get(position);
		View vi = convertView;

		if (convertView == null)
			vi = inflater.inflate(R.layout.item_row, null);

		ImageView image = (ImageView) vi.findViewById(R.id.image);
		TextView title = (TextView) vi.findViewById(R.id.title);
		TextView genre = (TextView) vi.findViewById(R.id.genre);
		TextView year = (TextView) vi.findViewById(R.id.year);

		if (item.sample.equals("NA"))
			image.setImageResource(R.drawable.com_facebook_button_check_off);
		else
			image.setImageResource(R.drawable.com_facebook_button_check_on);
		title.setText("Title:\n" + item.title);
		genre.setText("Performer:\n" + item.performer);
		year.setText("Composer:\n" + item.composer);

		return vi;
	}
}
