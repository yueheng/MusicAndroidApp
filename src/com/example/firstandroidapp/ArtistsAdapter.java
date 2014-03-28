package com.example.firstandroidapp;

import java.io.InputStream;
import java.util.List;

import com.example.firstandroidapp.ArtistsParse.ArtistsListViewItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArtistsAdapter extends BaseAdapter {

	LayoutInflater inflater;
	List<ArtistsListViewItem> items;

	public ArtistsAdapter(Activity context, List<ArtistsListViewItem> items) {
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
		ArtistsListViewItem item = items.get(position);
		View vi = convertView;

		if (convertView == null)
			vi = inflater.inflate(R.layout.item_row, null);

		TextView title = (TextView) vi.findViewById(R.id.title);
		TextView genre = (TextView) vi.findViewById(R.id.genre);
		TextView year = (TextView) vi.findViewById(R.id.year);

		new DownloadImageTask((ImageView) vi.findViewById(R.id.image))
				.execute(item.cover);

		title.setText("Name:\n" + item.name);
		genre.setText("Genre:\n" + item.genre);
		year.setText("Year:\n" + item.year);
		return vi;
	}
}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	ImageView bmImage;

	public DownloadImageTask(ImageView bmImage) {
		this.bmImage = bmImage;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		bmImage.setImageBitmap(result);
	}
}