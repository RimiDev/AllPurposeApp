package cs.dawson.dawsonelectriccurrents.cancelled;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cs.dawson.dawsonelectriccurrents.MenuActivity;
import cs.dawson.dawsonelectriccurrents.R;
import cs.dawson.dawsonelectriccurrents.beans.CancelledClass;

/**
 * CancelledActivity which is responsible for displaying a list
 * of all the courses that are cancelled by using an RSSFeed.
 *
 */
public class CancelledActivity extends MenuActivity
{
    private List<CancelledClass> cancelledClassList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled);

        RssAsyncTask task = new RssAsyncTask();
        task.execute(getString(R.string.cancelledClassUrl));
        Log.d("CancelledActivity", "Thread Name: " + Thread.currentThread().getName());
    }

    private AdapterView.OnItemClickListener showCancelledClasses = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(CancelledActivity.this, ShowCancelActivity.class);
            intent.putExtra("ClassCancelled", cancelledClassList.get(position));

            startActivity(intent);
        }
    };

    private AdapterView.OnItemLongClickListener searchFriends = new AdapterView.OnItemLongClickListener()
    {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l)
        {
            Intent intent = new Intent(CancelledActivity.this, ShowCancelledFriends.class);
            intent.putExtra("course", cancelledClassList.get(position));

            startActivity(intent);

            return true;
        }
    };

    private class RssAsyncTask extends AsyncTask<String, Void, List<CancelledClass>>
    {
        @Override
        protected List<CancelledClass> doInBackground(String... urls)
        {
            try
            {
                RssFeeder rssReader = new RssFeeder(urls[0]);
                return rssReader.getItems();

            }
            catch (Exception e)
            {
                Log.e("Canclled", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<CancelledClass> result)
        {
            Log.d("CancelledActivity", "List Size: " + result.size() + " " + result.get(0).getTitle());

            if(result.get(0).getTitle().equalsIgnoreCase("No classes cancelled."))
            {
                TextView noCancelledClasses = (TextView)findViewById(R.id.noCancelledClassesView);
                noCancelledClasses.setText(R.string.noCancelledClasses);
            }
            else
            {
                ListView cancelledListView = (ListView) findViewById(R.id.cancelledListView);
                cancelledClassList = result;

                List<String> courses = new ArrayList<>();
                for(int i = 0; i < result.size(); i++)
                    courses.add(result.get(i).getTitle() + " " + result.get(i).getCourse());

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CancelledActivity.this,
                        R.layout.cancelled_class, courses);
                cancelledListView.setAdapter(adapter);
                cancelledListView.setOnItemClickListener(showCancelledClasses);
                cancelledListView.setOnItemLongClickListener(searchFriends);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}