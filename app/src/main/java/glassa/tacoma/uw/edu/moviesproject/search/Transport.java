package glassa.tacoma.uw.edu.moviesproject.search;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A AsyncTask Send input from search bar to mySQL and Receive response from PHP
 * Created by Yunhang on 2016/11/5.
 */
public class Transport extends AsyncTask<Void,Void,String> {

    private Context myCon;
    private String myURL;
    private String myQuery;
    private ListView myListView;
    private String myClass;

    /**
     * constructor for this class
     * @param theCon passed in Context
     * @param theURL passed in URL
     * @param theQuery passed in Query
     * @param theListView passed in ListView
     * @param theClass passed in String
     */
    public Transport(Context theCon, String theURL, String theQuery, ListView theListView, String theClass) {
        myCon = theCon;
        myURL = theURL;
        myQuery = theQuery;
        myListView = theListView;
        myClass = theClass;
    }

    /**
     * method used to transport data from DB
     * @return string of transport data
     */
    private String transport() {
        HttpURLConnection myConnection = connect(myURL);
        // no URL
        if(myConnection == null) {
            return null;
        }

        try {
            OutputStream myOS = myConnection.getOutputStream();

            BufferedWriter myBuffer = new BufferedWriter(new OutputStreamWriter(myOS));
            myBuffer.write(new DataPack(myQuery).packageData());
            myBuffer.flush();

            // release buffer and os
            myBuffer.close();
            myOS.close();

            int responseCode = myConnection.getResponseCode();
            // decode
            if(responseCode == myConnection.HTTP_OK) {
                InputStream myIS = myConnection.getInputStream();

                BufferedReader myBufferedR = new BufferedReader(new InputStreamReader(myIS));
                String myInput;
                StringBuffer myResult = new StringBuffer();

                if(myBufferedR != null) {
                    while ((myInput = myBufferedR.readLine()) != null) {
                        myResult.append(myInput + "n");
                    }
                } else {
                    return null;
                }
                return myResult.toString();
            } else {
                return String.valueOf(responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * A URLConnection with support for HTTP-specific features.
     * almost copy from http://developer.android.com
     * @param theURL is the String of URL passed in
     * @return the connection
     */
    public HttpURLConnection connect(String theURL) {
        try {
            URL url = new URL(theURL);
            HttpURLConnection connect= (HttpURLConnection) url.openConnection();

            connect.setRequestMethod("POST");
            connect.setConnectTimeout(10000);
            connect.setReadTimeout(10000);
            connect.setDoInput(true);
            connect.setDoOutput(true);
            return connect;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * invoked on the UI thread before the task is executed
     * @param s the string passed in
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        myListView.setAdapter(null);
        if (s != null) {
            // thats mean internet is work
            if (!s.contains("null")) {
                Parser p = new Parser(myCon, s, myListView, myClass);
                p.execute();
            }else {
                // thats mean no data match
                // add text later?
            }
        }else {
            // thats mean no internet
            // add text later?
        }
    }

    /**
     * AsyncTask enables proper and easy use of the UI thread
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
    protected String doInBackground(Void... params) {
        return transport();
    }
}