package glassa.tacoma.uw.edu.moviesproject;

        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.AsyncTask;
        import android.os.Bundle;

        import android.support.v7.app.AppCompatActivity;
        import android.view.View;

        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;

public class MainActivity extends AppCompatActivity implements RegisterFragment.UserAddListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.fragment_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegisterFragment registerFragment = new RegisterFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, registerFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    @Override
    public void addUser(String url) {

        AddUserTask task = new AddUserTask();
        task.execute(new String[]{url.toString()});
        getSupportFragmentManager().popBackStack();
    }

    public void addLogin(String url) {
        LoginUserTask task = new LoginUserTask;
        task.execute(new String[]{url.toString()});
        getSupportFragmentManager().popBackStack();
    }

    private class LoginUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


    @Override
    protected String doInBackground(String... urls) {
        String response = "";
        HttpURLConnection urlConnection = null;
        for (String url : urls) {
            try {
                URL urlObject = new URL(url);
                urlConnection = (HttpURLConnection) urlObject.openConnection();

                InputStream content = urlConnection.getInputStream();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }

            } catch (Exception e) {
                response = "Unable to add user, Reason: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
        }
        return response;
    }

    /**
     * It checks to see if there was a problem with the URL(Network) which is when an
     * exception is caught. It tries to call the parse Method and checks to see if it was successful.
     * If not, it displays the exception.
     *
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {
        // Something wrong with the network or the URL.
        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = (String) jsonObject.get("result");
            if (status.equals("success")) {
                Toast.makeText(getApplicationContext(), "User successfully added!"
                        , Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to add: "
                                + jsonObject.get("error")
                        , Toast.LENGTH_LONG)
                        .show();
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                    e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

    private class AddUserTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add user, Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "User successfully added!"
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}