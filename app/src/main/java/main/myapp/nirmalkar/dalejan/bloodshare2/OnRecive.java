package main.myapp.nirmalkar.dalejan.bloodshare2;

import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
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
import java.util.Timer;
import java.util.TimerTask;

public class
OnRecive extends Service {
    Double latitude,longitude;
    public static String user = null;

    public static final int CONNECTION_TIMEOUT = 7000;
    public static final String SERVER_ADDRESS = "http://csvtu.ac.in/ew/newblood/";
    public Boolean GPSEnable(){
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            return false;
        }
        return true;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
   if (GPSEnable()) {
    final GPSTracker gps = new GPSTracker(getApplicationContext());
    Timer t1 = new Timer();
    if (gps.canGetLocation()) {
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        TimerTask tt = new TimerTask() {
            public void run() {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        ArrayList<NameValuePair> dataToSend = new ArrayList<>();

                        dataToSend.add(new BasicNameValuePair("email", user));
                        dataToSend.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
                        dataToSend.add(new BasicNameValuePair("longitude", Double.toString(longitude)));

                       HttpParams httpRequestParam = new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(httpRequestParam, CONNECTION_TIMEOUT);
                        HttpConnectionParams.setSoTimeout(httpRequestParam, CONNECTION_TIMEOUT);
                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(SERVER_ADDRESS + "sby_loc.php");

                        try {
                            post.setEntity(new UrlEncodedFormEntity(dataToSend));
                            HttpResponse httepResponse = client.execute(post);
                            HttpEntity entity = httepResponse.getEntity();
                            final String result = EntityUtils.toString(entity);
                            Log.d("UPDATE RESULT", result);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        };
        t1.scheduleAtFixedRate(tt, 100, 10000);
    } else {
        gps.showSettingsAlert();
    }


}
        return super.onStartCommand(intent, flags, startId);
    }




    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
