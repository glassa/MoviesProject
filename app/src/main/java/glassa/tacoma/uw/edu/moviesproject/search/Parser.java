package glassa.tacoma.uw.edu.moviesproject.search;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


/**
 * This a parser class to parse data from Transport class
 * Displays 'Can not Parse' if its unable to parse the given String
 * Created by Yunhang on 2016/11/5.
 */
public class Parser extends AsyncTask<Void, Void,Integer> {

    private Context myCon;
    private String myString;
    private ListView myListView;
    private String myClass;

    private ArrayList<String> myArray = new ArrayList<>();

    /**
     * constrouct for this class
     *
     * @param theCon      passed in Context
     * @param theString   pased in String
     * @param theListView passed in ListView
     * @param theClass    passed in String
     */
    public Parser(Context theCon, String theString, ListView theListView, String theClass) {
        myCon = theCon;
        myString = theString;
        myListView = theListView;
        myClass = theClass;
    }

    /**
     * Display th listView if input legal, otherwise display a warning
     * @param integer is use for estimate input is legal or not
     */
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        /**
         * display what input current
         * otherwise display "Unable to parse data"
         */
        if (integer == 1) {
            ArrayAdapter adapter = new ArrayAdapter(myCon, android.R.layout.simple_list_item_1, myArray);
            myListView.setAdapter(adapter);
        }else {
            Toast.makeText(myCon, "Unable to parse data", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * parse method to get String DB
     * @return the String which get
     */
    private int parseCourseJSON () {
        try {
            JSONArray arr = new JSONArray(myString);
            JSONObject obj = null;
            String input;
            myArray.clear();
            for (int i = 0; i < arr.length(); i++) {
                obj = arr.getJSONObject(i);
                input = obj.getString(myClass);
                myArray.add(input);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * invoked on the UI thread before the task is executed
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * perform background operations and publish results on the UI thread
     * @param params parms
     * @return parse class
     */
    @Override
    protected Integer doInBackground(Void... params) {
        return parseCourseJSON();
    }
}