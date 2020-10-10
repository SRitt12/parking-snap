package com.sritt.parkingsnap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.view.PreviewView;

public class MainActivity extends AppCompatActivity {
    //private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    //PreviewView previewView = findViewById(R.id.previewView);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        /*We can create a button that when clicked opens up the phone camera and takes a snap
        and stores that image in a image view
         */
        Button cameraBTN = findViewById(R.id.cameraBTN);
        cameraBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, 0);
            }
        });


        Button info = findViewById(R.id.infoBTN);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ini = new Intent(MainActivity.this, VehicleInfo.class);
                startActivity(ini);

            }
        });



    }

    /*
    store the picture in an image view
     */

    @Override
    protected void onActivityResult(int resultCode, int requestCode, Intent intent) {
        super.onActivityResult(resultCode, requestCode, intent);
        ImageView myIV = findViewById(R.id.myIV);

        Bitmap myBM =(Bitmap) intent.getExtras().get("data");
        myIV.setImageBitmap(myBM);


    }

    /*cameraProviderFuture.addListener(() -> {
        try {
            ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
            bindPreview(cameraProvider);
        } catch (ExecutionException | InterruptedException e) {
            // No errors need to be handled for this Future.
            // This should never be reached.
        }
    }, ContextCompat.getMainExecutor(this));

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.createSurfaceProvider());

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview);
    }*/









}