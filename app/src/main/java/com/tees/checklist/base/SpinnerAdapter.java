package com.tees.checklist.base;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> values;

    public SpinnerAdapter(Context context, int textViewResourceId,
                          List<String> values) {

        //Pass in the resource id:  R.id.text_view
        super(context, textViewResourceId, values);

        this.context = context;
        this.values = values;
    }
}