package com.example.teacoffeecounterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.teacoffeecounterapp.adapters.UserAdapter;
import com.example.teacoffeecounterapp.models.EmployeeChaiTeaInfo;
import com.example.teacoffeecounterapp.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalDataCountActivity extends AppCompatActivity {
RecyclerView recyclerView;
DatabaseReference databaseReference;
FirebaseDatabase firebaseDatabase;
    UserAdapter userAdapter;
    List<EmployeeChaiTeaInfo> teaInfoList;
    TextView tvTea,tvTeaWithoutSugar, tvBlackTea ,tvMilk,tvMilkwithoutSugar ,tvCoffee,tvCoffeeWithoutSugar, tvBlackCoffee,tvGreenTea,tvTotal;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_data_count);
        setTitle("Total Data");

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
        tvTotal=findViewById(R.id.tvTotalCount);
        tvTea=findViewById(R.id.tvTeaCount);
        tvTeaWithoutSugar=findViewById(R.id.tvTeaWithoutSugarCount);

        tvBlackTea=findViewById(R.id.tvBlackTeaCount);
        tvMilk=findViewById(R.id.tvMilkCount);
        tvMilkwithoutSugar=findViewById(R.id.tvMilkWithoutSugarCount);
        tvCoffee=findViewById(R.id.tvCoffeeCount);
        tvCoffeeWithoutSugar=findViewById(R.id.tvCoffeeWithoutSugarCount);

        tvBlackCoffee=findViewById(R.id.tvBlackCoffeeCount);
        tvGreenTea=findViewById(R.id.tvGreenTeaCount);
        recyclerView = findViewById(R.id.recyclerview);


//        users=new Users();
//        employeeChaiTeaInfo = new EmployeeChaiTeaInfo();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        teaInfoList = new ArrayList<>();

        userAdapter = new UserAdapter(this, teaInfoList);
        recyclerView.setAdapter(userAdapter);

        // databasestudent = FirebaseDatabase.getInstance().getReference("Student/3foBhguc0HK1mgjy03"); // Now this is your path
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        //databaseReference = firebaseDatabase.getReference("EmployeeChaiTeaInfo").child("Values");
        databaseReference = firebaseDatabase.getReference().child("EmployeeChaiTeaInfo").child("Values");
        getTotalCount();
        teaInfoList.clear();
        getItems();

    }

    private void getTotalCount() {

        databaseReference.orderByChild("value").equalTo("Tea ").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long teaWithMilkCount = snapshot.getChildrenCount();
                String output = String.valueOf(teaWithMilkCount);
                Log.d("OUTPUT", "Tea Count " + output);
                //tvTea.setText("Tea : " + output);
                tvTea.setText(output);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        databaseReference.orderByChild("value").equalTo("Tea without sugar ").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long teaWithoutSugarCount = snapshot.getChildrenCount();
                String output = String.valueOf(teaWithoutSugarCount);
                Log.d("OUTPUT", "Tea Count " + output);
               // tvTeaWithoutSugar.setText("Tea( No Sugar): " + output);
                tvTeaWithoutSugar.setText(output);

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
                Log.d("OUTPUT", "Coffee Count " + output);
             //   tvBlackTea.setText("Black Tea : " + output);
                tvBlackTea.setText(output);

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
                Log.d("OUTPUT", "Coffee Count " + output);
              //tvTeaWithoutMilkCount.setText("Total Tea (Without Sugar) Count: " + output);
                tvMilk.setText(output);

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
                Log.d("OUTPUT", "Coffee Count " + output);
              // tvMilkwithoutSugar.setText("Total Tea (Without Sugar) Count: " + output);
                tvMilkwithoutSugar.setText(output);

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
              //  tvCoffee.setText("Coffee : " + output);
                tvCoffee.setText(output);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.orderByChild("value").equalTo("Coffee without sugar ").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long teaWithoutSugarCount = snapshot.getChildrenCount();
                String output = String.valueOf(teaWithoutSugarCount);
                Log.d("OUTPUT", "Tea Count " + output);
                //tvCoffeeWithoutSugar.setText("Coffee( No Sugar) : " + output);
                tvCoffeeWithoutSugar.setText(output);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        databaseReference.orderByChild("value").equalTo("Black Coffee ").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long coffeeWithoutMilkCount = snapshot.getChildrenCount();
                String output = String.valueOf(coffeeWithoutMilkCount);
                Log.d("OUTPUT", "Coffee (Without Milk) " + output);
               // tvBlackCoffee.setText("Black Coffee : " + output);
                tvBlackCoffee.setText(output);

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
               // tvGreenTea.setText("Green Tea : " + output);
                tvGreenTea.setText(output);

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
                tvTotal.setText("Total Count: " + output);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

            private void getItems()
    {
        ProgressDialog mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setMessage("Loading ...");
        mProgressDialog.show();

        databaseReference. addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                teaInfoList.clear();

                for(DataSnapshot singleSnapshot : snapshot.getChildren())
                {

                    EmployeeChaiTeaInfo info = new EmployeeChaiTeaInfo();


                    String strUsername = singleSnapshot.child("username").getValue(String.class);
                    info.setUsername(strUsername);

                    String strValue = singleSnapshot.child("value").getValue(String.class);
                    info.setValue(strValue);//So you wont able to do this

                    teaInfoList.add(info);
                }


                userAdapter.notifyDataSetChanged();
                mProgressDialog.cancel();




            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        teaInfoList.clear();
        getTotalCount();
        getItems();

    }

    @Override
    protected void onResume() {
        super.onResume();
        teaInfoList.clear();
        getTotalCount();
        getItems();
    }
    @Override
    protected void onPause() {
        super.onPause();
        teaInfoList.clear();
        getTotalCount();
        getItems();
    }
    @Override
    protected void onStop() {
        super.onStop();
        teaInfoList.clear();
        getTotalCount();
        getItems();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        teaInfoList.clear();
        getTotalCount();
        getItems();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        teaInfoList.clear();
        getTotalCount();
        getItems();
    }


    //    @Override
//    protected void onStart() {
//        super.onStart();
//        databaseReference. addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                teaInfoList.clear();  // because everytime when data updates in your firebase database it creates the list with updated items
//                // so to avoid duplicate fields we clear the list everytime
//                if(snapshot.exists())
//                {
//                    for(DataSnapshot studentsnapshot : snapshot.getChildren())
//                    {
//
//                       //  Map<String, Object> mMapOne=studentsnapshot.getValue(EmployeeChaiTeaInfo.class);;
//
//
//                        EmployeeChaiTeaInfo employeeChaiTeaInfo = studentsnapshot.getValue(EmployeeChaiTeaInfo.class);
//
//                        teaInfoList.add(employeeChaiTeaInfo);
//                    }
//
//                  //  userAdapter.notifyDataSetChanged();
//                }
//
//
//
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

}
