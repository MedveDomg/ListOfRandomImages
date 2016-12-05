package omg.medvedomg.listofrandomimages1.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DownloadService extends IntentService {
    Context context;
    private final String TAG = "MY_TAG";

    ProgressBar progressBar;
    private List<Integer> progress = new ArrayList<Integer>();

    Thread download;
    String filepath = "/mnt/sdcard/downloaf/first.jpg";

    public static final int UPDATE_PROGRESS = 8344;
    public static final int FULL_PROGRESS = 8343;



    public DownloadService() {
        super("");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandle intent start");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String urlToDownload = intent.getStringExtra("url");
        ResultReceiver receiver = intent.getParcelableExtra("reciever");
        try {
            URL url = new URL(urlToDownload);
            Log.d(TAG, "url is " + urlToDownload);
            URLConnection connection = url.openConnection();
            connection.connect();

            int fileLength = connection.getContentLength();
            Bundle progressBundle = new Bundle();
            progressBundle.putInt("fullprogress",fileLength);
            receiver.send(FULL_PROGRESS,progressBundle);

            InputStream input = new BufferedInputStream(connection.getInputStream());

            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Download/" + "blablabla.png");

            byte data[] = new byte[1024];
            long total = 0;
            int count = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                Bundle resultData = new Bundle();

                resultData.putInt("progress", (int) (total * 100 / fileLength));
//                resultData.putInt("fullprogress", fileLength);
                receiver.send(UPDATE_PROGRESS, resultData);
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bundle resultData = new Bundle();
        resultData.putInt("progress" ,100);
        receiver.send(UPDATE_PROGRESS, resultData);
    }



}
