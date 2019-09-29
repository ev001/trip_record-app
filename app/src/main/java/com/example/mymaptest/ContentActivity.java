package com.example.mymaptest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class ContentActivity extends AppCompatActivity implements OnMapReadyCallback {
    Double latitude, longitude;
    String content, title;
    GoogleMap mMap;
    LatLng mlng;
    private final String dbName = "MAPS";
    private final String tableName = "MAP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Intent intent = getIntent();
        latitude = intent.getExtras().getDouble("latitude");
        longitude = intent.getExtras().getDouble("longitude");
        getData();

        mlng = new LatLng(latitude, longitude);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.cmap);
        mapFragment.getMapAsync(this);

        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        tv1.setText(title);
        tv2.setText(content);

        Button btn1 = findViewById(R.id.delete);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delData();
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button kakaoLinkBtn  = findViewById(R.id.share); // 카카오톡 링크 구현에 실패하였습니다. kakaolink import 불가.
        kakaoLinkBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(mlng).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mlng));
    }

    public void getData() {
        try {
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            //SELECT문을 사용하여 테이블에 있는 데이터를 가져옵니다..
            String sql = "SELECT * FROM " + tableName + " WHERE latitude = ? AND longitude = ?";
            Log.d("sqlllllllllll", sql);
            Cursor c = ReadDB.rawQuery(sql, new String[]{String.valueOf(latitude), String.valueOf(longitude)});

            if (c != null) {
                if (c.moveToFirst()) {
                    content = c.getString(c.getColumnIndex("content"));
                    title = c.getString(c.getColumnIndex("title"));
                    Log.d("search success", content + title);
                }
            }
            ReadDB.close();
        } catch (SQLiteException se) {
            Log.e("", se.getMessage());
        }
    }

    public void delData() {

        try {
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            //SELECT문을 사용하여 테이블에 있는 데이터를 가져옵니다.
            String sql = "DELETE FROM " + tableName + " WHERE latitude = ? AND longitude = ?";
            Log.d("sqlllllllllll", sql);
            Cursor c = ReadDB.rawQuery(sql, new String[]{String.valueOf(latitude), String.valueOf(longitude)});

            if (c != null) {
                if (c.moveToFirst()) {
                }
            }
            ReadDB.close();

        } catch (SQLiteException se) {
            Log.e("", se.getMessage());
        }
    }
}


