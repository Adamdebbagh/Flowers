package tekwin.org.catalog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tekwin.org.model.Flower;


public class MainActivity extends ActionBarActivity {

    public static final String PHOTOS_BASE_URL = "http://services.hanselandpetal.com/photos/";
    public static final String ENDPOINT = "http://services.hanselandpetal.com";


    ProgressBar pb;

    List<Flower> flowerList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        pb = (ProgressBar)findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_get_data) {
            if (isOnline()) {
                requestData("http://services.hanselandpetal.com/feeds/flowers.json");
            }else
            {
                Toast.makeText(this,"Network isn't available",Toast.LENGTH_LONG).show();
            }

        }

        return false;
    }

    private void requestData(String uri) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();
        FlowersApi api = adapter.create(FlowersApi.class);
        api.getFeed(new Callback<List<Flower>>() {
            @Override
            public void success(List<Flower> flowers, Response response) {
                flowerList = flowers;
                updateDisplay();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Toast.makeText(MainActivity.this,retrofitError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    protected void updateDisplay() {
        // use Flower Adapter to display data
        FlowerAdapter adapter = new FlowerAdapter(this, R.layout.item_flower, flowerList);
        //ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    // check whether Network connectivity available.
    private  boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
