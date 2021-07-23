package com.mitsui.tkmid.pdfviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pdf = findViewById(R.id.pdfView) as PDFView
        pdf.fromAsset("10410.pdf").load()
    }
}