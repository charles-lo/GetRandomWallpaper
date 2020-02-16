package ch.protonmail.android.protonmailtest

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
 * Shows the upcoming list of days returned by the API in order of day
 **/
class UpcomingFragment : Fragment() {
    private val TAG = UpcomingFragment::class.qualifiedName
    private val protonModel: ProtonViewModel by viewModel()

    companion object {
        private const val SHARED_PREF = "SHARED_PREF"
        private const val UPCOMING = "UPCOMING"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_upcoming, container, false)
        val swipeRefreshLayout = rootView.findViewById<SwipeRefreshLayout>(R.id.swipe_container)
        swipeRefreshLayout.setOnRefreshListener(refreshListener)

        val gSon = Gson()
        val preference = activity?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

        protonModel.getUpcoming()
        protonModel.listOfUpcoming.observe(
            this,
            Observer(function = fun(infoList: List<WeatherInfo>?) {
                infoList?.let {
                    swipeRefreshLayout.isRefreshing = false
                    if (infoList.isNotEmpty()) {
                        val sortedList = infoList.sortedWith(compareBy(WeatherInfo::day))

                        updateList(rootView, sortedList)
                        // save local cache
                        preference?.edit()
                            ?.putString(UPCOMING, gSon.toJson(sortedList))
                            ?.apply()
                    } else {
                        // load local cache
                        val strCache: String? = preference?.getString(UPCOMING, "")
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
        return rootView
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener{
        protonModel.getUpcoming()
    }

    private fun updateList(rootView: View, infoList: List<WeatherInfo>) {
        val layoutManager = LinearLayoutManager(context)
        val adapter = ForecastAdapter(context, infoList)
        val recycler = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
    }
}