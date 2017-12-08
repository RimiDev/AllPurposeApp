package cs.dawson.dawsonelectriccurrents;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

public class FindFriendActivity extends MenuActivity {

    private ListView friendsView;
    private List<String> friendsList;
    private JSONObject jsonObject;
    private JSONParser jsonParser  = new JSONParser();
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);

        fillListView();
    }

    private AdapterView.OnItemClickListener showFriends = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(FindFriendActivity.this, FindFriendCourseActivity.class);
            //intent.putExtra("FriendEmail", friendsList.get(position));

            startActivity(intent);
        }
    };

    public void fillListView(){
        ListView friendsListView = (ListView) findViewById(R.id.friendsListView);
        List<String> temp = new ArrayList<>();
        temp.add("1");

        friendsList = temp;

        if(friendsList.get(0).equalsIgnoreCase("User has no friends.")){
            TextView noFriendsTV = (TextView) findViewById(R.id.noFriendTV);
            noFriendsTV.setText("No friends");
        }
        else {
            List<String> friends = new ArrayList<>();
            for (int i = 0; i < friendsList.size(); i++)
                friends.add(friendsList.get(i));

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.cancelled_class, friends);
            friendsListView.setAdapter(adapter);
            friendsListView.setOnItemClickListener(showFriends);
        }
    }

    private class FriendsAsyncTask extends AsyncTask<String, Void, List<String>>{
        @Override
        protected List<String> doInBackground(String... urls){

            //JSONObject jsonObject = jsonParser.makeHttpRequest(url, "POST", friendsList);
            return null;
        }
    }

    /**
     * Method which calls the super method on onCreateOptionsMenu to display the menu. Required
     * so code to show the menu will not have to be repeated for each activity.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    /**
     * Method which calls the super method on onOptionsItemSelected to add functionality to the menu
     * buttons without having to repeat the code for each activity.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
