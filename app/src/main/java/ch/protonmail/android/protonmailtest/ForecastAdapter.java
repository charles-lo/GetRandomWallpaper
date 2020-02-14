package ch.protonmail.android.protonmailtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ProtonMail on 2/25/19.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.DayViewHolder> {

    String[] data;
    private Context mContext;

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.item_forecast , parent ,false);

        return new ForecastAdapter.DayViewHolder(item);
    }

    public ForecastAdapter(Context mContext) {
        this.mContext = mContext;
//        this.mDatas = mDatas;
    }


    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
//        holder.titleView.setText(data[0]);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {

        private TextView titleView;
        public DayViewHolder(@NonNull View v) {
            super(v);
//            titleView = v;
        }
    }
}
