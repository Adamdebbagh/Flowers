package tekwin.org;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adamdebbagh on 3/10/15.
 */
public class RequestPackage {

    private String uri;
    private String method = "GET";
    private Map<String,String> params = new HashMap<>();

    public void setParam(String key, String value){
        params.put(key,value);
    }

    public String getEncodedParams(){
        String value = null;
        StringBuilder sb = new StringBuilder();
        for (String key :params.keySet()) {
            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (sb.length() > 0){
                sb.append("&");
            }

            sb.append(key + "=" + value);

        }
        return sb.toString();
    }

    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}

