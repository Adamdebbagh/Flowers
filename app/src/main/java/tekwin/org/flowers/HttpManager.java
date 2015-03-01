package tekwin.org.flowers;

import android.net.http.AndroidHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * Created by adamdebbagh on 2/28/15.
 */
public class HttpManager {

    public static String getData(String uri){

        AndroidHttpClient client = AndroidHttpClient.newInstance("androidAgent");
        HttpGet request = new HttpGet(uri);
        HttpResponse response;

        try {

            response = client.execute(request);
            return EntityUtils.toString(response.getEntity());

        }catch (Exception e){

            e.printStackTrace();
            return null;
        }
        finally {
            client.close();
        }
    }
}
