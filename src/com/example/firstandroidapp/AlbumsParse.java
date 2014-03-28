package com.example.firstandroidapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

public class AlbumsParse {
	private String results;

	public AlbumsParse(String results) {
		this.results = results;

	}

	public List<AlbumsListViewItem> toParse() {

		List<AlbumsListViewItem> albumItems = new ArrayList<AlbumsListViewItem>();
		try {

			JSONObject jsonObject = new JSONObject(results);
			JSONObject jsonResults = jsonObject.getJSONObject("results");
			JSONArray jsonResult = jsonResults.getJSONArray("result");
			for (int i = 0; i < jsonResult.length(); i++) {
				JSONObject content = jsonResult.getJSONObject(i);

				final String coverJson = content.getString("cover");
				final String titleJson = content.getString("title");
				final String artistJson = content.getString("artist");
				final String genreJson = content.getString("genre");
				final String yearJson = content.getString("year");
				final String detailsJson = content.getString("details");

				// mainActivity.alert("year:"+year+"; title="+title+" genre="+genre+" cover="+cover+" details="+details);
				albumItems.add(new AlbumsListViewItem() {
					{
						if (coverJson.equals("NA")) {
							cover = "http://www.allmusic.com/images/allmusic-logo-share.png";
						} else
							cover = coverJson;
						title = titleJson;
						artist = artistJson;
						genre = genreJson;
						year = yearJson;
						details = detailsJson;
					}
				});

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return albumItems;
	}

	class AlbumsListViewItem {
		public String cover;
		public String title;
		public String artist;
		public String genre;
		public String year;
		public String details;

	}
}
