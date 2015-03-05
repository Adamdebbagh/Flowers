package tekwin.org.catalog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tekwin.org.model.Flower;
import tekwin.org.parsers.FlowerJSONParser;


public class MainActivity extends ActionBarActivity {

    public static final String PHOTOS_BASE_URL = "http://services.hanselandpetal.com/photos/";



    ProgressBar pb;
    List<MyTask> tasks;
    List<Flower> flowerList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        pb = (ProgressBar)findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();

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
                requestData("http://services.hanselandpetal.com/secure/flowers.json");
            }else
            {
                Toast.makeText(this,"Network isn't available",Toast.LENGTH_LONG).show();
            }

        }

        return false;
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
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

    private class MyTask extends AsyncTask<String,String,List<Flower>> {


        @Override
        protected void onPreExecute() {

            //updateDisplay("Starting Task...");
            if ( tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);

        }

        @Override
        protected List<Flower> doInBackground(String... params) {

           String content = HttpManager.getData(params[0],"feeduser","feedpassword");
            flowerList = FlowerJSONParser.parseFeed(content);

            for (Flower flower :flowerList) {

                try {
                    String imageUrl = PHOTOS_BASE_URL + flower.getPhoto();
                    InputStream in = (InputStream) new URL(imageUrl).getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    flower.setBitmap(bitmap);
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();

                }

            }


            return flowerList;
        }


        @Override
        protected void onPostExecute(List<Flower> result) {
            tasks.remove(this);
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }

            if (result == null){
                Toast.makeText(MainActivity.this,"Can't connect to web Service",Toast.LENGTH_LONG).show();
                return;
            }


            updateDisplay();


        }


    }
}
