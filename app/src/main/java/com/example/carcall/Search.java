package com.example.carcall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Search extends Activity {
	String serviceUrl = "http://wikimasry.com/carCall/public/user/";
	EditText text;
	Button startsearch, resultbtn;
	ProgressBar pb;
	ArrayList<String> phonearray;
	private String phoneNumber = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		text = (EditText) findViewById(R.id.searchtxt);
		startsearch = (Button) findViewById(R.id.startSearch);
		startsearch.setVisibility(View.VISIBLE);
		resultbtn = (Button) findViewById(R.id.result);
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);


		resultbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pb.setVisibility(View.VISIBLE);
				try {
                        Log.e("CarTest", phoneNumber);
					String uri = "tel:"+phoneNumber;
					Intent callIntent = new Intent(Intent.ACTION_CALL, Uri
							.parse(uri));

					startActivity(callIntent);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Your call has failed...", Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
				}
			}
		});

		startsearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				phonearray = new ArrayList<String>();
				new SearchTask().execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class SearchTask extends AsyncTask<Void, Void, Void> {

		private JSONArray jsonArray;

		@Override
		protected Void doInBackground(Void... params) {
			Log.d("CarTest", "start background task");
			// HttpURLConnection urlConnection = null;
			try {
				String id = text.getText().toString();
				// String Test =serviceUrl+""+""+url+""+id ;
				URL urlToRequest = new URL(serviceUrl
						+ URLEncoder.encode(id, "UTF-8"));
				Log.e("CarTest", id);
				Log.e("CarTest", serviceUrl + URLEncoder.encode(id, "UTF-8"));
				URLConnection urlConnection = urlToRequest.openConnection();
				// urlConnection.setRequestProperty("Content-Type",
				// "text/html; charset=UTF-8");
				// urlConnection = (HttpURLConnection)
				// urlToRequest.openConnection();
				Log.e("CarTest",
						"2" + urlConnection.getRequestProperty("Content-Type"));

				// create JSON object from content
				// 1- get byte steam
				InputStream in = urlConnection.getInputStream();
				// 2- convert stream to string
				StringBuilder strB = null;
				try {
					BufferedReader input = new BufferedReader(
							new InputStreamReader(in, "UTF-8"));
					strB = new StringBuilder();
					String str;
					while (null != (str = input.readLine())) {
						strB.append(str).append("\r\n");
					}
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				String getResponseText = strB.toString();
				Log.e("CarTest", getResponseText);
				// 3- convert string to jsonArray
				jsonArray = new JSONArray(getResponseText);
				// 4- get your data from json
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					phoneNumber = jsonObject.getString("phoneNumber" + "");
					//phonearray.add(phoneNumber);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pb.setVisibility(View.VISIBLE);

		}

		@Override
		protected void onPostExecute(Void result) {
			pb.setVisibility(View.INVISIBLE);
			if (phoneNumber == null){
				resultbtn.setText("invalid car Number");
				Toast.makeText(Search.this,"invalid car Number",
						Toast.LENGTH_SHORT).show();
				}
			
			else
			resultbtn.setText(phoneNumber);
			resultbtn.setVisibility(View.VISIBLE);

			Toast.makeText(Search.this,result + "" + phoneNumber,
					Toast.LENGTH_SHORT).show();

			super.onPostExecute(result);
		}

	}

}
