package com.example.eggsam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	EditText txtIdno;
	Button btnLogin;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //allow this activity to run a separate thread
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        
        this.txtIdno=(EditText) this.findViewById(R.id.editText1);
        this.btnLogin=(Button) this.findViewById(R.id.button1);
        this.btnLogin.setOnClickListener(this);
    }


	@Override
	public void onClick(View arg0) {
		String idno=this.txtIdno.getText().toString();
		try {
			URL url=new URL("http://172.19.131.99/sf/model/login.php?idno="+idno);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String data=br.readLine();
			conn.disconnect();
			
			Intent intent=new Intent(this,Question.class);
			intent.putExtra("question", data);
			intent.putExtra("idno", idno);
			this.startActivity(intent);
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    
    
}
