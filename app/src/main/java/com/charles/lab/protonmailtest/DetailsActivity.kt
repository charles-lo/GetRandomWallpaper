package com.charles.lab.protonmailtest

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.charles.lab.protonmailtest.model.WeatherInfo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import java.util.*


/**
 * Created by ProtonMail on 2/25/19.
 * Shows all the details for a particular day.
 */
class DetailsActivity : AppCompatActivity() {
    companion object {
        const val INFO = "info"
        private const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }

    private lateinit var info: WeatherInfo
    private lateinit var bitmap: Bitmap
    private lateinit var uri: Uri

    // TODO: Please fix any errors and implement the missing parts (including any UI changes)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        findViewById<Button>(R.id.download).setOnClickListener(downloadListener)
        findViewById<Button>(R.id.gallery).setOnClickListener(galleryListener)
        val strInfo = intent.getStringExtra(INFO)
        info = Gson().fromJson(strInfo, WeatherInfo::class.java)

        displayData(info)
    }

    private fun displayData(info: WeatherInfo) {
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Details " + info.day
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        Glide.with(this)
            .asBitmap()
            .load(info.image)
            .fitCenter()
            .placeholder(R.mipmap.ic_launcher)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    findViewById<ImageView>(R.id.image).setImageBitmap(resource)
                    bitmap = resource
                    findViewById<Button>(R.id.download).isEnabled = true
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })


        findViewById<TextView>(R.id.description).text = info.description
        findViewById<TextView>(R.id.sunrise).text = getString(R.string.sunrise, info.sunrise)
        findViewById<TextView>(R.id.sunset).text = getString(R.string.sunset, info.sunset)
        findViewById<TextView>(R.id.chance_rain).text =
            getString(R.string.chance_rain, info.chance_rain)
        findViewById<TextView>(R.id.high).text = getString(R.string.high, info.high)
        findViewById<TextView>(R.id.low).text = getString(R.string.low, info.low)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private val downloadListener = View.OnClickListener {
        downloadTheImage()
    }

    private val galleryListener = View.OnClickListener {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun downloadTheImage() {
        // TODO implement the procedure for image downloading
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {
                    AlertDialog.Builder(this)
                        .setMessage("We need write external storage permission to download image")
                        .setPositiveButton("OK") { _, _ ->
                            ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS
                            )
                            saveImage(bitmap)
                        }
                        .setNegativeButton("No") { _, _ -> finish() }
                        .show()
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ),
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS
                    )
                }
            } else {
                saveImage(bitmap)
            }
        }
    }

    // Method to save an image to gallery and return uri
    private fun saveImage(bitmap: Bitmap) {
        // Save image to gallery
        try {
            val title = "${UUID.randomUUID()}.jpg"
            val savedImageURL = MediaStore.Images.Media.insertImage(
                contentResolver,
                bitmap,
                title,
                "Image of $title"
            )

            // Parse the gallery image url to uri
            if (savedImageURL.isNullOrEmpty()) {
                toast("Image saved fail. You need to grant WRITE_EXTERNAL_STORAGE permission")
            } else {
                uri = Uri.parse(savedImageURL)
                if (uri != null && uri != Uri.EMPTY) {
                    toast("Image saved successful.")
                    findViewById<Button>(R.id.gallery).isEnabled = true
                } else {
                    toast("Image saved fail.")
                }
            }
        } catch (ex: SecurityException) {
            toast("Image saved fail. You need to grant WRITE_EXTERNAL_STORAGE permission")
        }

    }

    // Extension function to show toast message
    private fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
