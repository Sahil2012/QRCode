package com.example.qrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

public class ScanActivity extends AppCompatActivity {

    private ScannerLiveView scannerLiveView;
    private TextView setDecode;
    private Button bck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        if(checkPermission()){
            Toast.makeText(ScanActivity.this,"Permission Granted...",Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        scannerLiveView = findViewById(R.id.scV);
        setDecode = findViewById(R.id.scannedText);
        bck = findViewById(R.id.bck);

        scannerLiveView.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {

            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {

            }

            @Override
            public void onScannerError(Throwable err) {

            }

            @Override
            public void onCodeScanned(String data) {
                scannerLiveView.setVisibility(View.INVISIBLE);
                setDecode.setVisibility(View.VISIBLE);
                bck.setVisibility(View.VISIBLE);
                setDecode.setText(data);
            }
        });

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scannerLiveView.setVisibility(View.VISIBLE);
                setDecode.setVisibility(View.INVISIBLE);
                bck.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ZXDecoder zxDecoder = new ZXDecoder();

        zxDecoder.setScanAreaPercent(0.5);

        scannerLiveView.setDecoder(zxDecoder);
        scannerLiveView.startScanner();
    }

    @Override
    protected void onPause() {
        scannerLiveView.stopScanner();
        super.onPause();
    }

    private boolean checkPermission() {
        int cam =  ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        int vib = ContextCompat.checkSelfPermission(getApplicationContext(),VIBRATE);

        return cam == PackageManager.PERMISSION_GRANTED && vib == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[] {CAMERA,VIBRATE},100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0) {

            boolean a = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean b = grantResults[1] == PackageManager.PERMISSION_GRANTED;

            if(a && b) {
                Toast.makeText(ScanActivity.this,"Permission Granted...",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ScanActivity.this,"Permission Denied...",Toast.LENGTH_SHORT).show();
            }
        }
    }
}