package com.a.access;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AccessActivity extends AppCompatActivity {

    TextView tvAccess;
    Button btnSwitchAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);
        tvAccess= (TextView) findViewById(R.id.access_status);
        btnSwitchAccess= (Button) findViewById(R.id.open_access);


        btnSwitchAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                AccessActivity.this.startActivityForResult(intent, 1000);

            }
        });

        findViewById(R.id.jump_appdetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts( "package",AccessActivity.this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);

//                jumpDetail2("com.a.c1");

            }
        });
    }

    private void jumpDetail2(String packagename) {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts( "package",packagename, null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isAccessOn=AccessUtil.isAccessibilitySettingsOn(AccessActivity.this,MyAccessibilityService.class);
        tvAccess.setText("isAccessOn:"+isAccessOn);
        Log.d("vin","onResume isAccessOn:"+isAccessOn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean isAccessOn=AccessUtil.isAccessibilitySettingsOn(AccessActivity.this,MyAccessibilityService.class);
        Log.d("vin","onActivityResult isAccessOn:"+isAccessOn);
        tvAccess.setText("isAccessOn:"+isAccessOn);
    }
}
