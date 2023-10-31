package com.example.teacoffeecounterapp.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class EmployeeChaiTeaInfo {

    private String mDisplayName;
    private String value;
    private String dateTime;

    private long timestamp;

    Map<String, String> serverTimestamp;
    private String username;
    private String uniqueDeviceId;

    public String getmDisplayName() {
        return mDisplayName;
    }
    public void setmDisplayName(String mDisplayName) {
        this.mDisplayName = mDisplayName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(Map<String, String> serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUniqueDeviceId() {
        return uniqueDeviceId;
    }

    public void setUniqueDeviceId(String uniqueDeviceId) {
        this.uniqueDeviceId = uniqueDeviceId;
    }


    public Map<String,Object> toMap(){
        Map<String,Object> values = new HashMap<>();

        values.put("dateTime",dateTime);
        values.put("serverTimestamp",serverTimestamp);
        values.put("timestamp",timestamp);
        values.put("uniqueDeviceId",uniqueDeviceId);
        values.put("username",username);
        values.put("value",value);


        return values;

    }

    //    // [START post_to_map]
//    @Exclude
//    public Map<String, Object> toMap(){
//        Map<String, Object> map = new HashMap<>();
//        map.put("timestamp", ServerValue.TIMESTAMP);
//       map.put("value", value);
//       map.put("dateTime", dateTime);
////        map.put("body", body);
//        return map;
//    }


}
