package tekwin.org.catalog;

import android.util.Base64;

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

    public static String getData(String uri, String userName, String password) {

        BufferedReader reader = null;
        HttpURLConnection con = null;

        byte[] loginBytes = (userName + ":" + password).getBytes();

        // encode the credentials
        StringBuilder loginBuilder = new StringBuilder()
                //important . Basic is followed by a space character. if not app will crash
                .append("Basic ")
                .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));

        try {
            URL url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();

            //send a request to the server with a header "Authorization"
            con.addRequestProperty("Authorization", loginBuilder.toString());

            //get Content from the web
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            // read one line at a time
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();

            try {
                if (con != null) {
                    int status = con.getResponseCode();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        } finally {
            if (reader != null) {
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
