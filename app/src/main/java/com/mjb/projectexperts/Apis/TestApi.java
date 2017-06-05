package com.mjb.projectexperts.Apis;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mjb.projectexperts.Domain.Route;
import com.mjb.projectexperts.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import layout.MainFragment;


public class TestApi extends AsyncTask<String, Integer, String> {


    public static ArrayList<Route> routeList;

    public TestApi() {

    }

    @Override
    protected String doInBackground(String... params) {

        String result = null;

        try {

            //Consumo  Get
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet getRequest = new HttpGet(
                    "http://localhost:8080/webapiphp/index.php/welcome/routes/"+params[0]);
            getRequest.addHeader("accept", "application/json");

            /**Consumo  post
            HttpPost httpPost = new HttpPost("http://localhost:8080/webapiphp/index.php/welcome/routes/");

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("parametro1", "valor"));
            nameValuePair.add(new BasicNameValuePair("parametro2", "valor"));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            **/

            //Respuesta
            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));


            result = br.readLine();

            httpClient.getConnectionManager().shutdown();

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }


        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //int progreso = values[0].intValue();

    }

    @Override
    protected void onPostExecute(String result) {

        JSONArray json_array = null;
        JSONObject jObject;
        try {
            jObject = new JSONObject(result);
            json_array = jObject.optJSONArray("Routes");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        routeList = new ArrayList<>();

        for (int i = 0; i < json_array.length(); i++) {
            try {
                routeList.add(new Route(json_array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
