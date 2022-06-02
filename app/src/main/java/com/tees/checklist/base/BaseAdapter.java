package com.tees.checklist.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tees.checklist.R;

import java.util.List;


public class BaseAdapter extends ArrayAdapter<String> {
    public BaseAdapter(Context context, List<String> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.listText);
        tvName.setText(item.toString());

        return convertView;
    }
}