package com.example.kalpeshsoni.practical6;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button b2;
    private static final int CAMERA_REQUEST = 50;
    private boolean flashLightStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b2 = findViewById(R.id.button2);

        final boolean hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        boolean isEnabled = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;



        b2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);
                if(hasCameraFlash)
                {
                    if(flashLightStatus)
                    {
                        flashLightOff();
                    }
                    else
                    {
                        flashLightOn();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No flashlight is available in your device",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void flashLightOn()
    {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            String CameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(CameraId, true);
            flashLightStatus = true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void flashLightOff()
    {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try{
            String CameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(CameraId, false);
            flashLightStatus = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
