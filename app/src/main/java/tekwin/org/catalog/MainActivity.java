package tekwin.org.catalog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tekwin.org.model.Flower;


public class MainActivity extends ActionBarActivity {

    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;
    List<Flower> flowerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output =(TextView)findViewById(R.id.textView);
        output.setMovementMethod(new ScrollingMovementMethod());
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
                requestData("http://services.hanselandpetal.com/feeds/flowers.json");
            }else
            {
                Toast.makeText(this,"Network isn't available",Toast.LENGTH_LONG).show();
            }

        }

        return false;
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        //Parallel Task Processing
        task.execute(uri);
    }

    protected void updateDisplay(String message) {
        output.append(message +"\n");
    }
    // check whether Network connectivity available.
    private  boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private class MyTask extends AsyncTask<String,String,String> {


        @Override
        protected void onPreExecute() {

            updateDisplay("Starting Task...");
            if ( tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);

        }

        @Override
        protected String doInBackground(String... params) {

           String content = HttpManager.getData(params[0]);

            return content;
        }


        @Override
        protected void onPostExecute(String result) {
           updateDisplay(result);

            tasks.remove(this);
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {

            updateDisplay(values[0]);
        }
    }
}
