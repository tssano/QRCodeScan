package com.mitsui.tkmid.pdffromurl

import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class FileDownloader {

    private val MEGABYTE : Int = 1024*1024

    public fun downloadFile(fileUrl: String, directory: File) {

        try{
            //URL CONNECTION
            var url : URL = URL(fileUrl)
            var urlConnection : HttpURLConnection = url.openConnection() as HttpURLConnection
            urlConnection.connect()

            //INPUT STREAM
            var inputStream : InputStream = urlConnection.inputStream

            //OUTPUT STREAM
            var fileOutputStream : FileOutputStream = FileOutputStream(directory)

            //Copy INPUT STREAM to file
            inputStream.copyTo(fileOutputStream)

            fileOutputStream.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}