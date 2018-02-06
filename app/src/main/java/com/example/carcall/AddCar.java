package com.example.carcall;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCar extends ActionBarActivity {
	EditText carEt, phoneEt;
	Button addnewCar;
	String uri = "http://wikimasry.com/carCall/public/user";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_car);
		intialize();

		addnewCar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String carNumber = carEt.getText().toString();
				String phoneNumber = phoneEt.getText().toString();
    
				if (validateInputs()){
					new sendData().execute(carNumber, phoneNumber);

				}
				
				else
					Toast.makeText(AddCar.this, "Please Enter correct data ", Toast.LENGTH_LONG).show();
				
			}
		});
	}

	private void intialize() {
		carEt = (EditText) findViewById(R.id.CarEditText);
		phoneEt = (EditText) findViewById(R.id.phoneEditText);
		addnewCar = (Button) findViewById(R.id.addBtn);

	}

	private boolean validateInputs() {
		if (carEt.getText().toString() != null
				&& !carEt.getText().toString().isEmpty()) {
		} else {
			Toast.makeText(getBaseContext(), "ERROR, Can't register carNumber",
					Toast.LENGTH_SHORT).show();
			return false ;
		}
		
	/*	if (carEt.getText().toString().length()==9) {
		} else {
			Toast.makeText(getBaseContext(), "ERROR, write correct carNumber",
					Toast.LENGTH_SHORT).show();
			return false ;

		}*/
		
		if (phoneEt.getText().toString() != null
				&& !phoneEt.getText().toString().isEmpty()) {
		} else {
			Toast.makeText(getBaseContext(), "ERROR, Can't register phoneNumber",
					Toast.LENGTH_SHORT).show();
			return false ;
		}
		
		if (phoneEt.getText().toString().length()==11){
			
		}
		else {
			Toast.makeText(getBaseContext(), "ERROR, please wrie 11 digit ",
					Toast.LENGTH_SHORT).show();
			return false ;
		}
		
            if (phoneEt.getText().toString().startsWith("01")){
			
		}
		else {
			Toast.makeText(getBaseContext(), "ERROR, please wrie correct number start 01 ",
					Toast.LENGTH_SHORT).show();
			return false ;
		}
		
		
		return true;
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_car, menu);
		return true;
	}

	public class sendData extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... paramter) {

			String carNumber = paramter[0];
			String phoneNumber = paramter[1];
				
			ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
			BasicNameValuePair value1 = new BasicNameValuePair("carNumber",
					carNumber);
			BasicNameValuePair value2 = new BasicNameValuePair("phoneNumber",
					phoneNumber);

			data.add(value1);
			data.add(value2);

			try {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(uri);
				post.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));
				Log.e("Data", " " + data);
				client.execute(post);
				Log.e("Data", " " + uri);

			} catch (Exception e) {
			}

			return "done";
			
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			
			Toast.makeText(AddCar.this, "starting connection .....", Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			Toast.makeText(AddCar.this,result,
					Toast.LENGTH_LONG).show();
			
/*						
					if (TextUtils.isEmpty(result) || result.equals("-1")) {
				Toast.makeText(AddCar.this,result + "",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(AddCar.this, result + "",
						Toast.LENGTH_SHORT).show();}
			
*/		}

	}

}
