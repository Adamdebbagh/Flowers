package tekwin.org.flowers;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;

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

        if (id == R.id.action_settings) {
            MyTask task = new MyTask();
            //Parallel Task Processing
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Param 1", "Param 2", "Param 3");

        }

        return false;
    }

    protected void updateDisplay(String message) {
        output.append(message +"\n");
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

            for (int i = 0; i <params.length ; i++) {

                publishProgress("working in Progress with : " + params[i]);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "Task Complete";
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
