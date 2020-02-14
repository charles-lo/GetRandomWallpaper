package ch.protonmail.android.protonmailtest

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by ProtonMail on 2/25/19.
 * Shows any days that have less than a 50% chance of rain, ordered hottest to coldest
 * */
class HottestFragment : Fragment() {

    // TODO: Please fix any errors and implement the missing parts (including any UI changes)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_hottest, container, false)

        val layoutManager = LinearLayoutManager(context)
        val adapter = ForecastAdapter(context)

        val recycler = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        recycler.adapter = adapter
        recycler.layoutManager = layoutManager
        fetchData()
        return rootView
    }

    fun fetchData() {
        if (dataPresentInLocalStorage()) {
            fetchDataFromLocalStorage()
        } else {
            fetchDataFromServer()
        }
    }

    fun fetchDataFromServer() {
        FetchDataFromServerTask().execute()
    }

    fun fetchDataFromLocalStorage(): Array<String>? {
        // TODO implement
        return null
    }

    fun dataPresentInLocalStorage(): Boolean = true

    class FetchDataFromServerTask : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg p0: String?): String {
            val url = URL("https://5c5c8ba58d018a0014aa1b24.mockapi.io/api/forecast")
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connect()

            val responseCode: Int = httpURLConnection.responseCode

            var response: String = ""
            if (responseCode == 200) {
                response = httpURLConnection.responseMessage
            }
            return response
        }
    }
}