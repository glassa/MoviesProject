package glassa.tacoma.uw.edu.moviesproject;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;

import glassa.tacoma.uw.edu.moviesproject.profile.ProfileActivity;


/**
 * The fragment of the third tab of the TabHost on MainActivity. this handles
 * the search users function.
 */
public class Tab4FindUsers extends Fragment {

    String mCurrentUser;
    private String myS = "";
    private final String URL="http://cssgate.insttech.washington.edu/~_450team2/userSearcher.php";
    private String myPass = "";

    /**
     * Instantiates a new Tab 4 find users.
     */
    public Tab4FindUsers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab4_find_users, container, false);
        getActivity().setTitle("Find Users");
        mCurrentUser = ((TabHostActivity)getActivity()).getmCurrentUser();

        final EditText myInput= (EditText)v.findViewById(R.id.search_user_bar);
        Button mySearch = (Button)v.findViewById(R.id.search_user_button);
        mySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myS = myInput.getText().toString();
                    myPass = URL + "?username=" + myS;
                    run(myPass);
                }
                catch(Exception e) {
                    Toast.makeText(getActivity(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
        return v;
    }

    public void run(String theURL) {
        getFilteredUsersTask getFilteredUsersTask = new getFilteredUsersTask();
        getFilteredUsersTask.execute(new String[]{theURL.toString()});
    }

    private class getFilteredUsersTask extends AsyncTask<String, Void, String> {

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
        @Override
        protected void onPostExecute(String JSONresult) {
            final ArrayList<String> arrayList = new ArrayList<>();

            try {
                JSONArray jArray = new JSONArray(JSONresult);
                for (int j = 0; j < jArray.length(); j++) {
                    JSONObject json_data = jArray.getJSONObject(j);
                    StringBuilder sb = new StringBuilder();

                    String username = json_data.getString("Username");

                    sb.append(username);


                    arrayList.add(sb.toString());
                }
            }
            catch (Exception e) {
                Log.e("", "" + e);  }

            ListView list=(ListView)getView().findViewById(R.id.listview_search_user);
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, arrayList);
            list.setAdapter(arrayAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent myIntent = new Intent(getActivity(),ProfileActivity.class);
                    myIntent.putExtra("TARGET_USER", arrayList.get(position));
                    myIntent.putExtra("CURRENT_USER", mCurrentUser);
                    startActivity(myIntent);
                }
            });
        }
    }
}
