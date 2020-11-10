package com.nakhmadov.footballapp

import android.os.AsyncTask
import android.util.Log
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.util.concurrent.ExecutionException

class ServerRequest {
    fun getJSONFromUrl(
        url: String?,
        params: List<NameValuePair?>?
    ): JSONObject? {
        try {
            val httpClient =
                DefaultHttpClient()
            val httpPost =
                HttpPost(url)
            httpPost.entity = UrlEncodedFormEntity(params)
            val httpResponse: HttpResponse = httpClient.execute(httpPost)
            val httpEntity = httpResponse.entity
            `is` = httpEntity.content
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: ClientProtocolException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            val reader = BufferedReader(
                InputStreamReader(
                    `is`, "iso-8859-1"
                ), 8
            )
            val sb = StringBuilder()
            var line: String? = null
            while (reader.readLine().also { line = it } != null) {
                sb.append(line + "n")
            }
            `is`!!.close()
            json = sb.toString()
            Log.e("JSON", json)
        } catch (e: Exception) {
            Log.e("Buffer Error", "Error converting result $e")
        }
        try {
            jObj =
                JSONObject(json)
        } catch (e: JSONException) {
            Log.e("JSON Parser", "Error parsing data $e")
        }
        return jObj
    }

    var jobj: JSONObject? = null
    fun getJSON(
        url: String?,
        params: List<NameValuePair?>?
    ): JSONObject? {
        val param =
            Params(url, params)
        val myTask =
            Request()
        try {
            jobj = myTask.execute(param).get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
        return jobj
    }

    private class Params internal constructor(
        var url: String?,
        var params: List<NameValuePair?>?
    )

    private inner class Request :
        AsyncTask<Params?, String?, JSONObject?>() {

        override fun onPostExecute(json: JSONObject?) {
            super.onPostExecute(json)
        }

        override fun doInBackground(vararg params: Params?): JSONObject? {
            val request =
                ServerRequest()
            return request.getJSONFromUrl(params[0]?.url, params[0]?.params)
        }

    }

    companion object {
        var `is`: InputStream? = null
        var jObj: JSONObject? = null
        var json = ""
    }
}