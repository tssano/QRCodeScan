package com.mitsui.tkmid.pdffromurl

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDownload: Button = findViewById(R.id.btnDownload)
        btnDownload.setOnClickListener {
            //Toast.makeText(this, "This is Download Button!", Toast.LENGTH_SHORT).show()
            //var downloadPDF = DownloadPDF()
            var downloadPDF = DownloadPDF(this)
            downloadPDF.execute("https://www.nippan-r.co.jp/product/file/","00549.pdf")
        }

        val btnView: Button = findViewById(R.id.btnView)
        btnView.setOnClickListener {
            //Toast.makeText(this, "This is View Button!", Toast.LENGTH_SHORT).show()
            //var pdfFile =  File("${Environment.getExternalStorageDirectory()}/PDF DOWNLOAD/01020.pdf")
            var pdfFile = File(this.filesDir,"00549.pdf")
            pdfView.fromFile(pdfFile).load()
        }

    }
}