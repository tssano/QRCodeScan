package com.mitsui.tkmid.pdffromurl

import android.os.AsyncTask
import android.os.Environment
import java.io.File
import java.io.IOException
import kotlin.coroutines.coroutineContext
import android.content.Context

class DownloadPDF() : AsyncTask<String, Void, Void>() {

    lateinit var mContext: Context

    constructor(context: Context) :this() {
        mContext = context
    }

    override fun doInBackground(vararg params: String?): Void? {

        // Creating the name of files and urls
        var fileUrl : String? = params[0]
        var fileName : String? = params[1]

        // Getting reference for external storage
        //val extStorageDirectory : File? = Environment.getExternalStorageDirectory()
        val intStorageDirectory : File? = mContext.filesDir

        // File location
        //var folder = File(extStorageDirectory, "PDF DOWNLOAD")
        //folder.mkdir()

        //var pdfFile = File(folder, fileName)
        var pdfFile = File(intStorageDirectory, fileName)

        try {
            //pdfFile.createNewFile()
        } catch (e : IOException) {
            e.printStackTrace()
        }

        // File downloader class
        var fileDownloader = FileDownloader()
        if (fileUrl != null) {
            fileDownloader.downloadFile("${fileUrl}${fileName}", pdfFile)
        }
        return null

    }

}