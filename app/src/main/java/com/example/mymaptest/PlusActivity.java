package com.example.mymaptest;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlusActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemClickListener {

    private GoogleMap mMap;
    private final String dbName = "MAPS";
    private final String tableName = "MAP";
    private  String title;
    private  String content;
    public LatLng mlng;

    SQLiteDatabase sampleDB = null;
    EditText edi1, edi2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 레이아웃을 정의한 레이아웃 리소스(R.layout)을 사용하여 현재 액티비티의 화면을 구성하도록 합니다.
        setContentView(R.layout.activity_plus);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
         edi1 = findViewById(R.id.ediview1);
         edi2 = findViewById(R.id.ediview2);

        //DB
        try {
            sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            //테이블이 존재하지 않으면 새로 생성합니다.
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName
                    + " (title VARCHAR(20), content VARCHAR(100), latitude  DOUBLE(100), longitude  DOUBLE(40) );");

        } catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = edi1.getText().toString();
                content = edi2.getText().toString();

                //새로운 데이터를 테이블에 집어넣습니다..
                sampleDB.execSQL("INSERT INTO " + tableName
                        + " (title, content, latitude, longitude)  Values (' " + title + "', '" + content + "', '" + mlng.latitude + "', '" + mlng.longitude+"');");
                sampleDB.close();

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap  = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() { // 마커를 클릭하면 뜨는 이벤트를 위한 리스너
            @Override
            public void onMapLongClick(LatLng latLng) { // 지도선택 메써드

                LatLng newLocation = new LatLng(latLng.latitude, latLng.longitude); //onMapClick 의 리턴으로 받은 LatLng 객체의 위도 경도로 만듬
                mMap.addMarker(new MarkerOptions().position(newLocation).title("Marker in new")); // 마커 추가
                mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));  // 만든 마커로 이동
                mlng = latLng;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}