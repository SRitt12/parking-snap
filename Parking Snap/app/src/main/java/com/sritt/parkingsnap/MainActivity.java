package com.sritt.parkingsnap;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.view.PreviewView;

public class MainActivity extends AppCompatActivity {
    //private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView = findViewById(R.id.previewView);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //cameraProviderFuture = ProcessCameraProvider.getInstance(this);

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