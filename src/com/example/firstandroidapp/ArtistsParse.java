package com.example.firstandroidapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArtistsParse {
	private String results;

	public ArtistsParse(String results) {
		this.results = results;

	}

	public List<ArtistsListViewItem> toParse() {

		List<ArtistsListViewItem> artistItems = new ArrayList<ArtistsListViewItem>();
		try {

			JSONObject jsonObject = new JSONObject(results);
			JSONObject jsonResults = jsonObject.getJSONObject("results");
			JSONArray jsonResult = jsonResults.getJSONArray("result");
			for (int i = 0; i < jsonResult.length(); i++) {
				JSONObject content = jsonResult.getJSONObject(i);
				final String yearJson = content.getString("year");
				final String nameJson = content.getString("title");
				final String genreJson = content.getString("genre");
				final String coverJson = content.getString("cover");
				final String detailsJson = content.getString("details");

				// mainActivity.alert("year:"+year+"; title="+title+" genre="+genre+" cover="+cover+" details="+details);
				artistItems.add(new ArtistsListViewItem() {
					{
						if (coverJson.equals("NA")) {
							cover = "http://www.allmusic.com/images/allmusic-logo-share.png";
						} else
							cover = coverJson;
						name = nameJson;
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
		return artistItems;
	}

	class ArtistsListViewItem {
		public String cover;
		public String name;
		public String genre;
		public String year;
		public String details;

	}
}
