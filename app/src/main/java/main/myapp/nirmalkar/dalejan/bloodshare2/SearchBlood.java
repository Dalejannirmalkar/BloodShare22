package main.myapp.nirmalkar.dalejan.bloodshare2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class SearchBlood extends AppCompatActivity {
    WebView webview;
    Button sby10, sby15, sby20, sby60;
    public static final int CONNECTION_TIMEOUT = 7000;
    public static final String SERVER_ADDRESS = "http://csvtu.ac.in/ew/newblood/";

Double latitude,longitude;
    TextView kkk1;
    public static String bg;
    int five = 5, fifteen = 15, ten = 10, twenty = 20, sixty = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sby10 = (Button) findViewById(R.id.bsearch10);
        sby15 = (Button) findViewById(R.id.bsearch15);
        sby20 = (Button) findViewById(R.id.bsearch20);
        sby60 = (Button) findViewById(R.id.bsearch60);
        kkk1 = (TextView) findViewById(R.id.kkk1);
        webview = (WebView) findViewById(R.id.webView1);
        GPSTracker gps = new GPSTracker(SearchBlood.this);
        if (gps.canGetLocation()) {
            latitude= gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }
        if (bg.equalsIgnoreCase("Select Blood Group")||bg==null){
            Toast.makeText(SearchBlood.this,"Please Go back and Select the Blood group",Toast.LENGTH_LONG).show();

        }
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                ArrayList<NameValuePair> dataToSend = new ArrayList<>();

                dataToSend.add(new BasicNameValuePair("bloodg", bg));
                dataToSend.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
                dataToSend.add(new BasicNameValuePair("longitude", Double.toString(longitude)));
                dataToSend.add(new BasicNameValuePair("radius",Integer.toString(five) ));

                HttpParams httpRequestParam = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
                HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);
                HttpClient client = new DefaultHttpClient(httpRequestParam);
                HttpPost post = new HttpPost(SERVER_ADDRESS + "search_km.php");

                try {
                    post.setEntity(new UrlEncodedFormEntity(dataToSend));
                    HttpResponse httepResponse = client.execute(post);
                    HttpEntity entity = httepResponse.getEntity();
                    final String result = EntityUtils.toString(entity);
                    webview.post(new Runnable() {
                        @Override
                        public void run() {
                            kkk1.setText("List of people within 5 km ");
                            webview.loadData(result,"text/html","utf-8");
                            sby10.setVisibility(View.VISIBLE);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        sby10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        ArrayList<NameValuePair> dataToSend = new ArrayList<>();

                        dataToSend.add(new BasicNameValuePair("bloodg", bg));
                        dataToSend.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
                        dataToSend.add(new BasicNameValuePair("longitude", Double.toString(longitude)));
                        dataToSend.add(new BasicNameValuePair("radius",Integer.toString(ten) ));

                        HttpParams httpRequestParam = new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
                        HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);
                        HttpClient client = new DefaultHttpClient(httpRequestParam);
                        HttpPost post = new HttpPost(SERVER_ADDRESS + "search_km.php");

                        try {
                            post.setEntity(new UrlEncodedFormEntity(dataToSend));
                            HttpResponse httepResponse = client.execute(post);
                            HttpEntity entity = httepResponse.getEntity();

                            final String result = EntityUtils.toString(entity);
                            webview.post(new Runnable() {
                                @Override
                                public void run() {
                                    kkk1.setText("List of people within 10 km ");
                                    sby10.setVisibility(View.GONE);
                                    webview.loadData(result,"text/html","utf-8");
                                    sby15.setVisibility(View.VISIBLE);
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR) ;


            }
        });

        sby15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        ArrayList<NameValuePair> dataToSend = new ArrayList<>();

                        dataToSend.add(new BasicNameValuePair("bloodg", bg));
                        dataToSend.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
                        dataToSend.add(new BasicNameValuePair("longitude", Double.toString(longitude)));
                        dataToSend.add(new BasicNameValuePair("radius",Integer.toString(fifteen) ));

                        HttpParams httpRequestParam = new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
                        HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);
                        HttpClient client = new DefaultHttpClient(httpRequestParam);
                        HttpPost post = new HttpPost(SERVER_ADDRESS + "search_km.php");

                        try {
                            post.setEntity(new UrlEncodedFormEntity(dataToSend));
                            HttpResponse httepResponse = client.execute(post);
                            HttpEntity entity = httepResponse.getEntity();

                            final String result = EntityUtils.toString(entity);
                            webview.post(new Runnable() {
                                @Override
                                public void run() {
                                    kkk1.setText("List of people within 15 km ");
                                    sby15.setVisibility(View.GONE);
                                    webview.loadData(result,"text/html","utf-8");
                                    sby20.setVisibility(View.VISIBLE);
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR) ;

            }
        });

        sby20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        ArrayList<NameValuePair> dataToSend = new ArrayList<>();

                        dataToSend.add(new BasicNameValuePair("bloodg", bg));
                        dataToSend.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
                        dataToSend.add(new BasicNameValuePair("longitude", Double.toString(longitude)));
                        dataToSend.add(new BasicNameValuePair("radius",Integer.toString(twenty) ));

                        HttpParams httpRequestParam = new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
                        HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);
                        HttpClient client = new DefaultHttpClient(httpRequestParam);
                        HttpPost post = new HttpPost(SERVER_ADDRESS + "search_km.php");

                        try {
                            post.setEntity(new UrlEncodedFormEntity(dataToSend));
                            HttpResponse httepResponse = client.execute(post);
                            HttpEntity entity = httepResponse.getEntity();

                            final String result = EntityUtils.toString(entity);
                            webview.post(new Runnable() {
                                @Override
                                public void run() {
                                    kkk1.setText("List of people within 20 km ");
                                    sby20.setVisibility(View.GONE);
                                    webview.loadData(result,"text/html","utf-8");
                                    sby60.setVisibility(View.VISIBLE);
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR) ;

            }
        });
        sby60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        ArrayList<NameValuePair> dataToSend = new ArrayList<>();

                        dataToSend.add(new BasicNameValuePair("bloodg", bg));
                        dataToSend.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
                        dataToSend.add(new BasicNameValuePair("longitude", Double.toString(longitude)));
                        dataToSend.add(new BasicNameValuePair("radius",Integer.toString(sixty) ));

                        HttpParams httpRequestParam = new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
                        HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);
                        HttpClient client = new DefaultHttpClient(httpRequestParam);
                        HttpPost post = new HttpPost(SERVER_ADDRESS + "search_km.php");

                        try {
                            post.setEntity(new UrlEncodedFormEntity(dataToSend));
                            HttpResponse httepResponse = client.execute(post);
                            HttpEntity entity = httepResponse.getEntity();

                            final String result = EntityUtils.toString(entity);
                            webview.post(new Runnable() {
                                @Override
                                public void run() {
                                    kkk1.setText("List of people within 60 km ");
                                    sby60.setVisibility(View.GONE);
                                    webview.loadData(result,"text/html","utf-8");
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR) ;
            }
        });


    }

    @Override
    public void onBackPressed() {

        if (sby60.getVisibility() == View.VISIBLE) {
            sby60.setVisibility(View.GONE);
        }
        if (sby20.getVisibility() == View.VISIBLE) {
            sby20.setVisibility(View.GONE);
        }
        if (sby15.getVisibility() == View.VISIBLE) {
            sby15.setVisibility(View.GONE);

        } else {
            super.onBackPressed();
        }
    }

}

