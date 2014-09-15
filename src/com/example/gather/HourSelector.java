package com.example.gather;

import java.util.Date;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class HourSelector extends ActionBarActivity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hour_selector);
		Intent i1 = getIntent();
		List<Date> dates = (List<Date>) (i1.getSerializableExtra("dates"));
		//Toast.makeText(HourSelector.this, dates.toString() ,Toast.LENGTH_SHORT).show();
		TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1);
		LinearLayout l1 = (LinearLayout) findViewById(R.id.LinearLayout1);
		TableLayout statictimes = new TableLayout(this);
		statictimes.setLayoutParams(tableParams);
		
		TableRow first = new TableRow(this);
		TextView firsttext = new TextView(this);
		firsttext.setText("");
		first.addView(firsttext);
		statictimes.addView(first);
		for (int j = 8; j < 24; j++)
		{
			TableRow tr = new TableRow(this);
			TextView t = new TextView(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			if (j < 12)
			{
				t.setText(Integer.toString(j) + ":00 AM");
			}
			else if (j == 12)
			{
				t.setText(Integer.toString(j) + ":00 PM");
			}
			else
			{
				t.setText(Integer.toString(j-12) + ":00 PM");
			}
			tr.addView(t);
			statictimes.addView(tr);
		}
		l1.addView(statictimes);
		for (int i = 0; i < dates.size(); i++)
		{
			Toast.makeText(HourSelector.this, dates.get(i).toString() ,Toast.LENGTH_SHORT).show();
			TableLayout tableLayout = new TableLayout(this);
			tableLayout.setLayoutParams(tableParams);
			l1.addView(tableLayout);
			for (int j = 0; j < 17; j++)
			{
				final TableRow tr = new TableRow(this);
				if (j == 0)
				{
					TextView t = new TextView(this);
					int day = dates.get(i).getDate();
					int month = dates.get(i).getMonth();
					t.setText(month+"/"+day);
					tr.addView(t);
				}
				else
				{
					//View v = new View(this);
					//v.setId(View.generateViewId());
					tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					//v.setBackgroundColor(Color.WHITE);
					//v.setClickable(true);
					/*v.setOnClickListener(new OnClickListener(){
					   public void onClick(View v) {
						   v.setBackgroundColor(Color.GREEN);
					   }
					   });
					tr.addView(v);*/
					tr.setClickable(true);
					tr.setBackgroundColor(Color.BLACK);
					tr.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							   tr.setBackgroundColor(Color.GREEN);
						   }
					});
					
				}
				tableLayout.addView(tr);
			}
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hour_selector, menu);
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
}
