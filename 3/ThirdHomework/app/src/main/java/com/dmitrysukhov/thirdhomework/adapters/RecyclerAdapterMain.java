package com.dmitrysukhov.thirdhomework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmitrysukhov.thirdhomework.R;

public class RecyclerAdapterMain extends RecyclerView.Adapter<RecyclerAdapterMain.FirstViewHolder> {

    Context context;
    private final String[] stringArrayAdapterDays;
    private final String[] stringArrayAdapterWeather;
    private final String[] stringArrayAdapterTemperature;
    int[] images;

    public RecyclerAdapterMain(Context ct, String[] s1, String[] s2, String[] s3, int[] img) {
        context = ct;
        stringArrayAdapterDays = s1;
        stringArrayAdapterWeather = s2;
        stringArrayAdapterTemperature = s3;
        images = img;
    }

    @NonNull
    @Override
    public FirstViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.cardview_recent, viewGroup, false);
        return new FirstViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FirstViewHolder viewHolder, final int position) {
        viewHolder.textViewDay.setText(stringArrayAdapterDays[position]);
        viewHolder.textViewState.setText(String.format("%s%s", viewHolder.textViewState.getText().toString(), stringArrayAdapterWeather[position]));
        viewHolder.textViewTemperature.setText(stringArrayAdapterTemperature[position]);
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
