package com.dmitrysukhov.weatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmitrysukhov.weatherapp.R;

public class RecyclerAdapterDetails extends RecyclerView.Adapter<RecyclerAdapterDetails.SecondViewHolder>{

    Context context;
    private final String[] stringArrayDetailsCardsTime;
    private final String[] stringArrayDetailsCardsWeather;
    private final String[] stringArrayDetailsCardsWindSpeed;
    int[] imagesDetailsCards;

    public RecyclerAdapterDetails(Context ct, String[] s1, String[] s2, String[] s3, int[] img) {
        context = ct;
        stringArrayDetailsCardsTime = s1;
        stringArrayDetailsCardsWeather = s2;
        stringArrayDetailsCardsWindSpeed = s3;
        imagesDetailsCards = img;
    }

    @NonNull
    @Override
    public RecyclerAdapterDetails.SecondViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.cardview_weather, viewGroup, false);
        return new RecyclerAdapterDetails.SecondViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterDetails.SecondViewHolder viewHolder, final int position) {
        viewHolder.textViewDetailsTime.setText(stringArrayDetailsCardsTime[position]);
        viewHolder.textViewDetailsWeather.setText(stringArrayDetailsCardsWeather[position]);
        viewHolder.textViewDetailsWindSpeed.setText(stringArrayDetailsCardsWindSpeed[position]);
        viewHolder.imageViewCardDetails.setImageResource(imagesDetailsCards[position]);
    }

    @Override
    public int getItemCount() {
        return imagesDetailsCards.length;
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
