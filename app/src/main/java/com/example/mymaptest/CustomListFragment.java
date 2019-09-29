package com.example.mymaptest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomListFragment extends Fragment {
    Activity context;
    AdapterView.OnItemClickListener mCallback = null;
    ListViewAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_custom_list, container, false);
        ListView listView = view.findViewById(R.id.listView);
        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);

        if (mCallback != null) {
            listView.setOnItemClickListener(mCallback);
        }
        return view;
    }

    public void addItem(String title, String desc, LatLng latLng) {
        adapter.addItem(title, desc, latLng);
    }
}