package com.dmitrysukhov.thirdhomework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.SecondViewHolder>{

    Context context;
    private final String[] stringArray1;
    private final String[] stringArray2;
    private final String[] stringArray3;
    int[] images;

    public SecondAdapter(Context ct, String[] s1, String[] s2, String[] s3, int[] img) {
        context = ct;
        stringArray1 = s1;
        stringArray2 = s2;
        stringArray3 = s3;
        images = img;
    }

    @NonNull
    @Override
    public SecondAdapter.SecondViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardview_weather, viewGroup, false);
        return new SecondAdapter.SecondViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SecondAdapter.SecondViewHolder viewHolder, final int position) {
        viewHolder.textViewDetailsTime.setText(stringArray1[position]);
        viewHolder.textViewDetailsWeather.setText(stringArray2[position]);
        viewHolder.textViewDetailsWindSpeed.setText(stringArray3[position]);
        viewHolder.imageViewCardDetails.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }
    public static class SecondViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDetailsTime;
        private final TextView textViewDetailsWeather;
        private final TextView textViewDetailsWindSpeed;
        private final ImageView imageViewCardDetails;

        public SecondViewHolder(@NonNull View view) {
            super(view);
            imageViewCardDetails = view.findViewById(R.id.image_view_card_details);
            textViewDetailsTime = view.findViewById(R.id.text_view_card_details_time);
            textViewDetailsWeather = view.findViewById(R.id.text_view_card_details_weather);
            textViewDetailsWindSpeed = view.findViewById(R.id.text_view_card_details_wind_speed);
        }
    }
}
