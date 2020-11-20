package com.sritt.parkingsnap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class PlateReaderActivity extends AppCompatActivity {
    private static final String TAG = "ErrorLog";
    static String platenum ="";
    public static String getPlatenum(){
        return platenum;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_reader);


        // to add a vehicle to the database without using the register button
        //test.addToFirebase();

        int requestCode = 1;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED);
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, requestCode);
    }
    // onClick for button
    public void openCam (View view){
        dispatchTakePictureIntent();
    }


    public void checkPlate(View view){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Vehicles");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot snapshot){
                if(snapshot.child(platenum).exists()) {
                    Context context = getApplicationContext();
                    CharSequence text = "Plate " + platenum + "is in the database";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
                else{
                    PlateDialogFragment plateDF = new PlateDialogFragment();
                    plateDF.show(getSupportFragmentManager(), "TAG");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void toNewVehicle(View view){
        Intent tonNewVehicleIntent = new Intent(this, NewVehicleActivity.class);
        startActivity(tonNewVehicleIntent);
    }
    public void toList(View view){
        Intent toListIntent = new Intent(this, ListActivity.class);
        startActivity(toListIntent);
    }

    // Sends intent to open camera for initial image capture
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // log error state
            Log.e(TAG, "dispatchTakePictureIntent: ActivityNotFoundException ");
        }
    }

    InputImage image;
    // Creates variables and calls functions on the captured image to process it
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(imageBitmap);
            image = InputImage.fromBitmap(imageBitmap, 0);
            recognizeText(image);
            //processTextBlock(re);
        }
    }

    // TODO create models for different states
    private void recognizeText(InputImage image) {
        ArrayList <String> months = new ArrayList<String>();
        months.add("JAN");
        months.add("FEB");
        months.add("MAR");
        months.add("APR");
        months.add("MAY");
        months.add("JUN");
        months.add("JUL");
        months.add("AUG");
        months.add("SEP");
        months.add("OCT");
        months.add("NOV");
        months.add("DEC");
        TextRecognizer recognizer = TextRecognition.getClient();
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                // Task completed successfully
                                String textF = "";
                                for (Text.TextBlock block : visionText.getTextBlocks()) {
                                    Rect boundingBox = block.getBoundingBox();
                                    Point[] cornerPoints = block.getCornerPoints();
                                    for (Text.Line line: block.getLines()) {
                                        // ...
                                        for (Text.Element element: line.getElements()) {
                                            String text = element.getText();
                                            Log.e(TAG,text);
                                            TextView imageTextTV = findViewById(R.id.imageTextTV);
                                            if (text.length()==3 && (!months.contains(text))) {
                                                textF = textF + text + " ";
                                                platenum = textF;
                                            }
                                            imageTextTV.setText("Detected Number: " + platenum);
                                        }
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });
    }

    private void processTextBlock(Text result) {
        // [START mlkit_process_text_block]
        String resultText = result.getText();
        for (Text.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements()) {
                    String elementText = element.getText();
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                }
            }
        }
        // [END mlkit_process_text_block]
    }





}