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
import android.widget.Toast;

public class Regpage extends Activity{
	
	EditText enm,epn,eem,eps,eadd;
	Button brg; String snm,spn,sem,sps,sadd,response;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_page);
		
		enm=(EditText)findViewById(R.id.etr_name);
		epn=(EditText)findViewById(R.id.etr_phone);
		eem=(EditText)findViewById(R.id.etr_email);
		eps=(EditText)findViewById(R.id.etr_password);
		eadd=(EditText)findViewById(R.id.etr_address);
        
		brg=(Button)findViewById(R.id.bt_reg);
		brg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				snm=enm.getText().toString();
				spn=epn.getText().toString();
				sem=eem.getText().toString();
				sps=eps.getText().toString();
				sadd=eadd.getText().toString();
				
				if(getValidate(snm, spn, sem, sps, sadd))
				{
					new RegisterApiTask().execute();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Validation Error", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
	}

	 public class RegisterApiTask extends AsyncTask<String,String,String> {
		    
		    @Override
		    protected String doInBackground(String... params) {


		            String urlParameters = null;
		            try {
		                urlParameters =  "name=" + URLEncoder.encode(snm, "UTF-8") + "&&"
		                		+"address=" + URLEncoder.encode(sadd, "UTF-8") + "&&"
		                		+"phone=" + URLEncoder.encode(spn, "UTF-8") + "&&"
		                		+"email=" + URLEncoder.encode(sem, "UTF-8") + "&&"
		                        + "password=" + URLEncoder.encode(sps, "UTF-8");
		            } catch (UnsupportedEncodingException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }

		            response = Connectivity.excutePost(Constants.REGISTER_URL,
		                    urlParameters);
		            Log.e("You are at", "" + response);

		       return response;
		    }

		    @Override
		    protected void onPostExecute(String s) {
		        super.onPostExecute(s);
		        	              
		        if(response.contains("success"))
		        {
		        	
		        Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
		        Intent i=new Intent(getApplicationContext(),MainPage.class);
		        startActivity(i);
		        
		        }
		        else
		        {
		        	Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
		        }
		        
		    }

		}
	 
	 private boolean getValidate(String st_name,String st_phone, String st_email, String st_password, String st_sddress) {
	      
			
			if (st_password.length()==0)
	        {
	            eps.setError("Please enter Password");
	            return false;
	        }
	        if (st_sddress.length()==0)
	        {
	            eadd.setError("Can't be Empty");
	            return false;
	        }
	        if (st_phone.length()!=10)
	        {
	            epn.setError("Invalid phone No:");
	            return false;
	        }
	        if (st_name.length()==0)
	        {
	            enm.setError("Enter the Name");
	            return false;
	        }
	        if(st_email.indexOf("@")==-1)
	        {
	        	eem.setError("Invalid emailId");
	            return false;
	        }

	        return true;
	    }
	
}
