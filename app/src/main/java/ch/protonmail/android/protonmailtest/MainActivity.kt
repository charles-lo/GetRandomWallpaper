package ch.protonmail.android.protonmailtest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pager = findViewById<ViewPager>(R.id.pager)
        val adapter = TabsAdapter(this, supportFragmentManager)
        pager.adapter = adapter
        val tabs = findViewById<TabLayout>(R.id.tabLayout);
        tabs.addTab(tabs.newTab().setText("upcoming"))
        tabs.addTab(tabs.newTab().setText("hottest"))
    }
}
