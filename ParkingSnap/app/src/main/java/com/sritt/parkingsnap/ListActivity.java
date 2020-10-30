package com.sritt.parkingsnap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.Serializable;
import java.util.HashMap;

public class ListActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }
     static HashMap<String, Vehicle> vehicles = new HashMap();

}