package com.example.teacoffeecounterapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teacoffeecounterapp.models.EmployeeChaiTeaInfo;
import com.example.teacoffeecounterapp.models.Users;
import com.example.teacoffeecounterapp.receiver.FirebaseMessageReceiver;
import com.example.teacoffeecounterapp.receiver.ItemBroadcastReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_READ_PHONE_STATE =5 ;
    ProgressBar progressBar;
    RelativeLayout relativeValues;
RadioGroup rgValues;
RadioButton rbTeaWithMilk,rbCoffeeWithMilk,rbTeaWithoutMilk,rbCoffeeWithoutMilk,rbGreenTea,rbMilk,rbMilkWithoutSugar;
Button btnSubmit,btnFetchData,btnDelete,btnGetDeviceName;
    TextView tvTeaWithMilkCount,tvTeaWithoutMilkCount,tvCoffeeWithMilkCount,tvCoffeeWithoutMilkCount,tvGreenTeaCount, tvTotalCount;
    String strTeaCoffee;

    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference,databaseReferenceUser;
    EmployeeChaiTeaInfo employeeChaiTeaInfo;
   int  studentCounter=0;
  long  maxID;
    long firebaseTimeStamp;
    long timestamp;
    int teaCount;
    int coffeeCount;
    String firebaseValue;
    //sign up form
LinearLayout linearSignup;

    EditText etFirstName,etLastName,etContactNo;
    Button btnSave;
    Users users;
    String strFirstName;
    String strLastName;
    String strContact;
    String  username;
   // String  firstName;
    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;
    Intent intent;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

setTitle("Tea Coffee App");

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#22B5BC"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);


        initControls();
        try {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            makeRequest("e_QHEJoYT0iuvwd4Rks8Ix:APA91bEtz4oTqesXwD30B8hU3wXfjvXfkq44LqxWxfkdiEla2rKnbKpo5WwNn6D4dZm9pUhCxZsrzu3e4c03R8vtpNDhqrnPg-VISiIn2Z-KDQC3_oAwRL4wEJugaqxupjg25TAuAY6c");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

//        if(isNightMode(getApplicationContext()))
//        {
//
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//
//            etFirstName.setTextColor(Color.parseColor("#ffffff"));
//        }
//        else
//        {
//            etFirstName.setTextColor(Color.parseColor("#000000"));
//
//
//        }
//        int nightModeFlags =  getApplicationContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//
//        switch (nightModeFlags) {
//            case Configuration.UI_MODE_NIGHT_YES:
//               // Log.e(TAG, "onClick: NIGHT YES ");
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            etFirstName.setTextColor(Color.parseColor("#ffffff"));
//                break;
//
//            case Configuration.UI_MODE_NIGHT_NO:
//              //  Log.e(TAG, "onClick: LIGHT");
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                break;
//
//            case Configuration.UI_MODE_NIGHT_UNDEFINED:
//                //   doStuff();
//                break;
//        }


//        if (isNightMode(getApplicationContext()))
//        {
//            //String color= String.valueOf(Color.parseColor("#a8a8a8"));
//
//            etFirstName=findViewById(R.id.et_firstName);
//            etFirstName.setTextColor(Color.parseColor("#ffffff"));
//            //  etFirstName.setTextColor(Color.parseColor(color));
//            //    etFirstName.setTextColor(etFirstName.getContext().getColor(R.color.white));
//        }
        int permissionState = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);
        // If the permission is not granted, request it.
        if (permissionState == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
        else {
            //TODO
        }
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful())
                {
                    return;

                }
                //get new fcm registration token
                String token =task.getResult();
               System.out.println(token);
              //Toast.makeText(MainActivity.this,"your token is  " +token,Toast.LENGTH_SHORT).show();
               Log.d("token",token);

            }
        });
        final AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);



        //BELOW CODE ADDED FOR  PHONES()API -24 ANDROID VERSION 7.0)WHICH DONT SUPPORT "READ_PHONE_STATE" PERMISSION
    // FirebaseApp.initializeApp(getApplicationContext());
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        } else {
            //TODO
        }
//
//        int permissionState = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);
//        // If the permission is not granted, request it.
//        if (permissionState == PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
//        }
//        else {
//            //TODO
//        }
        users=new Users();
        employeeChaiTeaInfo = new EmployeeChaiTeaInfo();
        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        //databaseReference = firebaseDatabase.getReference("EmployeeChaiTeaInfo").child("Values");
  databaseReference = firebaseDatabase.getReference().child("EmployeeChaiTeaInfo").child("Values");
        databaseReferenceUser = firebaseDatabase.getReference().child("EmployeeChaiTeaInfo").child("Users");
       // initSignupFormListeners();




        checkdeviceUniqueId();
        checkButtonBasedOnTime();
       // checkdeviceUniqueId();
       // initSignupFormListeners();

        // initializing our object
        // class variable.

// databaseReference.getRef(). addValueEventListener(new ValueEventListener() {
//     @Override
//     public void onDataChange(@NonNull DataSnapshot snapshot) {
//         long count =snapshot.getChildrenCount();
//         String fullname = snapshot.child("username").getValue().toString();
//        Log.d("totalCount",fullname);
//
////                    String time=snapshot.child("timeStamp").getValue().toString();
//     }
//
//     @Override
//     public void onCancelled(@NonNull DatabaseError error) {
//
//     }
// });


//
//        databaseReference.child(String.valueOf(maxID + 1)).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String fullname = snapshot.child("username").getValue().toString();
//                Log.d("fullname",fullname);
//                tvTotalCount.setText(fullname);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });




        databaseReference.orderByChild("value").equalTo("Tea ").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long teaWithMilkCount = snapshot.getChildrenCount();
                String output = String.valueOf(teaWithMilkCount);
                Log.d("OUTPUT", "Tea Count " + output);
                tvTeaWithMilkCount.setText("Total Tea (With Milk) Count: " + output);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        databaseReference.orderByChild("value").equalTo("Tea without sugar ").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long teaWithoutMilkCount = snapshot.getChildrenCount();
                String output = String.valueOf(teaWithoutMilkCount);
                Log.d("OUTPUT", "Coffe Count " + output);
                tvTeaWithoutMilkCount.setText("Total Tea (Without Sugar) Count: " + output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.orderByChild("value").equalTo("Black Tea ").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long teaWithoutMilkCount = snapshot.getChildrenCount();
                String output = String.valueOf(teaWithoutMilkCount);
                Log.d("OUTPUT", "Coffe Count " + output);
               tvTeaWithoutMilkCount.setText("Total Tea (Without Sugar) Count: " + output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.orderByChild("value").equalTo("Milk ").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long milkCount = snapshot.getChildrenCount();
                String output = String.valueOf(milkCount);
                Log.d("OUTPUT", "Coffe Count " + output);
               // tvTeaWithoutMilkCount.setText("Total Tea (Without Sugar) Count: " + output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.orderByChild("value").equalTo("Milk without sugar ").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long milkWithoutSugarCount = snapshot.getChildrenCount();
                String output = String.valueOf(milkWithoutSugarCount);
                Log.d("OUTPUT", "Coffe Count " + output);
                // tvTeaWithoutMilkCount.setText("Total Tea (Without Sugar) Count: " + output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.orderByChild("value").equalTo("Coffee ").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long coffeeWithMilkCount = snapshot.getChildrenCount();
                String output = String.valueOf(coffeeWithMilkCount);
                Log.d("OUTPUT", "Coffee (With Milk) " + output);
                tvCoffeeWithMilkCount.setText("Total Coffee (With Milk) Count: " + output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.orderByChild("value").equalTo("Coffee  without sugar ").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long coffeeWithoutSugarCount = snapshot.getChildrenCount();
                String output = String.valueOf(coffeeWithoutSugarCount);
                Log.d("OUTPUT", "Coffee (Without Milk) " + output);
                tvCoffeeWithoutMilkCount.setText("Total Coffee (Without Sugar) Count: " + output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.orderByChild("value").equalTo("Black Coffee ").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long coffeeBlackCoffeeCount = snapshot.getChildrenCount();
                String output = String.valueOf(coffeeBlackCoffeeCount);
                Log.d("OUTPUT", "Coffee (Without Milk) " + output);
              //  tvCoffeeWithoutMilkCount.setText("Total Coffee (Without Sugar) Count: " + output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.orderByChild("value").equalTo("Green Tea ").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long greenTeaCount = snapshot.getChildrenCount();
                String output = String.valueOf(greenTeaCount);
                Log.d("OUTPUT", "Green Tea Count" + output);
                tvGreenTeaCount.setText("Total Green Tea Count: " + output);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                long totalCount = snapshot.getChildrenCount();
                String output = String.valueOf(totalCount);
                Log.d("OUTPUT", "Total Count" + output);
                tvTotalCount.setText("Total Count: " + output);





//                long count =snapshot.getChildrenCount();
//        String fullname = snapshot.child("username").getValue().toString();
////        Log.d("totalCount",fullname);
//


             //   timestamp = (Long) snapshot.getValue();
//                switch(snapshot.child("value").getValue(String.class)){ //This statement is seeing what "category" is.
//                    case "Tea":
//                        ++teaCount; //This is the "yes" int we made earlier; ++ increments 1.
//                        break;
//                    case "Coffee":
//                        ++coffeeCount; //This is the "no" int we made earlier; ++ increments 1.
//                        break;
////                    case "Unknown":
////                        ++unknown; //This is the "no" int we made earlier; ++ increments 1.
////                        break;
//
//                    tvTotalCount.setText();
//                }
                if(snapshot.exists())
                maxID=(snapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
//
//        btnFetchData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        int counter = (int) snapshot.getChildrenCount();
//
//                        //Convert counter to string
//                        String userCounter = String.valueOf(counter);
//
//                        //Showing the user counter in the textview
//                        tvCoffeeWithMilkCount.setText(userCounter);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//            }
//        });

        btnFetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),TotalDataCountActivity.class));

            }
        });



        readEmployeeChaiTeaInfo();
     //   delete();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
               //delete();
                //deleteData();
                //selectTime();
                //firebaseDatabase.getReference().child("EmployeeChaiTeaInfo").setValue(null);

               //   firebaseDatabase.getReference().child("EmployeeChaiTeaInfo").setValue(null);
                     //   snapshot.getRef().removeValue();


                deleteValue("2");


            }
        });
//below code to delete firebase data older than 2 hrs
       // long cutoff =new Date().getTime()- 2 * 60 * 60 * 1000;
//below code to delete firebase data older than 3 minutes(3/60=0.05 hrs)

   //     long cutoff = (long) (new Date().getTime()  - 0.05* 60 * 60 * 1000);
        //below code to delete firebase data older than 2 minutes(2/60=0.033 hrs)
//
//        long cutoff = (long) (new Date().getTime()  - 0.033* 60 * 60 * 1000);
//
//       // long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
//      //  Query oldItems = databaseReference.orderByChild("serverTimestamp").endAt(cutoff);
//       // oldItems.addListenerForSingleValueEvent(new ValueEventListener() {
//
//        databaseReference.orderByChild("serverTimestamp").endAt(cutoff).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
//                   // itemSnapshot.getRef().removeValue();
//                   databaseReference.setValue(null);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                throw databaseError.toException();
//            }
//        });

deleteDate();
      //  initListeners();
btnGetDeviceName.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(),getDeviceName(),Toast.LENGTH_LONG).show();

    }
});

    }


//    public void setAlarm(Users item){
//        intent = new Intent(getApplicationContext(), ItemBroadcastReceiver.class).putExtra("ID", item.getUuid());
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Integer.parseInt(item.getUniqueDeviceId()), intent, 0);
//
//        am.set(AlarmManager.RTC_WAKEUP, item.getDateTime().(1), pendingIntent);
//    }
//
//    public void  cancelAlarm(Users item) {
//        if(intent != null && pendingIntent != null){
//            am.cancel(pendingIntent);
//            /// if item needs to be saved, add your firestore set() routine
//        }
//    }


    public static String makeRequest(String id) throws JSONException {
        HttpURLConnection urlConnection;
        JSONObject json = new JSONObject();
        JSONObject info = new JSONObject();

        info.put("title", "Notification Title");   // Notification title
        info.put("body", "Notification body"); // Notification body
        info.put("sound", "chai_coffee_order_audio_loud.mp3"); // Notification sound
        json.put("notification", info);
        json.put("to","e_QHEJoYT0iuvwd4Rks8Ix:APA91bEtz4oTqesXwD30B8hU3wXfjvXfkq44LqxWxfkdiEla2rKnbKpo5WwNn6D4dZm9pUhCxZsrzu3e4c03R8vtpNDhqrnPg-VISiIn2Z-KDQC3_oAwRL4wEJugaqxupjg25TAuAY6c");
        Log.e("deviceidkey==> ",id+"");
        Log.e("jsonn==> ",json.toString());
        String data = json.toString();
        String result = null;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL("https://fcm.googleapis.com/fcm/send").openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "key=AAAAD1M9C2g:APA91bEdIhes5GHBZpGnUFh0WsNRS_aisclinN9WGc2f0dY7X2XDPoE1la7JRF4yJ397NdYVmPFgQmuyKc0rsn8vSlMrwLMf6Lfc2z86Hpznd0dveWPIa4T2rzaBV8HODtXt14q6egSA");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(data);
            writer.close();
            outputStream.close();

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean isNightMode(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

    private boolean CheckAllFields() {
        String regex = "^[+]?[0-9]{10,13}$";

        // boolean isdatatrue=false;
        // if (etFirstName.length() == 0) {
        //  if (etFirstName.length() == 0) {
        if (etFirstName.getText().toString().equals(""))
        {
            etFirstName.setError("This field is required");
            return false;
        }

        else     if (etLastName.getText().toString().equals(""))
        {
            etLastName.requestFocus();

            etLastName.setError("This field is required");
            return false;
        }

        else       if (etContactNo.length() == 0) {
            etContactNo.requestFocus();

            etContactNo.setError("Contact is required");
            return false;
        }

//        else

//            if (etContactNo.getText().toString().matches(regex)) {
//                Log.e("Validation", "Valid Mobile No");
//                etContactNo.requestFocus();
//
//                etContactNo.setError("Enter valid Mobile Number");
//            }
        // after all validation return true.
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
     //   deleteDate();
        checkButtonBasedOnTime();
        checkTeaCoffeeDataExists(getDeviceId(getApplicationContext()));

    }

    @Override
    protected void onResume() {
        super.onResume();
      //  progressBar.setVisibility(View.VISIBLE);

        deleteDate();
        getUsernameData(getDeviceId(getApplicationContext()));
        checkButtonBasedOnTime();

        checkTeaCoffeeDataExists(getDeviceId(getApplicationContext()));



    }


    @Override
    protected void onStop() {
        super.onStop();
        deleteDate();
       // checkButtonBasedOnTime();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteDate();
        //checkButtonBasedOnTime();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //deleteDate();

        getUsernameData(getDeviceId(getApplicationContext()));
        checkButtonBasedOnTime();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        deleteDate();
        getUsernameData(getDeviceId(getApplicationContext()));

        checkTeaCoffeeDataExists(getDeviceId(getApplicationContext()));
        checkButtonBasedOnTime();
    }


    private void deleteDate()
    {//below code to delete firebase data older than 2 hrs
        // long cutoff =new Date().getTime()- 2 * 60 * 60 * 1000;
//below code to delete firebase data older than 3 minutes(3/60=0.05 hrs)

        //     long cutoff = (long) (new Date().getTime()  - 0.05* 60 * 60 * 1000);
        //below code to delete firebase data older than 2 minutes(2/60=0.033 hrs)

        long cutoff = (long) (new Date().getTime()  - 1 * 60 * 60 * 1000);

        // long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
        //  Query oldItems = databaseReference.orderByChild("serverTimestamp").endAt(cutoff);
        // oldItems.addListenerForSingleValueEvent(new ValueEventListener() {

        databaseReference.orderByChild("serverTimestamp").endAt(cutoff).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    // itemSnapshot.getRef().removeValue();
                    databaseReference.setValue(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

//    private void delete() {
//
//        // Could store the push key or get it after push
//        firebaseDatabase.getReference().child("EmployeeChaiTeaInfo").setValue(null);
//
//
//    }

    //BELOW CODE ADDED FOR  PHONES()API -24 ANDROID VERSION 7.0)WHICH DONT SUPPORT "READ_PHONE_STATE" PERMISSION

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;

            default:
                break;
        }
    }


    //    @Override
//    protected void onStart() {
//        super.onStart();
//        //below code to delete firebase data older than 2 hrs
//        // long cutoff =new Date().getTime()- 2 * 60 * 60 * 1000;
////below code to delete firebase data older than 3 minutes(3/60=0.05 hrs)
//
//        //     long cutoff = (long) (new Date().getTime()  - 0.05* 60 * 60 * 1000);
//        //below code to delete firebase data older than 2 minutes(2/60=0.033 hrs)
//        long cutoff = (long) (new Date().getTime()  - 0.33* 60 * 60 * 1000);
//
//       // long cutoff = (long) (new Date().getTime()  - 15* 60 * 60 * 1000);
//
//        // long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
//        //  Query oldItems = databaseReference.orderByChild("serverTimestamp").endAt(cutoff);
//        // oldItems.addListenerForSingleValueEvent(new ValueEventListener() {
//
//        databaseReference.orderByChild("serverTimestamp").endAt(cutoff).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
//                    // itemSnapshot.getRef().removeValue();
//                    databaseReference.setValue(null);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                throw databaseError.toException();
//            }
//        });
//    }

    public void deleteValue(String dateTime)
    {

        databaseReference.child(dateTime).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
if(task.isSuccessful())
{
    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();

}
else
{
    Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();

}
            }
        });
    }
//private String convertFirebaseTimestampToHourMinute() {
//
//    Calendar cal = Calendar.getInstance();
//
//    cal.setTimeInMillis(Long.parseLong(String.valueOf(ServerValue.TIMESTAMP)));
//    SimpleDateFormat fmt = new SimpleDateFormat("HH:mm", Locale.US);
//    String time = fmt.format(cal.getTime()); //This returns a string formatted in the above way.
//return  time;
//    }

//    Calendar calendar = Calendar.getInstance();
//   // calendar.setTimeInMillis(Long.parseLong(firebaseTimeStamp));
//    SimpleDateFormat    simpleDateFormat = new SimpleDateFormat("HH:mm");
//
//    String   dateTime = simpleDateFormat.format(calendar.getTime()).toString();
//  //  String date = SimpleDateFormat.getDateTimeInstance().format("hh:mm", calendar);.toString()
////return  dateTime;

//}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.search:  // it is going to refer the search id name in main.xml

                //add your code here
checkTeaCoffeeDataExists(getDeviceId(getApplicationContext()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @SuppressLint("ResourceAsColor")
    private void checkButtonBasedOnTime() {
//        String valid_from_one = "10:48";
//        String valid_until_one = "11:00";

//        String valid_from_two= "14:45";
//        String valid_until_two= "15:00";

//        String valid_from_two = "12:00";
//        String valid_until_two = "13:55";
//
//        String valid_from_two = "10:48";
//        String valid_until_two = "11:00";
//
//        String valid_from_three = "16:45";
//        String valid_until_three = "17:00";
        Calendar calendar = Calendar.getInstance();
        // SimpleDateFormat    simpleDateFormat1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss aaa z");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();

        LocalTime target = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            target = LocalTime.parse( dateTime);
        }

        LocalTime valid_from_one = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            valid_from_one = LocalTime.parse( "10:35" );
        }
        LocalTime valid_until_one = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            valid_until_one = LocalTime.parse( "11:00" );
        }

        LocalTime valid_from_two = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            valid_from_two = LocalTime.parse( "14:35" );
        }
        LocalTime valid_until_two = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            valid_until_two = LocalTime.parse( "15:00" );
        }
//
//        LocalTime valid_from_two = LocalTime.parse( "12:39" );
//        LocalTime valid_until_two = LocalTime.parse( "13:00" );

        LocalTime valid_from_three= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            valid_from_three = LocalTime.parse( "16:35" );
        }
        LocalTime valid_until_three= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            valid_until_three = LocalTime.parse( "17:00" );
        }
        //  Boolean isTargetAfterStartAndBeforeStop = ( target.isAfter( valid_from_one ) && target.isBefore( valid_until_one ) ) ;
        Boolean isTargetBetweenFirstTimeZone=
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            isTargetBetweenFirstTimeZone = ( !(( target.isAfter( valid_from_one ) && (target.isBefore( valid_until_one ) ))) );
        }
        Boolean isTargetBetweenSecondTimeZone=
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            isTargetBetweenSecondTimeZone = ( !(( target.isAfter( valid_from_two ) && (target.isBefore( valid_until_two ) ))) );
        }

        Boolean isTargetBetweenThirdTimeZone=
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            isTargetBetweenThirdTimeZone = ( !(( target.isAfter( valid_from_three ) && (target.isBefore( valid_until_three ) ))) );
        }
        if(Boolean.TRUE.equals(isTargetBetweenFirstTimeZone)
                &&  isTargetBetweenSecondTimeZone
                && isTargetBetweenThirdTimeZone)
        {

            btnSubmit.setEnabled(false);
            btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
            btnSubmit.setTextColor(R.color.black);
        }
//
//      else   if(isTargetBetweenSecondTimeZone)
//{
//
//        btnSubmit.setEnabled(false);
//        btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//        btnSubmit.setTextColor(R.color.black);
//}
//        else  if(isTargetBetweenThirdTimeZone)
//        {
//
//            btnSubmit.setEnabled(false);
//            btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//            btnSubmit.setTextColor(R.color.black);
//        }



//        if ((dateTime.compareTo(valid_from_one) <0 && dateTime.compareTo(valid_until_one) <0)
//                || (dateTime.compareTo(valid_from_one) >= 0 && dateTime.compareTo(valid_until_one) <=0)
//                   || (dateTime.compareTo(valid_from_one) >= 0 && dateTime.compareTo(valid_until_one)>=0) )
//
//        {
//            if (dateTime.compareTo(valid_until_two)<0 && valid_from_two.compareTo(dateTime)<0)
//
//            {
//
//        btnSubmit.setEnabled(false);
//        btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//        btnSubmit.setTextColor(R.color.black);
//    }
//else if()

        //        String getMyTime="05/19/2016 09:45 PM ";
//        Log.d("getCurrentDateTime",getCurrentDateTime);
//// getCurrentDateTime: 05/23/2016 18:49 PM
//
//        if (getCurrentDateTime.compareTo(getMyTime) < 0)
//        {
//
//        }
//        else
//        {
//            Log.d("Return","getMyTime older than getCurrentDateTime ");
//        }
    //    Toast.makeText(getApplicationContext(),dateTime,Toast.LENGTH_SHORT).show();
//        if ((dateTime.compareTo(valid_until_one)>0 && valid_from_one.compareTo(dateTime)<0)||
//                (dateTime.compareTo(valid_until_two)>0 && valid_from_two.compareTo(dateTime)<0)||
//                (dateTime.compareTo(valid_until_three)>0 && valid_from_three.compareTo(dateTime)<0 )  )
//

//
//            if ((dateTime.compareTo(valid_until_one)>0 && dateTime.compareTo(valid_from_one)>0)
//
//            ||(dateTime.compareTo(valid_until_one)<0 && dateTime.compareTo(valid_from_one)<0))
////                    ||(dateTime.compareTo(valid_until_one)<0 && dateTime.compareTo(valid_from_one)>0))
//
//
//        {//datetime-valid_until >0  && valid_from-datetime<0
//            //Toast.makeText(getApplicationContext(),dateTime,Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(),dateTime+" >" + valid_until_one +" & "+ "\n"+
//                    valid_from_one+" < " + dateTime+ valid_from_one,Toast.LENGTH_SHORT).show();
//
//            btnSubmit.setEnabled(false);
//            btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//            btnSubmit.setTextColor(R.color.black);
//
//        }
//        if ((dateTime.compareTo(valid_until_two)>0 && dateTime.compareTo(valid_from_two)>0)
//
//                ||(dateTime.compareTo(valid_until_two)<0 && dateTime.compareTo(valid_from_two)<0))
////                    ||(dateTime.compareTo(valid_until_one)<0 && dateTime.compareTo(valid_from_one)>0))
//
//
//        {//datetime-valid_until >0  && valid_from-datetime<0
//            //Toast.makeText(getApplicationContext(),dateTime,Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(),dateTime+" >" + valid_until_one +" & "+ "\n"+
//                    valid_from_one+" < " + dateTime+ valid_from_one,Toast.LENGTH_SHORT).show();
//
//            btnSubmit.setEnabled(false);
//            btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//            btnSubmit.setTextColor(R.color.black);
//
//        }



//        if ((dateTime.compareTo(valid_until_one)<0 && valid_from_one.compareTo(dateTime)>0)
//                ||(dateTime.compareTo(valid_until_one)<0 && valid_from_one.compareTo(dateTime)<0)
//                ||(dateTime.compareTo(valid_from_one)>0) &&(valid_from_one.compareTo(dateTime)<0)
//                ||(dateTime.compareTo(valid_from_one)>0) &&(valid_from_one.compareTo(dateTime)<0)  )
//
//        {//datetime-valid_until >0  && valid_from-datetime<0
//            //Toast.makeText(getApplicationContext(),dateTime,Toast.LENGTH_SHORT).show();
////            Toast.makeText(getApplicationContext(),dateTime+" >" + valid_until_one +" & "+ "\n"+
////                    valid_from_one+" < " + dateTime+ valid_from_one,Toast.LENGTH_SHORT).show();
//
//            btnSubmit.setEnabled(false);
//            btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//            btnSubmit.setTextColor(R.color.black);
//
//        }



//        if ((dateTime.compareTo(valid_until_one)<0 && valid_from_one.compareTo(dateTime)>0)
//                ||(dateTime.compareTo(valid_until_one)<0 && valid_from_one.compareTo(dateTime)<0)
//                    ||(dateTime.compareTo(valid_from_one)>0) &&(valid_from_one.compareTo(dateTime)<0)
//                    ||(dateTime.compareTo(valid_from_one)>0) &&(valid_from_one.compareTo(dateTime)<0)  )
//
//        {//datetime-valid_until >0  && valid_from-datetime<0
//            //Toast.makeText(getApplicationContext(),dateTime,Toast.LENGTH_SHORT).show();
////            Toast.makeText(getApplicationContext(),dateTime+" >" + valid_until_one +" & "+ "\n"+
////                    valid_from_one+" < " + dateTime+ valid_from_one,Toast.LENGTH_SHORT).show();
//
//            btnSubmit.setEnabled(false);
//            btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//            btnSubmit.setTextColor(R.color.black);
//
//        }
//     else   if ((dateTime.compareTo(valid_until_two)<0 && valid_from_two.compareTo(dateTime)>0)
//                ||(dateTime.compareTo(valid_until_two)<0 && valid_from_two.compareTo(dateTime)<0)
//                ||(dateTime.compareTo(valid_from_two)>0) &&(valid_from_two.compareTo(dateTime)<0)
//                ||(dateTime.compareTo(valid_from_two)>0) &&(valid_from_two.compareTo(dateTime)<0)  )
//
//        {//datetime-valid_until >0  && valid_from-datetime<0
//            //Toast.makeText(getApplicationContext(),dateTime,Toast.LENGTH_SHORT).show();
////            Toast.makeText(getApplicationContext(),dateTime+" >" + valid_until_one +" & "+ "\n"+
////                    valid_from_one+" < " + dateTime+ valid_from_one,Toast.LENGTH_SHORT).show();
//
//            btnSubmit.setEnabled(false);
//            btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//            btnSubmit.setTextColor(R.color.black);
//
//
//    }
//     else   if ((dateTime.compareTo(valid_until_three)<0 && valid_from_three.compareTo(dateTime)>0)
//                ||(dateTime.compareTo(valid_until_three)<0 && valid_from_three.compareTo(dateTime)<0)
//                ||(dateTime.compareTo(valid_from_three)>0) &&(valid_from_three.compareTo(dateTime)<0)
//            ||(dateTime.compareTo(valid_from_three)>0) &&(valid_from_three.compareTo(dateTime)<0)  )
//
//    {//datetime-valid_until >0  && valid_from-datetime<0
//        //Toast.makeText(getApplicationContext(),dateTime,Toast.LENGTH_SHORT).show();
////        Toast.makeText(getApplicationContext(),dateTime+" >" + valid_until_one +" & "+ "\n"+
////                valid_from_one+" < " + dateTime+ valid_from_one,Toast.LENGTH_SHORT).show();
//
//        btnSubmit.setEnabled(false);
//        btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//        btnSubmit.setTextColor(R.color.black);
//
//    }

//
//        else    if (dateTime.compareTo(valid_until_two)>0 && valid_from_two.compareTo(dateTime)<0)
//        {//datetime-valid_until >0  && valid_from-datetime<0
//            //Toast.makeText(getApplicationContext(),dateTime,Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(),dateTime+" >" + valid_until_two +" & "+ "\n"+
//                    valid_from_two+" < " + dateTime+ valid_from_two,Toast.LENGTH_SHORT).show();
//
//            btnSubmit.setEnabled(false);
//            btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//            btnSubmit.setTextColor(R.color.black);
//
//        }

//    else       if (dateTime.compareTo(valid_until_three)>0 && valid_from_three.compareTo(dateTime)<0)
//        {//datetime-valid_until >0  && valid_from-datetime<0
//            //Toast.makeText(getApplicationContext(),dateTime,Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(),dateTime+" >" + valid_until_three +" & "+ "\n"+
//                    valid_from_three+" < " + dateTime+ valid_from_three ,Toast.LENGTH_SHORT).show();
//
//            btnSubmit.setEnabled(false);
//            btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//            btnSubmit.setTextColor(R.color.black);
//
//        }
//
//        else {
//            Log.d("Return","dateTime greater/older than valid_until ");
//          //  Toast.makeText(getApplicationContext(),dateTime+" > " + valid_until,Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(),dateTime+" < " + "valid_until"+" & "+ "\n"+
//                    dateTime+" > " +"valid_from"    ,Toast.LENGTH_SHORT).show();
//
//        }
//        else
//        {
//            btnSubmit.setEnabled(true);
//            btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//            btnSubmit.setTextColor(R.color.black);
//
//        }
//        Calendar calendar = Calendar.getInstance();
//                    SimpleDateFormat sfd=new SimpleDateFormat("HH:mm",Locale.getDefault());
//
//
//                   String date= sfd.format(calendar);
////
//                           if(dateTime.equals("17:48"))
//                           {
//                             //  btnSubmit.setEnabled(false);
//                               btnSubmit.setEnabled(false);
//                               btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
//                               btnSubmit.setTextColor(R.color.black);
//
//                           }

    }
        public static String getTimeDate(long timestamp){
        try{
           // DateFormat dateFormat = DateFormat.getDateTimeInstance();
            Date netDate = (new Date(timestamp));
           // SimpleDateFormat sfd=new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z",Locale.getDefault());
            SimpleDateFormat sfd=new SimpleDateFormat("HH:mm",Locale.getDefault());

            return sfd.format(netDate);
        } catch(Exception e) {
            return "date";
        }

    }
    public static String getDate(long timestamp){
        try{
            // DateFormat dateFormat = DateFormat.getDateTimeInstance();
            Date netDate = (new Date(timestamp));
            // SimpleDateFormat sfd=new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z",Locale.getDefault());
            SimpleDateFormat sfd=new SimpleDateFormat("yyyy.MM.dd",Locale.getDefault());

            return sfd.format(netDate);
        } catch(Exception e) {
            return "date";
        }

    }
    /** Returns the consumer friendly device name */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }
    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            deviceUniqueIdentifier = tm.getDeviceId();
        }
        if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceUniqueIdentifier;
    }
   // Android 10 Restricted developer to Access IMEI number.
   // You can have a alternate solution by get Software ID. You can use software id as a unique id. Please find below code as i use in Application.
    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }
    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }
    public void readEmployeeChaiTeaInfo(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try{
                 timestamp = (Long) snapshot.getValue();

//                    String time=snapshot.child("timeStamp").getValue().toString();
//                    Long t=Long.parseLong(time);
//                    Date myDate = new Date(t*1000);
//
//                    Log.d("myDate", "myDate:  " + myDate);



                    System.out.println(timestamp);

                Log.d("TAG", "time11:  " + timestamp);
                long test = Long.valueOf(timestamp);
                 firebaseValue = getTimeDate(test);

                 String   firebaseValueDate= getDate(test);

//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTimeInMillis(timestamp);
//                    SimpleDateFormat sfd=new SimpleDateFormat("HH:mm",Locale.getDefault());
//
//                   String date= sfd.format(calendar);
                Toast.makeText(getApplicationContext(),firebaseValue.toString()  +firebaseValueDate,Toast.LENGTH_LONG).show();
                    Log.d("TAG", "firebaseValueDate:  " + firebaseValueDate);


//                    try {
//                        if (firebaseValue=="12:50"&& firebaseValueDate=="2023-10-10") {
                           // firebaseDatabase.getReference().child("EmployeeChaiTeaInfo").setValue(null);
                            //snapshot.getRef().removeValue();

                      if(  databaseReference.orderByChild("dateTime").equals ("12:10:2023 12:22"))
                      {

                       //   databaseReference.child("dateTime").child("12:15").removeValue();
                          firebaseDatabase.getReference().child("EmployeeChaiTeaInfo").child("Values").setValue(null);
Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
                      }
                        // firebaseDatabase.getReference().child("EmployeeChaiTeaInfo").setValue(null);
//                           databaseReference.child("dateTime").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                               @Override
//                               public void onComplete(@NonNull Task<Void> task) {
//if(task.isSuccessful())
//{
//    Toast.makeText(getApplicationContext(),"Data has been successfully deleted!",Toast.LENGTH_LONG).show();
//
//}
//else
//{
//    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
//
//}
//                               }
//                           });








            }
            catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//
//      databaseReference.setValue(ServerValue.TIMESTAMP);
    }


    private void selectTime()
    {
        Calendar    calendar = Calendar.getInstance();
        SimpleDateFormat    simpleDateFormat1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss aaa z");

        SimpleDateFormat    simpleDateFormat = new SimpleDateFormat("HH:mm");

        String   dateTime = simpleDateFormat.format(calendar.getTime()).toString();
       Toast.makeText(getApplicationContext(),dateTime,Toast.LENGTH_SHORT).show();
    }

    private void setAlarm()

{
int hour =16;
int minute=20;
    Intent intent=new Intent(AlarmClock.ACTION_SET_ALARM);
    intent.putExtra(AlarmClock.EXTRA_HOUR,hour);
    intent.putExtra(AlarmClock.EXTRA_MINUTES,minute);

    if(hour <= 24 && minute <= 60)
    {
        startActivity(intent);
    }
}
//    private void getTimeSlot()
//    {
//
//        Calendar currnetDateTime = Calendar.getInstance();
//        //SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss a");
//
//        //hh---means 01 ,02 etc
//        SimpleDateFormat df = new SimpleDateFormat("hh:mm");
//
//        String  currentTime = df.format(currnetDateTime.getTime());
//        if(currnetDateTime.get(Calendar.AM_PM) == Calendar.PM){
//            {
//                if (currentTime.equals("04.10")  || currentTime .equals("16.10"))
//                {
//                    firebaseDatabase.getReference().child("EmployeeChaiTeaInfo").setValue(null);
//
//
//                }
//            }
//    }}

    private void initControls()
    {
        //progressBar=findViewById(R.id.progress_bar);
        //relative
        relativeValues=findViewById(R.id.relative_values);
        //RADIOGROUP
        rgValues=findViewById(R.id.rg_values);
        //RADIOBUTTONS
        rbTeaWithMilk=findViewById(R.id.rb_teaWithMilk);
        rbTeaWithoutMilk=findViewById(R.id.rb_teaWithoutMilk);

        rbMilk=findViewById(R.id.rb_milk);
        rbMilkWithoutSugar=findViewById(R.id.rb_milkWithoutSugar);


        rbCoffeeWithMilk=findViewById(R.id.rb_coffeeWithMilk);
        rbCoffeeWithoutMilk=findViewById(R.id.rb_coffeeWithoutMilk);
        rbGreenTea=findViewById(R.id.rb_greenTea);
//BUTTONS
        btnSubmit=findViewById(R.id.btn_submit);
        btnFetchData=findViewById(R.id.btn_fetchData);
        btnDelete=findViewById(R.id.btn_delete);
        btnGetDeviceName=findViewById(R.id.btn_getDeviceName);
//TEXTVIEWS
         tvTeaWithMilkCount=findViewById(R.id.tv_totalTeaWithMilkCount);
        tvTeaWithoutMilkCount=findViewById(R.id.tv_totalTeaWithoutMilkCount);

        tvCoffeeWithMilkCount=findViewById(R.id.tv_totalCoffeeWithMilkCount);
        tvCoffeeWithoutMilkCount=findViewById(R.id.tv_totalCoffeeWithoutMilkCount);

        tvGreenTeaCount=findViewById(R.id.tv_GreenTeaCount);
        tvTotalCount=findViewById(R.id.tv_TotalCount);

//sign up form
        linearSignup=findViewById(R.id.linear_signup);

        etFirstName=findViewById(R.id.et_firstName);
        etLastName=findViewById(R.id.et_lastName);
        etContactNo=findViewById(R.id.et_contactNo);

        btnSave=findViewById(R.id.btn_save);

    }
private void initSignupFormListeners() {




    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(CheckAllFields()) {

            strFirstName = etFirstName.getText().toString();
            strLastName = etLastName.getText().toString();
            strContact = etContactNo.getText().toString();
            users.setFirstName(strFirstName);
            users.setLastName(strLastName);
            users.setContactNo(strContact);


            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");

            String strDateTime = simpleDateFormat.format(calendar.getTime()).toString();

            users.setDateTime(strDateTime);
            users.setDeviceName(getDeviceName());
            // users.setImei(getDeviceIMEI());

            users.setUniqueDeviceId(getDeviceId(getApplicationContext()));


         //   databaseReferenceUser.child(String.valueOf(maxID + 1)). setValue(users);
                databaseReferenceUser.push(). setValue(users);

                Toast.makeText(getApplicationContext(), "User data has been saved successfully!!", Toast.LENGTH_SHORT).show();

            // addDatatoFirebase(strTeaCoffee);
            etFirstName.setText("");
            etLastName.setText("");
            etContactNo.setText("");

        }}
    });


}

    public   void checkTeaCoffeeDataExists(String deviceId)
    {

checkButtonBasedOnTime();
        databaseReference.orderByChild("uniqueDeviceId").equalTo(deviceId).addValueEventListener(new ValueEventListener() {
            private String name;

            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    btnSubmit.setEnabled(false);
                    btnSubmit.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    btnSubmit.setTextColor(R.color.black);

//Toast.makeText(getApplicationContext(),"Error:  Order is already submitted!!",Toast.LENGTH_SHORT).show();

                }

//                else
//                {
//                    btnSubmit.setEnabled(true);
//                    btnSubmit.setBackgroundColor(Color.parseColor("#22B5BC"));
//               //     btnSubmit.setTextColor(R.color.white);
//                    btnSubmit.setTextColor(Color.parseColor("#FFFFFF"));
//
//              }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       // return  username;
    }
    public String getID()
    {
        databaseReference.orderByChild("uniqueDeviceId").equalTo(getDeviceId(getApplicationContext())).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    String uniqueDeviceId = dataSnapshot1.child("uniqueDeviceId").getValue(String.class);
                        username = dataSnapshot1.child("firstName").getValue(String.class);
                    employeeChaiTeaInfo.setUsername(username);
                    //                    Query ref = databaseReferenceUser.orderByChild("uniqueDeviceId").equalTo(uniqueDeviceId);
//                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot userSnapshot) {
//                            username = userSnapshot.child("firstName").getValue(String.class);
//                            employeeChaiTeaInfo.setUsername(username);
//                        }
//
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
return  username;
    }
            private String name="";

    public   String getUsernameData11(String deviceId) {
        databaseReferenceUser.orderByChild("uniqueDeviceId").equalTo(deviceId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            for(DataSnapshot dataSnapshot1: snapshot.getChildren()) {
                               String theraid = dataSnapshot1.child("firstName").getValue(String.class);
                                Query userRef =databaseReference.orderByChild("username").equalTo(theraid);
                               userRef.addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                       Users thera= dataSnapshot1.getValue(Users.class);
                                       username=thera.getFirstName();
                                    //  username= snapshot.child("username").getValue(String.class);
                                       employeeChaiTeaInfo.setUsername(username);


                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError error) {

                                   }
                               });


                        }
                    }}

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return username;
    }

    public   String getUsernameData(String deviceId)
{


    databaseReferenceUser.orderByChild("uniqueDeviceId").equalTo(deviceId).addValueEventListener(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists())
            {

          // btnSubmit.setEnabled(false);

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
                {
                    // Users yourModel = childSnapshot.getValue(Users.class);
                  //  Users yourModel = childSnapshot.getValue(Users.class);
                    users = childSnapshot.getValue(Users.class);

                  // username= childSnapshot.child("firstName").getValue(String.class);

                    username= users.getFirstName()+ " " + users.getLastName();

                    //if(yourModel.getUniqueDeviceId().equalsIgnoreCase(getDeviceId(getApplicationContext()))) {
                    // Here is your desired location
                    Log.d("Key1111", username);
              //  username = users.getFirstName();
            employeeChaiTeaInfo.setUsername(username);
                    // databaseReference.setValue(username);
//                    if(name!=null){
//                       // if(role.equals("student")){
//                            employeeChaiTeaInfo.setUsername(name);
//                       // }
//                    }


                    //Log.d("name", username);
                    // employeeChaiTeaInfo.setUsername(username);

                    //      }

                    // Here is your desired location

                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

return  username;
}






    public void checkdeviceUniqueId() {

         ProgressDialog mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setMessage("Loading ...");
        mProgressDialog.show();
        databaseReferenceUser.orderByChild("uniqueDeviceId").equalTo(getDeviceId(getApplicationContext())).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
if(snapshot.exists())
{


    linearSignup.setVisibility(View.GONE);

    relativeValues.setVisibility(View.VISIBLE);



    //setAlarm();
    btnSubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int selected= rgValues.getCheckedRadioButtonId();
            RadioButton teaCoffeeBtn=(RadioButton) findViewById(selected);

            strTeaCoffee = teaCoffeeBtn.getText().toString();
//databaseReference.setValue(employeeChaiTeaInfo);
            //save user current data while adding data
            Calendar  calendar = Calendar.getInstance();

         // SimpleDateFormat    simpleDateFormat = new SimpleDateFormat("HH:mm");


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");

            String   strDateTime = simpleDateFormat.format(calendar.getTime()).toString();



            employeeChaiTeaInfo.setValue(strTeaCoffee);
            employeeChaiTeaInfo.setDateTime(strDateTime);

            // employeeChaiTeaInfo.setTimestamp(ServerValue.TIMESTAMP);

            // databaseReference.setValue(ServerValue.TIMESTAMP);
            employeeChaiTeaInfo.setServerTimestamp(ServerValue.TIMESTAMP);

            employeeChaiTeaInfo.setUniqueDeviceId(getDeviceId(getApplicationContext()));

            //employeeChaiTeaInfo.setUsername(getDeviceId(getApplicationContext()));

            //employeeChaiTeaInfo.setUsername(strFirstName);
// getUsernameData(getDeviceId(getApplicationContext()));
//            employeeChaiTeaInfo.setUsername(username);
          //  getUsernameData(getDeviceId(getApplicationContext()));


     employeeChaiTeaInfo.setUsername(getUsernameData11(getDeviceId(getApplicationContext())));
         //   employeeChaiTeaInfo.setUsername( getID());





         //   databaseReference.child(String.valueOf(maxID+1)).setValue(employeeChaiTeaInfo);
            databaseReference.push().setValue(employeeChaiTeaInfo);

            Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_SHORT).show();
            // addDatatoFirebase(strTeaCoffee);
        }
    });
    mProgressDialog.cancel();



}

else
{
    linearSignup.setVisibility(View.VISIBLE);

    relativeValues.setVisibility(View.GONE);
    initSignupFormListeners();
    mProgressDialog.cancel();

}
                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
            //  boolean  isAllFieldsChecked1 = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
               // if (CheckAllFields()) {


               // }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mProgressDialog.dismiss();
            }
        });
    }






    private void addDatatoFirebase(String value) {
        // below 3 lines of code is used to set
        // data in our object class.
//        employeeInfo.setEmployeeName(name);
//        employeeInfo.setEmployeeContactNumber(phone);
//        employeeInfo.setEmployeeAddress(address);
        // getting text from our edittext fields.
      //  String strValue = strTeaCoffee;
        employeeChaiTeaInfo.setValue(value);
       // String phone = employeePhoneEdt.getText().toString();
       // String address = employeeAddressEdt.getText().toString();
        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
//if(snapshot.exists())
//{
//     maxID=snapshot.getChildrenCount();
//    databaseReference.child(String.valueOf(maxID+1)).setValue(value);
//   // databaseReference.setValue(employeeChaiTeaInfo);
//}
//else {
//    databaseReference.setValue(employeeChaiTeaInfo);
//
//}

                databaseReference.setValue(employeeChaiTeaInfo);

//
//                int counter = (int) snapshot.getChildrenCount();
//
//                //Convert counter to string
//                String userCounter = String.valueOf(counter);
//
//                //Showing the user counter in the textview
//                tvTotalCount.setText("Total Count:" +"\n" +userCounter);


                // after adding this data we are showing toast message.
                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();

            }



        });
    }
}