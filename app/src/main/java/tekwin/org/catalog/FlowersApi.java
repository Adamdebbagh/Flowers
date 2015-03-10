package tekwin.org.catalog;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import tekwin.org.model.Flower;

/**
 * Created by adamdebbagh on 3/10/15.
 */
public interface FlowersApi {
    @GET("/feeds/flowers.json")
    public void getFeed(Callback<List<Flower>> response);

}
