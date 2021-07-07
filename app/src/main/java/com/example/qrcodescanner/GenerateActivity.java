package com.example.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateActivity extends AppCompatActivity {

    private Button genCode;
    private EditText edit;
    private ImageView ig;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        genCode = findViewById(R.id.genCode);
        edit = findViewById(R.id.editCode);
        ig = findViewById(R.id.qrDis);

        genCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = edit.getText().toString().trim();

                if(str.equals("")){
                    Toast.makeText(GenerateActivity.this,"Enter the text to generate the code...",Toast.LENGTH_SHORT).show();
                } else {

                    WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

                    Display display = windowManager.getDefaultDisplay();

                    Point p = new Point();
                    display.getSize(p);

                    int width = p.x;
                    int height = p.y;

                    int dimen = width < height ? width : height;

                    dimen = dimen * 3 / 4;

                    qrgEncoder = new QRGEncoder(str,null, QRGContents.Type.TEXT,dimen);

                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();

                        ig.setVisibility(View.VISIBLE);
                        edit.setVisibility(View.GONE);
                        genCode.setVisibility(View.GONE);

                        ig.setImageBitmap(bitmap);

                    } catch (WriterException e) {
                        e.printStackTrace();
                        Toast.makeText(GenerateActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}