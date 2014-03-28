package com.example.firstandroidapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;
import com.facebook.*;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Spinner typeSpinner;
	private TextView titleTextView;
	private Button submit;
	public JSONArray jsonResult;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
	}

	public void onClick(View v) {

		HttpAsync httpAsync = new HttpAsync(MainActivity.this);
		String results = "Cannot retrive data from AsyncTask";
		titleTextView = (TextView) findViewById(R.id.title);
		typeSpinner = (Spinner) findViewById(R.id.type);
		String title = titleTextView.getText().toString();
		title = title.replace('\n', ' ');
		title = title.replace(' ', '+');
		title = title.trim();
		String type = typeSpinner.getSelectedItem().toString();

		if (title.equals("")) {
			Toast.makeText(this, "Plsease enter title", Toast.LENGTH_LONG)
					.show();
			return;
		}

		try {
			String urlStr;

			urlStr = "http://cs-server.usc.edu:35266/examples/servlet/HelloWorldExample?title="
					+ URLEncoder.encode(title, "UTF-8") + "&type=" + type;

			URL url = new URL(urlStr);
			results = httpAsync.execute(url).get();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		if (results.length() == 0) {
			alert("Unable to find music data. Try again later.");
			return;
		}

		try {
			JSONObject jsonObject = new JSONObject(results);
			JSONObject jsonResults = jsonObject.getJSONObject("results");
			jsonResult = jsonResults.getJSONArray("result");
			if (jsonResult.get(0).toString().equals("{}")) {
				alert("No result is found.");
				return;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Intent intent = new Intent(MainActivity.this, ResultListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("results", results);
		bundle.putString("title", title);
		bundle.putString("type", type);
		intent.putExtras(bundle);
		startActivity(intent);

	}

	public void alert(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}

}