package tekwin.org.flowers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by adamdebbagh on 2/28/15.
 */
public class HttpManager {

    public static String getData(String uri){

        BufferedReader reader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //get Content from the web
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            // read one line at a time
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            return sb.toString();

        }catch (Exception e) {
           e.printStackTrace();
            return null;
        }
        finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }
}
