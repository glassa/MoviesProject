package glassa.tacoma.uw.edu.moviesproject.search;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

import glassa.tacoma.uw.edu.moviesproject.R;
import glassa.tacoma.uw.edu.moviesproject.profile.ProfileActivity;

/**
 * Search user bar class for project
 */
public class SearchUserActivity extends AppCompatActivity {

    private String myS = "";
    private final String URL="http://cssgate.insttech.washington.edu/~_450team2/userSearcher.php";
    private String myPass = "";


    /**
     * onCreat create the search bar frame and list of result
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_search_user);;
//        run(URL);

        final EditText myInput= (EditText)findViewById(R.id.search_user_bar);
        Button mySearch = (Button)findViewById(R.id.search_user_button);
        mySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myS = myInput.getText().toString();
                    myPass = URL + "?username=" + myS;
                    run(myPass);
                }
                catch(Exception e) {
                    Toast.makeText(SearchUserActivity.this, "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    /**
     * basic method to create listview from database
     * @param theURL is the user database link
     */
    public void run(String theURL) {
        Task task = new Task();
        task.execute(new String[]{theURL.toString()});

        final ArrayList<String> arrayList = new ArrayList<>();

        try {
            JSONArray jArray = new JSONArray(URLDecoder.decode(task.get(), "UTF-8" ));
            for (int j = 0; j < jArray.length(); j++) {
                JSONObject json_data = jArray.getJSONObject(j);
                StringBuilder sb = new StringBuilder();

                String username = String.format("%-15s", json_data.getString("Username"));

                sb.append(username);


                arrayList.add(sb.toString());
            }
        }
        catch (Exception e) {
            Log.e("", "" + e);  }

        ListView list=(ListView)findViewById(R.id.listview_search_user);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(SearchUserActivity.this,ProfileActivity.class);
                myIntent.putExtra("TARGET_USER", arrayList.get(position));
                //write something here to transfer current user to ProfileActivity
//                myIntent.putExtra("TARGET_USER", arrayList.get(position));
                startActivity(myIntent);
            }
        });
    }

    /**
     * AsyncTask for database
     */
    private class Task extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    java.net.URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    InputStream content = urlConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
    }
}