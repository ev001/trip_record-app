package com.example.mymaptest;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Menu3Fragment extends Fragment {
    Activity context;
    AdapterView.OnItemClickListener mCallback = null;
    ListViewAdapter adapter;
    private final String dbName = "MAPS";
    private final String tableName = "MAP";
    ArrayList<ListViewItem> al = new ArrayList<ListViewItem>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = (Activity) context;
        if (context instanceof AdapterView.OnItemClickListener) {
            mCallback = (AdapterView.OnItemClickListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_grid, container, false);
        GridView gridView = view.findViewById(R.id.gridView);
        adapter = new ListViewAdapter();


        gridView.setAdapter(adapter);

        try {
            SQLiteDatabase ReadDB = getActivity().openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            //SELECT문을 사용하여 테이블에 있는 데이터를 가져옵니다.
            Cursor c = ReadDB.rawQuery("SELECT * FROM " + tableName, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        //테이블에서 두개의 컬럼값을 가져와서
                        String title = c.getString(c.getColumnIndex("title"));
                        String content = c.getString(c.getColumnIndex("content"));
                        Double latitude = c.getDouble(c.getColumnIndex("latitude"));
                        Double longitude = c.getDouble(c.getColumnIndex("longitude"));
                        LatLng ll = new LatLng(latitude, longitude);

                        //ListViewItem 넣습니다.
                        ListViewItem item = new ListViewItem(title, content, ll);
                        //ArrayList에 추가합니다..

                        adapter.addItem(title, content, ll);
                    } while (c.moveToNext());
                }
            }

            ReadDB.close();

            //화면에 보여주기 위해 Listview에 연결합니다.
            gridView.setAdapter(adapter);

        } catch (SQLiteException se) {
            Log.e("",  se.getMessage());
        }

        return view;
    }
}