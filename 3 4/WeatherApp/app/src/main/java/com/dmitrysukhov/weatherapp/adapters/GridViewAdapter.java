package com.dmitrysukhov.weatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dmitrysukhov.weatherapp.R;

public class GridViewAdapter extends BaseAdapter {
    private final Context mContext;
    public String[] gridViewDetailsNames = {"Ощущается","Влажность","Вероятность дождя","ДАВЛЕНИЕ","Скорость ветра","Индекс УФ"};
    public String[] gridViewDetailsValues = {"-2℃","50%","0%","1031,00hPa","3,7 км/ч","2"};

    public GridViewAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return gridViewDetailsNames.length;
    }

    public Object getItem(int position) {
        return gridViewDetailsNames[position];
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid = inflater.inflate(R.layout.cell_grid_details_fragment_text_values, viewGroup, false);
        } else {
            grid = view;
        }
        TextView textViewName = grid.findViewById(R.id.text_view_details_name);
        TextView textViewValue = grid.findViewById(R.id.text_view_details_value);
        textViewName.setText(gridViewDetailsNames[i]);
        textViewValue.setText(gridViewDetailsValues[i]);

        return grid;
    }
}