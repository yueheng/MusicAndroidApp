package com.example.firstandroidapp;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.firstandroidapp.AlbumsParse.AlbumsListViewItem;
import com.example.firstandroidapp.ArtistsParse.ArtistsListViewItem;
import com.example.firstandroidapp.SongsParse.SongsListViewItem;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.facebook.FacebookException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ResultListActivity extends ListActivity {
	final Context context = this;

	private String results;
	private String title;
	private String type;

	private ListView resultList;
	public static Activity activity;
	private Button facebookButton;
	private Button sampleButton;
	private Dialog dialog;
	private int positionClick;

	List<ArtistsListViewItem> artistsItems;
	List<SongsListViewItem> songsItems;
	List<AlbumsListViewItem> albumsItems;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);

		Intent i = getIntent();
		Bundle extras = i.getExtras();
		results = extras.getString("results");
		title = extras.getString("title");
		type = extras.getString("type");

		resultList = getListView();

		if (type.equalsIgnoreCase("artists")) {
			showArtistsList();
		} else if (type.equalsIgnoreCase("albums")) {
			showAlbumsList();
		} else if (type.equalsIgnoreCase("songs")) {
			showSongsList();
		}

		resultList.setTextFilterEnabled(true);
		resultList.setOnItemClickListener(new ListItemClickListener());
	}

	private void showArtistsList() {
		ArtistsParse artistsParse = new ArtistsParse(results);
		artistsItems = artistsParse.toParse();
		ArtistsAdapter adapter = new ArtistsAdapter(this, artistsItems);
		resultList.setAdapter(adapter);
	}

	private void showAlbumsList() {
		AlbumsParse albumsParse = new AlbumsParse(results);
		albumsItems = albumsParse.toParse();
		AlbumsAdapter adapter = new AlbumsAdapter(this, albumsItems);
		resultList.setAdapter(adapter);
	}

	private void showSongsList() {
		SongsParse songsParse = new SongsParse(results);
		songsItems = songsParse.toParse();
		SongsAdapter adapter = new SongsAdapter(this, songsItems);
		resultList.setAdapter(adapter);
	}

	private class ListItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			positionClick = position;
			// custom dialog
			dialog = new Dialog(context);
			dialog.setTitle("Post to Facebook");

			if (type.equalsIgnoreCase("artists")
					|| type.equalsIgnoreCase("albums")) {
				dialog.setContentView(R.layout.popup_window);
			} else if (type.equalsIgnoreCase("songs")) {
				dialog.setContentView(R.layout.popup_window_songs);
				sampleButton = (Button) dialog.findViewById(R.id.sample_Button);
				sampleButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						MediaPlayer mediaPlayer = new MediaPlayer();
						mediaPlayer
								.setAudioStreamType(AudioManager.STREAM_MUSIC);
						try {
							mediaPlayer.setDataSource(songsItems
									.get(positionClick).sample);
							mediaPlayer.prepare();
							mediaPlayer.start();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalStateException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				});
			}
			facebookButton = (Button) dialog.findViewById(R.id.facebook_Button);
			facebookButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					publishFeedDialog();
				}
			});

			dialog.show();
		}
	}

	private void publishFeedDialog() {
		Bundle params = new Bundle();
		if (type.equals("artists")) 
			params = artistsBundle();
		else if (type.equals("albums"))
			params = albumsBundle();
		else if (type.equals("songs"))
			params = songsBundle();
		WebDialog feedDialog = (new WebDialog.Builder(this.context,
				"272940082843660", "feed", params)).setOnCompleteListener(
				new OnCompleteListener() {
					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						if (error == null) {
							// When the story is posted, echo the success
							// and the post Id.
							final String postId = values.getString("post_id");
							if (postId != null) {
								Toast.makeText(context,
										"Posted story, id: " + postId,
										Toast.LENGTH_SHORT).show();
							} else {
								// User clicked the Cancel button
								Toast.makeText(context, "Publish cancelled",
										Toast.LENGTH_SHORT).show();
							}
						} else if (error instanceof FacebookOperationCanceledException) {
							// User clicked the "x" button
							Toast.makeText(context, "Publish cancelled",
									Toast.LENGTH_SHORT).show();
						} else {
							// Generic, ex: network error
							Toast.makeText(context, "Error posting story",
									Toast.LENGTH_SHORT).show();
						}
					}

				}).build();
		feedDialog.show();
	}

	private Bundle artistsBundle() {
		Bundle params = new Bundle();
		params.putString("name", artistsItems.get(positionClick).name);
		params.putString("caption", "I like "
				+ artistsItems.get(positionClick).name
				+ " who is active since "
				+ artistsItems.get(positionClick).year);
		params.putString("description",
				"Genre of Music is: " + artistsItems.get(positionClick).genre);
		params.putString("link", artistsItems.get(positionClick).details);
		params.putString("picture", artistsItems.get(positionClick).cover);

		JSONObject here = new JSONObject();
		JSONObject properties = new JSONObject();
		try {
			here.put("text", "here");
			here.put("href", artistsItems.get(positionClick).details);
			properties.put("Look at details ", here);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		params.putString("properties", properties.toString());
		return params;
	}

	private Bundle albumsBundle() {
		Bundle params = new Bundle();
		params.putString("name", albumsItems.get(positionClick).title);
		params.putString("caption", "I like "
				+ albumsItems.get(positionClick).title + " released in "
				+ albumsItems.get(positionClick).year);
		params.putString("description",
				"Arist: " + albumsItems.get(positionClick).artist
						+ " & Genre: " + albumsItems.get(positionClick).genre);
		params.putString("link", albumsItems.get(positionClick).details);
		params.putString("picture", albumsItems.get(positionClick).cover);

		JSONObject here = new JSONObject();
		JSONObject properties = new JSONObject();
		try {
			here.put("text", "here");
			here.put("href", albumsItems.get(positionClick).details);
			properties.put("Look at details ", here);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		params.putString("properties", properties.toString());
		return params;
	}

	private Bundle songsBundle() {
		Bundle params = new Bundle();
		params.putString("name", songsItems.get(positionClick).title);
		params.putString("caption", "I like "
				+ songsItems.get(positionClick).title + " composed "
				+ songsItems.get(positionClick).composer);
		params.putString("description",
				"Performer: " + songsItems.get(positionClick).performer);
		params.putString("link", songsItems.get(positionClick).details);
		params.putString("picture", songsItems.get(positionClick).cover);

		JSONObject here = new JSONObject();
		JSONObject properties = new JSONObject();
		try {
			here.put("text", "here");
			here.put("href", songsItems.get(positionClick).details);
			properties.put("Look at details ", here);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		params.putString("properties", properties.toString());
		return params;
	}
}
