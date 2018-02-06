package com.example.carcall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener{

	Button search , add , about
	 ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		intialize ();
	
	}

	private void intialize() {
		search = (Button) findViewById(R.id.search);
		add=(Button) findViewById(R.id.add);
		about=(Button) findViewById(R.id.about);
		
		search.setOnClickListener(this);
		add.setOnClickListener(this);
		about.setOnClickListener(this);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		Intent i ;
		int id = v.getId();
		switch (id) {
		case R.id.search:
			i =new Intent(Main.this,Search.class);
			startActivity(i);
			break;
		case R.id.add:
			 i =new Intent(Main.this,AddCar.class);
			startActivity(i);
			break;
		case R.id.about:
			 i =new Intent(Main.this,AboutUs.class);
			startActivity(i);
			break;
		default:
			break;
		}
		
	}
}
