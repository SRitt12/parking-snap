package com.sritt.parkingsnap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    private static HashMap<String, Vehicle> vehicles;
    DatabaseReference reference;

    public static HashMap getSingleton() {
        if (vehicles == null) {
            vehicles = new HashMap<>();
        }
        vehicles.remove(null);
        return vehicles;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.vehicleLv);


        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String license = intent.getStringExtra("licence");
        String make = intent.getStringExtra("make");
        String model = intent.getStringExtra("model");
        String color=intent.getStringExtra("color");
        String year =intent.getStringExtra("year");

        Vehicle newVehicle = new Vehicle(firstName,lastName,license,year,make,model,color);
        Log.d("vehicle info", "onCreate: "+newVehicle.toString());

        ListActivity.getSingleton().put(newVehicle.getLicence(),newVehicle);
        Log.d("set", "onCreate: "+ListActivity.getSingleton().keySet().toString());



        Iterator hmIterator = ListActivity.getSingleton().entrySet().iterator();

        while(hmIterator.hasNext()){
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            String vehicleDetail = mapElement.getValue().toString();
            arrayList.add(vehicleDetail);
        }
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adp);
        reference = FirebaseDatabase.getInstance().getReference().child("Vehicles");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vehicles.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    String retrievedData = snapshot1.getValue().toString();
                    String [] split = retrievedData.split(",");
                    for( int i = 0; i < split.length;i++){
                        split[i] = split[i].trim();
                    }
                    arrayList.add( (new Vehicle((split[0].split("="))[1],(split[1].split("="))[1],(split[2].split("="))[1],(split[4].split("="))[1],(split[6].split("="))[1].substring(0,(split[6].split("="))[1].length()-1),(split[5].split("="))[1],(split[3]).split("=")[1]).toString()));
                }
                adp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}