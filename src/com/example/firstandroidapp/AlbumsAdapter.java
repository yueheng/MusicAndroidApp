package com.example.firstandroidapp;

import java.util.List;

import com.example.firstandroidapp.AlbumsParse.AlbumsListViewItem;
import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumsAdapter extends BaseAdapter {

	LayoutInflater inflater;
	List<AlbumsListViewItem> items;

	public AlbumsAdapter(Activity context, List<AlbumsListViewItem> items) {
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
		AlbumsListViewItem item = items.get(position);
		View vi = convertView;

		if (convertView == null)
			vi = inflater.inflate(R.layout.item_row_albums, null);

		TextView title = (TextView) vi.findViewById(R.id.title);
		TextView artist = (TextView) vi.findViewById(R.id.artist);
		TextView genre = (TextView) vi.findViewById(R.id.genre);
		TextView year = (TextView) vi.findViewById(R.id.year);

		new DownloadImageTask((ImageView) vi.findViewById(R.id.image))
				.execute(item.cover);

		title.setText("Title:\n" + item.title);
		artist.setText("Artist:\n" + item.artist);
		genre.setText("Genre:\n" + item.genre);
		year.setText("Year:\n" + item.year);
		return vi;
	}
}
