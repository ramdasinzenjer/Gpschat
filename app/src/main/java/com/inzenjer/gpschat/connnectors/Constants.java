package com.inzenjer.gpschat.connnectors;


public interface Constants {

    //Progress Message
    String LOGIN_MESSAGE="Logging in...";
    String REGISTER_MESSAGE="Register in...";

  //Urls
    String BASE_URL="http://192.168.43.28/gpchat/";   
   
    String REGISTER_URL=BASE_URL+"userreg.php?";
    String LOGIN_URL=BASE_URL+"login.php?";
    String REGLIST_URL=BASE_URL+"retrusers.php?"; 
    String FRIENDSLIST_URL=BASE_URL+"retruserfriends.php?";        
    String SENDREQUEST_URL=BASE_URL+"frndadd.php?";
    String REQUESTLOC_URL=BASE_URL+"mLocServicesfrnd.php?";
    String RETREQUESTLIST=BASE_URL+"frndrqst.php?";    
    String USERDATA_URL=BASE_URL+"retruserdata.php?";
    
    String ACCEPTFRIEND_URL=BASE_URL+"accept_frnd.php?";
    String MESSAGERECIEVE_URL = BASE_URL+"gchat_msgret.php?";
    String MESSAGESEND_URL = BASE_URL+"gchat_msgsent.php?";
    String FRIENDCHECK_URL=BASE_URL+"frndcheck.php?";
    String SAVELOCATION_URL = BASE_URL+"saveloc.php?";
    
    //Details
    String PASSWORD="Password";
    String PHONENUMBER="PhoneNumber";
    String LOGINSTATUS="LoginStatus";
    String SERVICESTATUS="ServiceStatus";
    String MYLOCATIONLAT="MyLocationLat";
    String MYLOCATIONLON="MyLocationLon";
    String USERTYPE="UserType";
    String SHARESERVICESTATUS="ShareService";
    String EMERGENCYNUMBER="EmergencyNumber";
    
    //SharedPreference
    String PREFERENCE_PARENT="Parent_Pref";
	
	
	
}
