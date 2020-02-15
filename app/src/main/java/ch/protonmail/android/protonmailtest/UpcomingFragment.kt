package ch.protonmail.android.protonmailtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.protonmail.android.protonmailtest.model.WeatherInfo
import ch.protonmail.android.protonmailtest.viewmodel.ProtonViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by ProtonMail on 2/25/19.
 * Shows the upcoming list of days returned by the API in order of day
 **/
class UpcomingFragment : Fragment() {
    private val protonListModel: ProtonViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_upcoming, container, false)

        protonListModel.getUpcoming()
        protonListModel.listOfUpcoming.observe(
            this,
            Observer(function = fun(productList: List<WeatherInfo>?) {
                productList?.let {

                    val layoutManager = LinearLayoutManager(context)
                    val adapter = ForecastAdapter(context, productList)
                    val recycler = rootView.findViewById<RecyclerView>(R.id.recycler_view)
                    recycler.layoutManager = layoutManager
                    recycler.adapter = adapter
                }
            })
        )

        return rootView
    }
}