package com.inzenjer.gpschat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class MainHome extends Activity{
	
	 CheckBox chblocservice,chbshareloc; Button bvaf,blf,bcf;
	
	ApplicationPreference appPref; Boolean b_locservice,b_shareloc;
	ImageView img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainhome); 
		
		bvaf=(Button)findViewById(R.id.mbt_frnds);
		blf=(Button)findViewById(R.id.mbt_locate);
		bcf=(Button)findViewById(R.id.mbt_chatView);
		img=(ImageView)findViewById(R.id.img_emergency);
		
		chblocservice=(CheckBox)findViewById(R.id.checkBoxlcserv);
		chbshareloc=(CheckBox)findViewById(R.id.CheckBoxlcshare);
		
		appPref = (ApplicationPreference) getApplication();
		b_locservice=appPref.getServiceStatus();
		b_shareloc=appPref.getShareServiceStatus();
		
		if(b_locservice==true)
		{
			chblocservice.setChecked(true);
		}
		
		if(b_shareloc==true)
		{
			chbshareloc.setChecked(true);
		}
		
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
     				SmsManager smsManager = SmsManager.getDefault();
    			//	smsManager.sendTextMessage(strMessagen, null, "hi", null, null);
     				smsManager.sendTextMessage(appPref.getEnum(), null, " Emergency at : " +SrtService.stplace , null, null);
     				Toast.makeText(getBaseContext(), "SMS Sent!",
     							Toast.LENGTH_SHORT).show();
     				
     				
     			  } catch (Exception e) {
     				Toast.makeText(getBaseContext(),
     					"Failed!",
     					Toast.LENGTH_LONG).show();
     				e.printStackTrace();
     			  }
				
			}
		});
		
		img.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),EmergencyNum.class);
				startActivity(i);
				return false;
			}
		});
		
		bvaf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),ViewAddFriends.class);
				startActivity(i);
			}
		});
		blf.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i=new Intent(getApplicationContext(),FindFriends.class);
						startActivity(i);	
					}
				});
		bcf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Friend_req.class);
				startActivity(i);
			}
		});
		
		chblocservice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StringBuffer result=new StringBuffer();
				result.append("Location Service: ").append(chblocservice.isChecked());
							
				if(chblocservice.isChecked()==true)
				{
					appPref.setServiceStatus(true);
					startService(new Intent(getApplicationContext(), com.inzenjer.gpschat.SrtService.class));
					
				}
				else
				{
					appPref.setServiceStatus(false);
					stopService(new Intent(getApplicationContext(), com.inzenjer.gpschat.SrtService.class));
				}
					Toast.makeText(getApplicationContext(), result.toString(),Toast.LENGTH_LONG).show();
				
			}
		});
		
		chbshareloc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StringBuffer result=new StringBuffer();
				result.append("Share Loc Service: ").append(chbshareloc.isChecked());
							
				if(chbshareloc.isChecked()==true)
				{
					appPref.setShareServiceStatus(true);
				}
				else
				{
					appPref.setShareServiceStatus(false);
				}
					Toast.makeText(getApplicationContext(), result.toString(),Toast.LENGTH_LONG).show();
				
			}
		});
		
		
	}

}
