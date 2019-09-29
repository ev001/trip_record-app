package com.example.mymaptest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Menu2Fragment extends Fragment implements GoogleMap.OnMapLoadedCallback, OnMapReadyCallback,  AdapterView.OnItemClickListener {

    private GoogleMap mMap;
    public  LatLng mlng;
    private final String dbName = "MAPS";
    private final String tableName = "MAP";
    ArrayList<LatLng> lllist = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // Add a marker in Sydney and move the camera
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        addnowMarker();
        for(int i = 0; i < lllist.size(); i++){
            mMap.addMarker(new MarkerOptions().position(lllist.get(i)).title(""));
            Log.i("uiiiiiiiiiiiiiiiii", i + " ");
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                intent.putExtra("latitude", marker.getPosition().latitude);
                intent.putExtra("longitude", marker.getPosition().longitude);

                startActivity(intent);
                getActivity().finish();
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    void addnowMarker (){
        try {
            SQLiteDatabase ReadDB = getActivity().openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            //SELECT문을 사용하여 테이블에 있는 데이터를 가져옵니다..
            Cursor c = ReadDB.rawQuery("SELECT * FROM " + tableName, null);
            Log.i("a112121212aaaaaaaaaaaaaaaaaaaa", "ASDFASDF");

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        //테이블에서 두개의 컬럼값을 가져와서
                        String title = c.getString(c.getColumnIndex("title"));
                        String content = c.getString(c.getColumnIndex("content"));
                        Double latitude = c.getDouble(c.getColumnIndex("latitude"));
                        Double longitude = c.getDouble(c.getColumnIndex("longitude"));
                        Log.i("aaaaaaaaaaaaaaaaaaaaa", "ASDFASDF");
                        LatLng ll = new LatLng(latitude, longitude);
                        Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", ll.toString());
                        lllist.add(ll);
                      mMap.addMarker(new MarkerOptions().position(ll).title("Marker in new")); // 마커 추가
                    } while (c.moveToNext());
                }
            }
            ReadDB.close();
        } catch (SQLiteException se) {
            Log.e("", se.getMessage());
        }
    }

    @Override
    public void onMapLoaded() {
        addnowMarker();
    }
}
