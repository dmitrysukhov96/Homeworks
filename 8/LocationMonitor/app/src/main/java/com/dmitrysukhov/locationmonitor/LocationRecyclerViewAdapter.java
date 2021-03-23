package com.dmitrysukhov.locationmonitor;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Set;

public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.LocationViewHolder> {
    ArrayList<MyLocation> locationArrayList;

    public LocationRecyclerViewAdapter(ArrayList<MyLocation> myLocationArrayList) {
        this.locationArrayList = myLocationArrayList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_recyclerview_item, parent,false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        locationArrayList = new ArrayList<>();
        Set<String> stringSet = SharedPreferencesHelper.getPrefStringSetByKey(MainActivity.NEW_LOCATION_KEY);
        if (stringSet!=null) {
            for (String str: stringSet){
                String[] stringArray = str.split("@");
                MyLocation myLocation = new MyLocation(stringArray[0],stringArray[1]);
                locationArrayList.add(myLocation);
            }
        }
        holder.coordinates.setText(locationArrayList.get(position).getCoordinates());
        holder.address.setText(locationArrayList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return locationArrayList.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView coordinates, address;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            coordinates = itemView.findViewById(R.id.text_view_item_coordinates);
            address = itemView.findViewById(R.id.text_view_item_address);
        }
    }
}
