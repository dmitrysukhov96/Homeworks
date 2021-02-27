package com.dmitrysukhov.thirdhomework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.FirstViewHolder> {

    Context context;
    private final String[] stringArray1;
    private final String[] stringArray2;
    private final String[] stringArray3;
    int[] images;
    private String textCircle = " • ";

    public FirstAdapter(Context ct, String[] s1, String[] s2, String[] s3, int[] img) {
        context = ct;
        stringArray1 = s1;
        stringArray2 = s2;
        stringArray3 = s3;
        images = img;
    }

    @NonNull
    @Override
    public FirstViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_recent, viewGroup, false);
        return new FirstViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FirstViewHolder viewHolder, final int position) {
        viewHolder.textViewDay.setText(stringArray1[position]);
        viewHolder.textViewState.setText(textCircle+stringArray2[position]);
        viewHolder.textViewTemperature.setText(stringArray3[position]);
        viewHolder.iconWeather.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class FirstViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDay;
        private final TextView textViewState;
        private final TextView textViewTemperature;
        private final ImageView iconWeather;

        public FirstViewHolder(@NonNull View view) {
            super(view);
            iconWeather = view.findViewById(R.id.image_view_card_view_icon_weather);
            textViewDay = view.findViewById(R.id.text_view_card_day);
            textViewState = view.findViewById(R.id.text_view_card_state);
            textViewTemperature = view.findViewById(R.id.text_view_card_temperature);
        }
    }
}
