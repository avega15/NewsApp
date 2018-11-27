package cs4540.newsapp.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String URL = "https://newsapi.org/v1/articles";
    private static final String SOURCE_PARAM = "source";
    private static final String SOURCE = "the-next-web";
    private static final String SORTBY_PARAM = "sortBy";
    private static final String SORTBY = "latest";
    private static final String APIKEY_PARAM = "apiKey";
    private static final String APIKEY = "be632a45eb00471db696022782632a96";

    public static URL buildUrl() {
        Uri uri = Uri.parse(URL).buildUpon()
                .appendQueryParameter(SOURCE_PARAM, String.valueOf(SOURCE))
                .appendQueryParameter(SORTBY_PARAM, String.valueOf(SORTBY))
                .appendQueryParameter(APIKEY_PARAM, String.valueOf(APIKEY))
                .build();
        try {
            URL url = new URL(uri.toString());
            Log.v(TAG, "URL: " + url);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = httpUrlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            httpUrlConnection.disconnect();
        }
    }
}
