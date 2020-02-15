package ch.protonmail.android.protonmailtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ch.protonmail.android.protonmailtest.model.WeatherInfo;

/**
 * Created by ProtonMail on 2/25/19.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.DayViewHolder> {

    private List<WeatherInfo> m_Data;
    private Context mContext;

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.item_forecast, parent, false);

        return new ForecastAdapter.DayViewHolder(item);
    }

    public ForecastAdapter(Context mContext, List<WeatherInfo> data) {
        this.mContext = mContext;
        this.m_Data = data;
    }


    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        WeatherInfo info = m_Data.get(position);
        holder.day.setText("day : " + info.getDay());
        holder.description.setText(info.getDescription());
        holder.chanceRain.setText("rain rate : " + info.getChance_rain());
        holder.highTemp.setText("high temp : " + info.getHigh() + "°");
        Glide.with(mContext)
                .load(info.getImage())
                .skipMemoryCache(true)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return m_Data.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {

        private TextView day;
        private TextView description;
        private TextView chanceRain;
        private TextView highTemp;
        private ImageView imageView;

        DayViewHolder(@NonNull View v) {
            super(v);
            day = v.findViewById(R.id.day);
            description = v.findViewById(R.id.description);
            imageView = v.findViewById(R.id.image);
            chanceRain = v.findViewById(R.id.chance_rain);
            highTemp = v.findViewById(R.id.high_tmp);
        }
    }
}
