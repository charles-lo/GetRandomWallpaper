package ch.protonmail.android.protonmailtest

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ch.protonmail.android.protonmailtest.model.WeatherInfo
import ch.protonmail.android.protonmailtest.viewmodel.ProtonViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by ProtonMail on 2/25/19.
 * Shows any days that have less than a 50% chance of rain, ordered hottest to coldest
 * */
class HottestFragment : Fragment()  {
    private val TAG = HottestFragment::class.simpleName
    private val protonModel: ProtonViewModel by viewModel()

    companion object {
        private const val SHARED_PREF = "SHARED_PREF"
        private const val HOTTEST = "HOTTEST"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_hottest, container, false)
        val swipeRefreshLayout = rootView.findViewById<SwipeRefreshLayout>(R.id.swipe_container)
        swipeRefreshLayout.setOnRefreshListener(refreshListener)

        val gSon = Gson()
        val preference = activity?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

        protonModel.networkError.observe(this,
            Observer(function = fun(_: Boolean?) {
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(activity, "internet offline", Toast.LENGTH_SHORT).show()
                activity?.findViewById<TextView>(R.id.offline)?.visibility = VISIBLE
            }
            )
        )
        protonModel.listOfHottest.observe(
            this,
            Observer(function = fun(infoList: List<WeatherInfo>?) {
                infoList?.let {
                    swipeRefreshLayout.isRefreshing = false
                    activity?.findViewById<TextView>(R.id.offline)?.visibility = GONE
                    if (infoList.isNotEmpty()) {
                        val sortedList = infoList.filter { it.chance_rain < 0.5 }
                            .sortedWith(compareByDescending(WeatherInfo::high))

                        updateList(rootView, sortedList)
                        // save local cache
                        preference?.edit()
                            ?.putString(HOTTEST, gSon.toJson(sortedList))
                            ?.apply()
                    } else {
                        // check local cache
                        val strCache: String? = preference?.getString(HOTTEST, "")
                        val type = object : TypeToken<List<WeatherInfo>>() {}.type

                        if (strCache!!.isNotBlank()) {
                            val infoCache: List<WeatherInfo> = gSon.fromJson(strCache, type)
                            Log.d(TAG, "" + infoCache)

                            if (infoCache.isNotEmpty()) {
                                updateList(rootView, infoCache)
                            } else {
                            }
                        } else {
                        }
                    }
                }
            })
        )
        protonModel.getHottest()
        return rootView
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener{
        protonModel.getHottest()
    }

    private fun updateList(rootView: View, infoList: List<WeatherInfo>) {
        val layoutManager = LinearLayoutManager(context)
        val adapter = ForecastAdapter(context, infoList)
        val recycler = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }
}