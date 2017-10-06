package com.inzenjer.gpschat;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.inzenjer.gpschat.connnectors.Connectivity;
import com.inzenjer.gpschat.connnectors.Constants;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Friend_req extends Activity{
	
	ListView listreq;	ListAdapter req_adapter;
	ArrayList<HashMap<String, String>> oslist_req = new ArrayList<HashMap<String, String>>();
	
	String mphone,sfn;  String nm,mail,adr;	TextView mtdet;
	
	String response1,response2,response3; Button bt;
	ApplicationPreference appPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendreqsts);
		
		listreq=(ListView)findViewById(R.id.mlistreq);
		mtdet=(TextView)findViewById(R.id.mt_reqdet);
		bt=(Button)findViewById(R.id.mbt_acptreq);
		appPref = (ApplicationPreference) getApplication();
		
		new friendreq_pointlist().execute();
	}
	
	
	class friendreq_pointlist extends AsyncTask<String, String, String>
	  {
		
		  @Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
		
			  String urlParameters = null;
	            try {
	                urlParameters ="friend_num=" + URLEncoder.encode(appPref.getUserId(), "UTF-8") + "&&"
	                		+"reqid=" + URLEncoder.encode("1", "UTF-8") + "&&"
	                		+"acptid=" + URLEncoder.encode("0", "UTF-8");
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response1 = Connectivity.excutePost(Constants.RETREQUESTLIST,
	                    urlParameters);
	            Log.e("You are at", "" + response1);
	       return response1;
			 
		}
		  @Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(response1.contains("success"))
			{
				parsingmethod();
				bt.setEnabled(true);
			}
			else {
				bt.setEnabled(false);
				Toast.makeText(getApplicationContext(), "No pending requests available.", Toast.LENGTH_SHORT).show();
			}
		}
	  }
	  
	  
	  public void parsingmethod() {
			try
			{
				JSONObject jobject=new JSONObject(response1);
				JSONObject jobject1=jobject.getJSONObject("Event");
				JSONArray ja=jobject1.getJSONArray("Details");
				listreq.setAdapter(null);
				oslist_req.clear();
				
				int length=ja.length();
				for(int i=0;i<length;i++)
				{
		          
					JSONObject c = ja.getJSONObject(i);
		            // Storing  JSON item in a Variable
					mphone = c.getString("mphone");
		                 
		            // Adding value HashMap key => value
		            HashMap<String, String> map = new HashMap<String, String>();
		            map.put("mphone", mphone);
		       
		            map.put("show", "New friend Request from : "+mphone);
		            oslist_req.add(map);
		            
		            req_adapter = new SimpleAdapter(getApplicationContext(), oslist_req,
		                R.layout.layout_single,
		                new String[] {"show"}, new int[] {R.id.textView_single});
		            listreq.setAdapter(req_adapter);
		            listreq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		               @Override
		               public void onItemClick(AdapterView<?> parent, View view,
		                                            int position, long id) {  
		            	 sfn=oslist_req.get(+position).get("mphone");
		            	 
		              Toast.makeText(getApplicationContext(), ""+sfn, Toast.LENGTH_SHORT).show();
		             
		            	   new frienddet().execute();
		               }
		                });
		            
		            }
			}
		        catch (JSONException e) {
		          e.printStackTrace();
		        }
		       }
	  
	  
	  
	 public class frienddet extends AsyncTask<String, String, String>
	  {
		
		  @Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
		
			  String urlParameters = null;
	            try {
	                urlParameters ="phone=" + URLEncoder.encode(sfn, "UTF-8");
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response2 = Connectivity.excutePost(Constants.USERDATA_URL,
	                    urlParameters);
	            Log.e("You are at", "" + response2);
	       return response2;
			 
		}
		  @Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(response2.contains("success"))
			{
				mparsingmethod();
			}
			else {
				Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
			}
		}
	  }
	  
	  
	  public void mparsingmethod() {
			try
			{
				JSONObject jobject=new JSONObject(response2);
				JSONObject jobject1=jobject.getJSONObject("Event");
				JSONArray ja=jobject1.getJSONArray("Details");
				int length=ja.length();
				for(int i=0;i<length;i++)
				{
		          
					JSONObject c = ja.getJSONObject(i);
		            // Storing  JSON item in a Variable
					 nm= c.getString("name");
					 mail= c.getString("email");
					 adr= c.getString("address");
		               
					 mtdet.setText(""+nm+"\n"+mail+"\n"+adr);
		            }
			}
		        catch (JSONException e) {
		          e.printStackTrace();
		        }
		       }
	  
	  public class friendrequp extends AsyncTask<String, String, String>
	  {
		
		  @Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
		
			  String urlParameters = null;
	            try {
	                urlParameters ="mphone=" + URLEncoder.encode(sfn, "UTF-8")+"&&"
	                		+"friend_num=" + URLEncoder.encode(appPref.getUserId(), "UTF-8")+"&&"
	                		+"reqid=" + URLEncoder.encode("1", "UTF-8");
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response3 = Connectivity.excutePost(Constants.ACCEPTFRIEND_URL,
	                    urlParameters);
	            Log.e("You are at", "" + response3);
	       return response3;
			 
		}
		  @Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(response3.contains("success"))
			{
				Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
			}
		}
	  }
	  
	  
	  public void updatereq(View view)
	  {
		  new friendrequp().execute();
	  }

}
