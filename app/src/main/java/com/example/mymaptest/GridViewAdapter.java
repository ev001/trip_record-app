package com.example.mymaptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<>();
    public GridViewAdapter(){

    }
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2);

        ListViewItem listViewItem = listViewItemList.get(position);

        titleTextView.setText(listViewItem.getTitleStr());
        descTextView.setText(listViewItem.getDescStr());

        return convertView;
    }
}
