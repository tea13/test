package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Picasso

class PhotoPreview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_preview)

        var string: String? = null
        val imageView: ImageView = findViewById(R.id.photoView)
        string = getIntent().getStringExtra("URI_FILE")
        if (string!=null){
            val uri = Uri.parse(string)
            Picasso.get().load(uri).fit().centerCrop().into(imageView)
        }
    }
}