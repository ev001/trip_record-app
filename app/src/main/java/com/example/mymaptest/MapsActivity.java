package com.example.mymaptest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemClickListener {

    private GoogleMap mMap;
    public LatLng mlng;

    ArrayList<LatLng> lllist = new ArrayList<>();

    private final String dbName = "MAPS";
    private final String tableName = "MAP";
    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private Menu1Fragment menu1Fragment = new Menu1Fragment();
    private Menu2Fragment menu2Fragment = new Menu2Fragment();
    private Menu3Fragment menu3Fragment = new Menu3Fragment();

    public void myCallback(String title, String snippet) {
        Toast.makeText(this, " title: " + title + " / snippet: " + snippet, Toast.LENGTH_LONG).show();

        /*CustomListFragment customListFragment = (CustomListFragment) getSupportFragmentManager().findFragmentById(R.id.);
        customListFragment.addItem(title, snippet,mlng);

        Log.d("위경도","위도" + mlng.latitude + "경도 " + mlng.longitude);
*/
    }

    public static String getKeyHash(final Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
        if (packageInfo == null) return null;
        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w("TAG", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. 레이아웃을 정의한 레이아웃 리소스(R.layout)을 사용하여 현재 액티비티의 화면을 구성하도록 합니다.
        setContentView(R.layout.activity_map);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlusActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();
        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_home: {
                        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_dashboard: {
                        transaction.replace(R.id.frame_layout, menu2Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_notifications: {
                        transaction.replace(R.id.frame_layout, menu3Fragment).commitAllowingStateLoss();
                        break;
                    }
                }

                return true;
            }
        });
        try {
            Log.d("TAG", "Key hash is " + getKeyHash(this));
        } catch (PackageManager.NameNotFoundException ex) {// handle exception
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng mapMain = new LatLng(35.95, 128.25);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mapMain));
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