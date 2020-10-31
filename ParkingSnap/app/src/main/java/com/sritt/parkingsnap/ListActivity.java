package com.sritt.parkingsnap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {
    ListView listView;
    private static HashMap<String, Vehicle> vehicles;


    public static HashMap getSingleton() {
        if (vehicles == null) {
            vehicles = new HashMap<>();
            vehicles.put("CV8U3A", new Vehicle("Lunduk", "Sherpa", "CV8U3A", "2022", "Honda", "Civic", "Black"));
            vehicles.put("P0IU98", new Vehicle("Samuel", "Ritter", "P0IU98", "2021", "Dodge", "Charger", "Tan"));


        }
        return vehicles;
    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.vehicleLv);
        ArrayList<String> arrayList = new ArrayList<>();

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

    }


}