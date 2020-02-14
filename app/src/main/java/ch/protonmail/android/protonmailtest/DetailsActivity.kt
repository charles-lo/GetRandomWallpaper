package ch.protonmail.android.protonmailtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

/**
 * Created by ProtonMail on 2/25/19.
 * Shows all the details for a particular day.
 */
class DetailsActivity : AppCompatActivity() {

    // TODO: Please fix any errors and implement the missing parts (including any UI changes)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        findViewById<Button>(R.id.download).setOnClickListener(downloadListener)
        setTitle()
        displayData("")
    }

    fun setTitle() {
        title = "Details"
    }

    fun displayData(text: String) {
        findViewById<TextView>(R.id.title).text = text
    }

    val downloadListener = View.OnClickListener {
        downloadTheImage("")
    }

    fun downloadTheImage(url: String) {
        // TODO implement the procedure for image downloading
    }
}
