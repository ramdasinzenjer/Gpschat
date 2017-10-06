package com.inzenjer.gpschat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.inzenjer.gpschat.connnectors.Connectivity;
import com.inzenjer.gpschat.connnectors.Constants;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Chatroom extends Activity{

	
	ListView mconversation; TextView tnam;
	StringBuffer outputstring;
	ArrayAdapter<String> mconvarrayadapter;
	EditText mout; String s_mout,shared_nam,shared_uid,hos,sdate,hos2;
	
	Timer t = new Timer();
	ApplicationPreference appPreference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatbox);
		mout=(EditText)findViewById(R.id.edit_text_out);
		tnam=(TextView)findViewById(R.id.tvnam);
		appPreference=(ApplicationPreference)getApplication();
		
		mconvarrayadapter= new ArrayAdapter<String>(this,R.layout.text);	
		mconversation=(ListView)findViewById(R.id.in);
		mconversation.setAdapter(mconvarrayadapter);
		
     SharedPreferences share=this.getSharedPreferences("mfrndid", MODE_WORLD_READABLE);
	shared_nam=share.getString("pnum","");

	shared_uid=appPreference.getUserId();
				
	tnam.setText("Chat with "+shared_nam);
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Calendar calobj = Calendar.getInstance();
		//System.out.println(df.format(calobj.getTime()));
		sdate=df.format(calobj.getTime());
		
		
		t.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
		      //Your task
		    	new MessageRecApiTask().execute();
		    	
		    }},0,1000);		
		
		
	}
	
	
	
	public void send_message(View view)
	{
		s_mout=mout.getText().toString();
		mconvarrayadapter.add("Me : "+s_mout);
		mout.setText(null);
		new MessageSendApiTask().execute();
	}
	
	public void back_page(View view)
	{
		Intent i=new Intent(getApplicationContext(),MainHome.class);
		startActivity(i);
		this.finish();		
	}
 
	 public void parsingmethod()
		{
		 mconvarrayadapter.clear();
			try
			{
				JSONObject jobject=new JSONObject(hos2);
				JSONObject jobject1=jobject.getJSONObject("Event");
				JSONArray ja=jobject1.getJSONArray("Details");
				int length=ja.length();
				for(int i=0;i<length;i++)
				{
					JSONObject data1=ja.getJSONObject(i);
					
					String smgin=data1.getString("message");
					String timein=data1.getString("dat_time");
					String sn_id=data1.getString("sen_id");
					if(sn_id.equals(shared_uid))
					{
 					mconvarrayadapter.add("Me : "+smgin);
					}
					else
					{
						mconvarrayadapter.add(""+smgin);
					}									
				}
			}
			catch(Exception e)
			{
				System.out.println("error:"+e);
			}
		}
	 
	    
	    public class MessageRecApiTask extends AsyncTask<String,String,String> {
		    
		    @Override
		    protected String doInBackground(String... params) {


		            String urlParameters = null;
		            try {
		                urlParameters =  "sen_id=" + URLEncoder.encode(shared_uid, "UTF-8") + "&&"
		                        + "rec_id=" + URLEncoder.encode(shared_nam, "UTF-8");
		            } catch (UnsupportedEncodingException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }		

		            hos2 = Connectivity.excutePost(Constants.MESSAGERECIEVE_URL,
		                    urlParameters);
		            Log.e("You are at", "" + hos2);

		       return hos2;
		    }

		    @Override
		    protected void onPostExecute(String s) {
		        super.onPostExecute(s);
		        
		      //  linlaHeaderProgress.setVisibility(View.GONE);
		        if(hos2.contains("success"))
		        {
		        	        	
					parsingmethod();
		        
		        }
		        else
		        {
		        	Toast.makeText(getApplicationContext(), "Network Error, Try again later.."+hos2, 
		        			Toast.LENGTH_SHORT).show();
		        }
		        
		    }

		    @Override
		    protected void onPreExecute() {
		        super.onPreExecute();

		    }
		}
	  
	    public class MessageSendApiTask extends AsyncTask<String,String,String> {
		    
		    @Override
		    protected String doInBackground(String... params) {


		            String urlParameters = null;
		            try {
		                urlParameters =  "sen_id=" + URLEncoder.encode(shared_uid, "UTF-8") + "&&"
		                        + "rec_id=" + URLEncoder.encode(shared_nam, "UTF-8")+ "&&" 
		                        + "message=" + URLEncoder.encode(s_mout, "UTF-8")+ "&&" 
		                        + "dat_time=" + URLEncoder.encode(sdate, "UTF-8");
		            } catch (UnsupportedEncodingException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }		

		            hos = Connectivity.excutePost(Constants.MESSAGESEND_URL,
		                    urlParameters);
		            Log.e("You are at", "" + hos);

		       return hos;
		    }

		    @Override
		    protected void onPostExecute(String s) {
		        super.onPostExecute(s);
		        
		        if(hos.contains("success"))
				{
					Toast.makeText(getApplicationContext(), 
							"Message sent", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), 
							"Network Error, Try again later...", Toast.LENGTH_SHORT).show();
				}
		        
		    }

		    @Override
		    protected void onPreExecute() {
		        super.onPreExecute();

		    }
		}
	    
	    @Override
	    public void onBackPressed() {
	    	// TODO Auto-generated method stub
	    	super.onBackPressed();
	    	t.cancel();
	    	this.finish();
	    }
	   
}
