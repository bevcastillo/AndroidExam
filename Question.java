package com.example.eggsam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Question extends Activity implements OnItemClickListener, OnClickListener {

	ListView lv;
	ArrayList<MyQuestion> list=new ArrayList<MyQuestion>();
	ArrayAdapter<MyQuestion> adapter;
	//
	AlertDialog.Builder builder;
	AlertDialog dialog;
	LinearLayout view;
	EditText myans;
	TextView myquestion;
	//
	String questionno;
	String answer;
	String idno;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.question);
		this.lv=(ListView) this.findViewById(R.id.listView1);
		
		Bundle b=this.getIntent().getExtras();
		String data=b.getString("question");
		idno=b.getString("idno");
		
		try {
			JSONObject json=new JSONObject(data);
			JSONArray jarray=json.getJSONArray("question");
				for(int i=0;i<jarray.length();i++){
					JSONObject obj=jarray.getJSONObject(i);
					String id=obj.getString("id");
					String question=obj.getString("question");
					MyQuestion q=new MyQuestion(id,question);
					list.add(q);
				}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.adapter=new ArrayAdapter<MyQuestion>(this,android.R.layout.simple_list_item_1,list);
		this.lv.setAdapter(adapter);
		this.lv.setOnItemClickListener(this);
		//
		myquestion=new TextView(this);
		myans=new EditText(this);
		myans.setHint("Type Answer CAREFULLY ");
		view=new LinearLayout(this);
		view.setPadding(5, 5, 5, 5);
		view.setOrientation(LinearLayout.VERTICAL);
		view.addView(myquestion);
		view.addView(myans);
		
		//
		builder=new AlertDialog.Builder(this);
		builder.setView(view);
		builder.setTitle("Question ");
		builder.setPositiveButton("OK", this);
		builder.setNegativeButton("Cancel", this);
		dialog=builder.create();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		MyQuestion q=list.get(arg2);
		questionno=arg2+1+"";
		builder.setTitle("Question "+arg2);
		myquestion.setText(q.getQuestion());
		dialog.show();
		
	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		
		int id=arg1;
		switch(id){
		case DialogInterface.BUTTON_POSITIVE:
			answer=this.myans.getText().toString();
			try {
				URL url=new URL("http://172.19.131.99/sf/model/answer.php?idno="+idno+"&questionno="+questionno+"&answer="+answer);
				HttpURLConnection conn=(HttpURLConnection) url.openConnection();
				BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String result=br.readLine();
				conn.disconnect();
				
				Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
				
				myans.setText("");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		case DialogInterface.BUTTON_NEGATIVE:
			dialog.dismiss();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		try {
			URL url=new URL("http://172.19.131.99/sf/model/score.php?idno="+idno);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String result=br.readLine();
			conn.disconnect();
			
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			
		} catch (MalformedURLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
}
