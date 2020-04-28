package com.humber.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingApp();
        connectBTApp();
        sendText();
        emojies();
    }

    public void drawingApp(){
        ImageButton drawApp = findViewById(R.id.drawing_app_button);
        drawApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DrawGalleryActivity.class));
            }
        });
    }

    public void connectBTApp(){
        ImageButton btApp = findViewById(R.id.bt_menu);
        btApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BluetoothConnectActivity.class));
            }
        });
    }

    public void sendText(){
        ImageButton btApp = findViewById(R.id.send_text);
        btApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SendTextActivity.class));
            }
        });
    }
    public void emojies(){
        ImageButton btApp = findViewById(R.id.emojis);
        btApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EmojiSelectActivity.class));
            }
        });
    }



}
