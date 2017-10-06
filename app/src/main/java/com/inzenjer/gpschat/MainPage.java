package com.inzenjer.gpschat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.inzenjer.gpschat.connnectors.Connectivity;
import com.inzenjer.gpschat.connnectors.Constants;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainPage extends Activity {

	EditText ephn,eps; String sphn,sps,resp; Button blg;
	TextView txtreg; ApplicationPreference applicationPreference;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        ephn=(EditText)findViewById(R.id.etphn);
        eps=(EditText)findViewById(R.id.etps);
        txtreg=(TextView)findViewById(R.id.mtxtreg);
        blg=(Button)findViewById(R.id.bt_lg);
        applicationPreference=(ApplicationPreference)getApplication();
        
        blg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sphn=ephn.getText().toString();
				sps=eps.getText().toString();
				
				new LoginApiTask().execute();
			}
		});
        
        txtreg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(getApplicationContext(),Regpage.class);
				startActivity(i);
			}
		});
        
    }
    
    
    public class LoginApiTask extends AsyncTask<String,String,String> {
	    
	    @Override
	    protected String doInBackground(String... params) {


	            String urlParameters = null;
	            try {
	                urlParameters =  "phone=" + URLEncoder.encode(sphn, "UTF-8") + "&&"
	                        + "password=" + URLEncoder.encode(sps, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            resp = Connectivity.excutePost(Constants.LOGIN_URL,
	                    urlParameters);
	            Log.e("You are at", "" + resp);

	       return resp;
	    }

	    @Override
	    protected void onPostExecute(String s) {
	        super.onPostExecute(s);
	        	              
	        if(resp.contains("success"))
	        {
	        	
	        Toast.makeText(getApplicationContext(), ""+resp, Toast.LENGTH_SHORT).show();
	        applicationPreference.setLoginStatus(true);
	        applicationPreference.setUserId(sphn);
	        Intent i=new Intent(getApplicationContext(),MainHome.class);
	        startActivity(i);
	        
	        }
	        else
	        {
	        	Toast.makeText(getApplicationContext(), ""+resp, Toast.LENGTH_SHORT).show();
	        }
	        
	    }

	}
    
    
}
