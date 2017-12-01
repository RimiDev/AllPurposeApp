package cs.dawson.dawsonelectriccurrents.cancelled;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cs.dawson.dawsonelectriccurrents.MenuActivity;
import cs.dawson.dawsonelectriccurrents.R;
import cs.dawson.dawsonelectriccurrents.beans.CancelledClass;

public class CancelledActivity extends MenuActivity
{
    private RecyclerView cancelledListView;
    private List<CancelledClass> cancelledClassList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled);

        cancelledListView = (RecyclerView) findViewById(R.id.cancelledListView);
        cancelledListView.setLayoutManager(new LinearLayoutManager(this));

        AsyncFeed af = new AsyncFeed();
        af.execute((Void)null);
    }

    private AdapterView.OnClickListener showCancelledClasses = new AdapterView.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(CancelledActivity.this, ShowCancelActivity.class);
            startActivity(intent);
        }
    };


    private class AsyncFeed extends AsyncTask<Void, Void, Boolean>
    {
        private String urlLink;

        @Override
        protected Boolean doInBackground(Void... voids)
        {
            try
            {
                URL url = new URL("https://www.dawsoncollege.qc.ca/wp-content/external-includes/cancellations/feed.xml");
                InputStream inputStream = url.openConnection().getInputStream();
                cancelledClassList = getRssFeedData(inputStream);
                return true;
            }
            catch(Exception e)
            {
                Log.d("AsyncFeed", "doInBackgroundError: " + e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success)
        {
            if(success)
            {
                cancelledListView.setOnClickListener(showCancelledClasses);
                cancelledListView.setAdapter(new RssFeedListAdapter(cancelledClassList));
            }
        }

        private List<CancelledClass> getRssFeedData(InputStream inputStream)
        {
            List<CancelledClass> cancelledClassList = new ArrayList<>();
            boolean isItem = false;

            try
            {
                String title = "", course = "", teacher = "", dateTimeCancelled = "";
                URL url = new URL("https://www.dawsoncollege.qc.ca/wp-content/external-includes/cancellations/feed.xml");
                inputStream = url.openConnection().getInputStream();
                XmlPullParser xmlParser = Xml.newPullParser();
                xmlParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlParser.setInput(inputStream, null);

                xmlParser.nextTag();
                while (xmlParser.next() != XmlPullParser.END_DOCUMENT) {

                    String result = "";
                    int eventType = xmlParser.getEventType();
                    String name = xmlParser.getName();
                    if (name == null)
                        continue;


                    if (eventType == XmlPullParser.END_TAG) {
                        if (name.equalsIgnoreCase("item"))
                            isItem = false;
                        continue;
                    }



                    if (eventType == XmlPullParser.START_TAG) {
                        if (name.equalsIgnoreCase("item"))
                        {
                            isItem = true;
                            continue;
                        }
                    }

                    if (xmlParser.next() == XmlPullParser.TEXT) {
                        result = xmlParser.getText();
                        xmlParser.nextTag();
                    }
                    Log.d("CancelledActivity", "INSIDE");
                    if (name.equalsIgnoreCase("title"))
                        title = result;
                    else if (name.equalsIgnoreCase("course"))
                        course = result;
                    else if (name.equalsIgnoreCase("teacher"))
                        teacher = result;
                    else if (name.equalsIgnoreCase("pubDate"))
                        dateTimeCancelled = result;

                    if (!title.equals("") && !course.equals("") && !teacher.equals("") && !dateTimeCancelled.equals("")) {
                        if (isItem) {
                            Log.d("CancelledActivity", title + " " + course + " " + teacher + " " + dateTimeCancelled);
                            CancelledClass cc = new CancelledClass(title, course, teacher, dateTimeCancelled);
                            cancelledClassList.add(cc);
                        }

                        title = "";
                        course = "";
                        teacher = "";
                        dateTimeCancelled = "";
                    }
                }
                inputStream.close();
            } catch (Exception e) {
                Log.d("AsyncFeed", "doInBackgroundError: " + e.getMessage());
                try {
                    throw e;
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (XmlPullParserException e1) {
                    e1.printStackTrace();
                }
            }
            return cancelledClassList;
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
