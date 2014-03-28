package com.example.firstandroidapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SongsParse {
	private String results;

	public SongsParse(String results) {
		this.results = results;

	}

	public List<SongsListViewItem> toParse() {

		List<SongsListViewItem> songsItems = new ArrayList<SongsListViewItem>();
		try {

			JSONObject jsonObject = new JSONObject(results);
			JSONObject jsonResults = jsonObject.getJSONObject("results");
			JSONArray jsonResult = jsonResults.getJSONArray("result");
			for (int i = 0; i < jsonResult.length(); i++) {
				JSONObject content = jsonResult.getJSONObject(i);
				final String sampleJson = content.getString("sample");
				final String titleJson = content.getString("title");
				final String performerJson = content.getString("performer");
				final String composerJson = content.getString("composer");
				final String detailsJson = content.getString("details");

				// mainActivity.alert("year:"+year+"; title="+title+" genre="+genre+" cover="+cover+" details="+details);
				songsItems.add(new SongsListViewItem() {
					{

						sample = sampleJson;
						title = titleJson;
						performer = performerJson;
						composer = composerJson;
						details = detailsJson;
					}
				});

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return songsItems;
	}

	class SongsListViewItem {
		public String cover = "http://www.allmusic.com/images/allmusic-logo-share.png";
		public String sample;
		public String title;
		public String performer;
		public String composer;
		public String details;

	}
}
