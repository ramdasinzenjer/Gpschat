package com.inzenjer.gpschat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EmergencyNum extends Activity{
	
	EditText enumber; String snumber; Button bt_up;TextView tnum;
	
	ApplicationPreference applicationPreference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enumber);
		enumber=(EditText)findViewById(R.id.etrem_phone);
		bt_up=(Button)findViewById(R.id.bt_addenum);
		tnum=(TextView)findViewById(R.id.text_enum);
		applicationPreference=(ApplicationPreference)getApplication();
		
		if(applicationPreference.getEnum().equals(""))
		{
			tnum.setText("No Number available. Please Update.");
		}
		else
		{
			tnum.setText(applicationPreference.getEnum());
		}
		
		
		bt_up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				snumber=enumber.getText().toString();
				
				applicationPreference.setEnum(snumber);
				
				Toast.makeText(getApplicationContext(), "Emergency Number Updated", Toast.LENGTH_SHORT).show();				
			}
		});
		
	}

}
