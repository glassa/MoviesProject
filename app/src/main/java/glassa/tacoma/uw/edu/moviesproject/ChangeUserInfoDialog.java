package glassa.tacoma.uw.edu.moviesproject;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferenceEntry;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeUserInfoDialog extends DialogFragment {
    EditText ed1, ed2;
    String mUsername;
    String url = "http://cssgate.insttech.washington.edu/~_450team2/userChange.php?";
    String TAG = "ChangeUserInfoDialog";

    public ChangeUserInfoDialog() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.fragment_change_user_info_dialog, null);
        ed1 = (EditText) v.findViewById(R.id.userbox);
        ed2 = (EditText) v.findViewById(R.id.passbox);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_change_user_info_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String mUsername = ed1.getText().toString();
                        String mPass = ed2.getText().toString();
                        String url = buildUserChangeURL(v);
                        ChangeUserTask task = new ChangeUserTask();
                        task.execute(new String[]{url.toString()});


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }

    private String buildUserChangeURL(View v) {

        StringBuilder sb = new StringBuilder(url);

        try {

            String userName = ed1.getText().toString();
            sb.append("&Username=");
            sb.append(URLEncoder.encode(userName, "UTF-8"));

            MainActivity m = (MainActivity) getActivity();
            m.setmUsername(userName);

            String userPW = ed2.getText().toString();
            sb.append("&Passcode=");
            sb.append(URLEncoder.encode(userPW, "UTF-8"));


            Log.i("UserChange", sb.toString());

        } catch (Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    private class ChangeUserTask extends AsyncTask<String, Void, String> {


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
         * If it was, they are saved as logged in via Sharedpreferences, the username is stored in
         * a SQlite database, and the user is taken to the TabHostActivity.
         * If not, it displays and logs the exception.
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
                    Toast.makeText(getApplicationContext(), "User successfully changed!"
                            , Toast.LENGTH_LONG)
                            .show();


                } else {
                    Toast.makeText(getApplicationContext(), "Failed to change: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
                Log.wtf(TAG, e.getMessage());
            }
        }
    }
}
