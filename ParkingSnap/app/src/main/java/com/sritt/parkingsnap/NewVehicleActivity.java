package com.sritt.parkingsnap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

public class NewVehicleActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;

    public boolean validate(String s){
        if(s.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vehicle);

        EditText firstName = findViewById(R.id.firstNameET);
        EditText lastName = findViewById(R.id.lastNameET);
        EditText make = findViewById(R.id.makeET);
        EditText plateNum = findViewById(R.id.numET);
        EditText model = findViewById(R.id.modelET);
        EditText color = findViewById(R.id.colorET);
        EditText year = findViewById(R.id.yearET);
        Button registerBTN = findViewById(R.id.registerBTN);


        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valid = true;

                boolean[] valids = new boolean[7];
                valids[0] = validate(firstName.getText().toString());
                valids[1] = validate(lastName.getText().toString());
                valids[2] = validate(make.getText().toString());
                valids[3] = validate(plateNum.getText().toString());
                valids[4] = validate(model.getText().toString());
                valids[5] = validate(color.getText().toString());
                valids[6] = validate(year.getText().toString());


                for (boolean i : valids) {
                    if (!(valid && i)) {
                        valid = false;
                        Toast.makeText(NewVehicleActivity.this, "Error: one or more fields are empty", Toast.LENGTH_SHORT).show();

                    }
                }

                if (valid) {
                    Vehicle v = new Vehicle(firstName.getText().toString(), lastName.getText().toString(), plateNum.getText().toString(), year.getText().toString(), make.getText().toString(), model.getText().toString(), color.getText().toString());


                    Intent intent = new Intent(NewVehicleActivity.this, ListActivity.class);
                    intent.putExtra("vehicle",v);
                    intent.putExtra("firstName",  v.getFirstName());
                    intent.putExtra("lastName",  v.getLastName());
                    intent.putExtra("licence", v.getLicence());
                    intent.putExtra("make",  v.getMake());
                    intent.putExtra("model",  v.getModel());
                    intent.putExtra("color",v.getColor());
                    intent.putExtra("year", v.getYear());
                    startActivity(intent);
                    firebaseInsert(firstName.getText().toString(), lastName.getText().toString(), plateNum.getText().toString(), year.getText().toString(), make.getText().toString(), model.getText().toString(), color.getText().toString());



                }
            }
        });


        }

    public void firebaseInsert(String firstName, String lastName, String licence, String year, String make, String model, String color){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Vehicles");
        Vehicle vehicle = new Vehicle( firstName, lastName, licence, year, make, model, color);
        reference.child(licence).setValue(vehicle);
    }











    }
