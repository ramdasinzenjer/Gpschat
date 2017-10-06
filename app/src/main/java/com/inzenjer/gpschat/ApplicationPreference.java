package com.inzenjer.gpschat;

import com.inzenjer.gpschat.connnectors.Constants;
import android.app.Application;
import android.content.SharedPreferences;


 public class ApplicationPreference extends Application {
    private static SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    String Phonenumber,Password,ENumber; String mlattitude,mlongitude;
    Boolean LoginStatus,ServiceStatus,Shareloc; String styp;

    public Boolean getLoginStatus() {
        LoginStatus= appSharedPrefs.getBoolean(Constants.LOGINSTATUS, false);     
        return LoginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        prefsEditor.putBoolean(Constants.LOGINSTATUS,loginStatus);
        prefsEditor.commit();
    }
    public Boolean getServiceStatus() {
    	ServiceStatus= appSharedPrefs.getBoolean(Constants.SERVICESTATUS, false);     
        return ServiceStatus;
    }

    public void setServiceStatus(Boolean serviceStatus) {
        prefsEditor.putBoolean(Constants.SERVICESTATUS,serviceStatus);
        prefsEditor.commit();
    }
    
    public Boolean getShareServiceStatus() {
    	Shareloc= appSharedPrefs.getBoolean(Constants.SHARESERVICESTATUS, false);     
        return Shareloc;
    }

    public void setShareServiceStatus(Boolean shareserviceStatus) {
        prefsEditor.putBoolean(Constants.SHARESERVICESTATUS,shareserviceStatus);
        prefsEditor.commit();
    }
    
    public String getUserId() {
    	Phonenumber= appSharedPrefs.getString(Constants.PHONENUMBER, "");     
        return Phonenumber;
    }
    
    public void setUserId(String username) {
        prefsEditor.putString(Constants.PHONENUMBER,username);
        prefsEditor.commit();
    }
    
    public void setUserType(String usertype) {
        prefsEditor.putString(Constants.USERTYPE,usertype);
        prefsEditor.commit();
    }

    public String getUserType() {
    	styp= appSharedPrefs.getString(Constants.USERTYPE, "");     
        return styp;
    }

    public String getLongitude() {
    	mlongitude= appSharedPrefs.getString(Constants.MYLOCATIONLON, "");     
        return mlongitude;
    }

    public void setLongitude(String longitude) {
        prefsEditor.putString(Constants.MYLOCATIONLON,longitude);
        prefsEditor.commit();
    }
    
    public String getLattitude() {
    	mlattitude= appSharedPrefs.getString(Constants.MYLOCATIONLAT, "");     
        return mlattitude;
    }

    public void setLattitude(String lattitude) {
        prefsEditor.putString(Constants.MYLOCATIONLAT,lattitude);
        prefsEditor.commit();
    }
    
    public String getEnum() {
    	ENumber= appSharedPrefs.getString(Constants.EMERGENCYNUMBER, "");     
        return ENumber;
    }

    public void setEnum(String enumber) {
        prefsEditor.putString(Constants.EMERGENCYNUMBER,enumber);
        prefsEditor.commit();
    }
    
    @SuppressWarnings("static-access")
	@Override
    public void onCreate() {
        super.onCreate();
        this.appSharedPrefs = getApplicationContext().getSharedPreferences(
                Constants.PREFERENCE_PARENT, MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
        
    }

}
