package com.charles.lab.protonmailtest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.charles.lab.protonmailtest.ForecastAdapter.DayViewHolder
import com.charles.lab.protonmailtest.model.WeatherInfo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson

/**
 * Created by ProtonMail on 2/25/19.
 */
class ForecastAdapter(
    private val mContext: Context,
    private val m_Data: List<WeatherInfo>
) : RecyclerView.Adapter<DayViewHolder>() {
    private var gSon: Gson? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val item =
            LayoutInflater.from(mContext).inflate(R.layout.item_forecast, parent, false)
        gSon = Gson()
        return DayViewHolder(item)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val info = m_Data[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.INFO, gSon!!.toJson(info))
            mContext.startActivity(intent)
        }
        holder.day.text = mContext.getString(R.string.day, info.day)
        holder.description.text = info.description
        holder.chanceRain.text = mContext.getString(R.string.chance_rain, info.chance_rain)
        holder.highTemp.text = mContext.getString(R.string.high, info.high)
        Glide.with(mContext)
            .load(info.image)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return m_Data.size
    }

    class DayViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val day: TextView = v.findViewById(R.id.day)
        val description: TextView = v.findViewById(R.id.description)
        val chanceRain: TextView = v.findViewById(R.id.chance_rain)
        val highTemp: TextView = v.findViewById(R.id.high_tmp)
        val imageView: ImageView = v.findViewById(R.id.image)
    }

}