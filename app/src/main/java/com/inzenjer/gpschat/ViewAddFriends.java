package com.inzenjer.gpschat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ViewAddFriends extends Activity{
	
	ListView aprlist,rglist;	ListAdapter apr_adapter,rg_adapter;
	ArrayList<HashMap<String, String>> oslist_apr = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> oslist_rg = new ArrayList<HashMap<String, String>>();
	String response1,response2,response3;
	
	String sname,sphone,smail,saddress,spass; String sname1,sphone1,smail1,saddress1,spass1;
	public static String pname;
	ApplicationPreference appPref;
	
	String valuefrndnum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewaddfriends);
		
		aprlist=(ListView)findViewById(R.id.listViewaprusr);
		rglist=(ListView)findViewById(R.id.listViewrusr);
		appPref=(ApplicationPreference)getApplication();
		
		new reg_userlist().execute();
		new frnds_list().execute();
		
	}
	
	
	class reg_userlist extends AsyncTask<String, String, String>
	  {
		  
		  @Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			  String urlParameters = null;
	            try {
	                urlParameters =  "phone=" + URLEncoder.encode(appPref.getUserId(), "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response1 = Connectivity.excutePost(Constants.REGLIST_URL,
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
				rg_parsingmethod();
			}
			else {
				Toast.makeText(getApplicationContext(), "No new registrations", Toast.LENGTH_SHORT).show();
			}
		}
	  }
	  
	  
	  public void rg_parsingmethod() {
			try
			{
				JSONObject jobject=new JSONObject(response1);
				JSONObject jobject1=jobject.getJSONObject("Event");
				JSONArray ja=jobject1.getJSONArray("Details");
				rglist.setAdapter(null);
				oslist_rg.clear();
				
				int length=ja.length();
				for(int i=0;i<length;i++)
				{
		          
					JSONObject c = ja.getJSONObject(i);
		            // Storing  JSON item in a Variable
		            sname = c.getString("name");
		            sphone=c.getString("phone");
		            smail=c.getString("email");
		            saddress=c.getString("address");
		            spass=c.getString("password");
		            
		            // Adding value HashMap key => value
		            HashMap<String, String> map = new HashMap<String, String>();
		            map.put("name", sname);
		            map.put("phone", sphone);
		            map.put("email", smail);
		            map.put("address", saddress);
		            
		            map.put("show", "Name : "+sname+"\n Email : "+smail);
		            oslist_rg.add(map);
		            
		            rg_adapter = new SimpleAdapter(getApplicationContext(), oslist_rg,
		                R.layout.layout_single,
		                new String[] {"show"}, new int[] {R.id.textView_single});
		            rglist.setAdapter(rg_adapter);
		            rglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		               @Override
		               public void onItemClick(AdapterView<?> parent, View view,
		                                            int position, long id) {  
		            	 String  value=oslist_rg.get(+position).get("name");
		            	 valuefrndnum=oslist_rg.get(+position).get("phone");
		              Toast.makeText(getApplicationContext(), ""+value+
		            		  " : Long press to send request. ", Toast.LENGTH_SHORT).show();
		             
		            	   
		               }
		                });
		            rglist.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							//Toast.makeText(getApplicationContext(), ""+valuefrndnum
							//		, Toast.LENGTH_SHORT).show();
							new send_request().execute(); 
							return false;
						}
					});
		            }
			}
		        catch (JSONException e) {
		          e.printStackTrace();
		        }
		  }
	  
	  class frnds_list extends AsyncTask<String, String, String>
	  {
		 
		  @Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			  String urlParameters = null;
	            try {
	                urlParameters =  "phone=" + URLEncoder.encode(appPref.getUserId(), "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            response2 = Connectivity.excutePost(Constants.FRIENDSLIST_URL,
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
				apr_parsingmethod();
			}
			else {
				Toast.makeText(getApplicationContext(), "You dont have any friends yet.", Toast.LENGTH_SHORT).show();
			}
		}
	  }
	  
	  
	  public void apr_parsingmethod() {
			try
			{
				JSONObject jobject=new JSONObject(response2);
				JSONObject jobject1=jobject.getJSONObject("Event");
				JSONArray ja=jobject1.getJSONArray("Details");
				aprlist.setAdapter(null);
				oslist_apr.clear();
				
				int length=ja.length();
				for(int i=0;i<length;i++)
				{
		          
					JSONObject c = ja.getJSONObject(i);
		            // Storing  JSON item in a Variable
		            sname1 = c.getString("name");

		            sphone1=c.getString("phone");
		            smail1=c.getString("email");
		            saddress1=c.getString("address");
		            
		            // Adding value HashMap key => value
		            HashMap<String, String> map = new HashMap<String, String>();
		            map.put("name", sname1);

		            map.put("phone", sphone1);
		            map.put("email", smail1);
		            map.put("address", saddress1);
		            
		            map.put("show", "Name : "+sname1+"\n Phone No: "+sphone1+"\n Email : "+smail1);
		            oslist_apr.add(map);
		            
		            apr_adapter = new SimpleAdapter(getApplicationContext(), oslist_apr,
		                R.layout.layout_single2,
		                new String[] {"show"}, new int[] {R.id.textView_single1});
		            aprlist.setAdapter(apr_adapter);
		            aprlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		               @Override
		               public void onItemClick(AdapterView<?> parent, View view,
		                                            int position, long id) {
						   Log.e("onclick", "ets");
		            	 String valuephn=oslist_apr.get(+position).get("phone");
		            	 
		            //  Toast.makeText(getApplicationContext(), ""+valuephn, Toast.LENGTH_SHORT).show();
		              
		              //SharedPreferences share=getSharedPreferences("mfrndid", MODE_WORLD_READABLE);
						//SharedPreferences.Editor de=share.edit();
						//de.putString("pnum", valuephn);
						//de.commit();
				       pname= valuephn;
		            Intent i=new Intent(getApplicationContext(),Chatroom.class);
		            startActivity(i);
		               
		               }
		                });
		            }
			}
		        catch (JSONException e) {
		          e.printStackTrace();
		        }
		       }

	  class send_request extends AsyncTask<String, String, String>
	  {
		  
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters = null;
            try {
                urlParameters =  "mphone=" + URLEncoder.encode(appPref.getUserId(), "UTF-8") + "&&"
                        + "friend_num=" + URLEncoder.encode(valuefrndnum, "UTF-8") + "&&"
                        + "reqid=" + URLEncoder.encode("1", "UTF-8") + "&&"
                        + "acptid=" + URLEncoder.encode("0","UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            response3 = Connectivity.excutePost(Constants.SENDREQUEST_URL,
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
				Toast.makeText(getApplicationContext(), "Friend Request sent.", Toast.LENGTH_SHORT).show();
				m_refresh();
			}
			else {
				Toast.makeText(getApplicationContext(), "Already requested.", Toast.LENGTH_SHORT).show();
			}
		}
	  }
	  
	  public void m_refresh()
	  {
		  finish();
		  startActivity(getIntent());
	  }
	  
}
