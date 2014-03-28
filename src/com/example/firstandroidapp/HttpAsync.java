package com.example.firstandroidapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class HttpAsync extends AsyncTask<URL, Integer, String> {

	private static final int HTTP_STATUS_OK = 200;
	private MainActivity mainActivity;
	private String results = "";
	private String urlStr;
	private static byte[] buff = new byte[1024];

	public HttpAsync(MainActivity activity) {
		super();
		this.mainActivity = activity;
	}

	@Override
	protected String doInBackground(URL... params) {

		try {
			URL url = params[0];
			// String urlStr = url.toString();
			urlStr = url.toString();
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(urlStr);
			HttpResponse response = httpclient.execute(httpGet);

			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() != HTTP_STATUS_OK) {
				// handle error here

			}

			HttpEntity entity = response.getEntity();
			InputStream webs = entity.getContent();

			ByteArrayOutputStream content = new ByteArrayOutputStream();

			int readCount = 0;
			while ((readCount = webs.read(buff)) != -1) {
				content.write(buff, 0, readCount);
			}
			results = new String(content.toByteArray(), "UTF-8");

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return results;
	}

	/*
	 * @Override protected void onPostExecute(String results) { try { JSONObject
	 * jsonObject = new JSONObject(results); JSONObject jsonResults =
	 * jsonObject.getJSONObject("results"); JSONArray jsonResult =
	 * jsonResults.getJSONArray("result"); mainActivity.jsonResult = jsonResult;
	 * } catch (JSONException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

}
