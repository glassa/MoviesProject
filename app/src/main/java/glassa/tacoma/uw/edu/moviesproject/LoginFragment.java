package glassa.tacoma.uw.edu.moviesproject;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import java.net.URLEncoder;

import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferenceEntry;
import glassa.tacoma.uw.edu.moviesproject.util.SharedPreferencesHelper;


/**
 * -------------------------------------------------------------------------
 * A fragment class to hold the login UI.                                   |
 * -------------------------------------------------------------------------
 * Contains 2 EditText views, one for the user name,                        |
 * and one for the password. If the database returns that the user          |
 * verified, the TabHostActivity is launched via the AsyncTask OnPostExecute|
 * method.                                                                  |
 * -------------------------------------------------------------------------
 * Contains two buttons.                                                    |
 *                                                                          |
 * Login Button:On click, calls a stringbuilder                             |
 * to build the appended url, and call the AsyncTask                        |
 *                                                                          |
 * Register Button:On click, launches the Register Fragment.                |
 * -------------------------------------------------------------------------
 * Conatains a stringbuilder that builds the url by taking                  |
 * the data out of the EditViews and appending it onto the url.             |
 * -------------------------------------------------------------------------
 */
public class LoginFragment extends Fragment {
    /**
     * The interface Login add listener.
     */
    public interface LoginAddListener{
        /**
         * Add login.
         *
         * @param url the url
         */
        public void addLogin(String url, String mUsername);
        public void addUser(String url);
        public void sharedPrefLogin(String mUsername);

    }
    public interface FacebookLoginListener{
        public void facebookLogin();
    }

    /**
     * The url we use to connect to the SQL database.
     */
    private final static String USER_LOGIN_URL =
            "http://cssgate.insttech.washington.edu/~_450team2/login.php?";
    /**
     * The url we use when registering a new user.
     * Currently inactive since we made registration it's own fragment
     */

    /**
     * The listener we use when sending a log in request
     */
    private LoginFragment.LoginAddListener mListener;
    /**
     * The listener we used when logging in via facebook. Currently inactive.
     */

    /**
     * A string to allow for easier logging.
      */
    private final static String TAG = "LoginFragment";
    /**
     * The login button
     */
    Button b1,
    /**
     * The register button.
     */
    b2,
    /**
     * The share this app button.
      */
    b3;
    /**
     * The username textbox
     */
    EditText ed1,
    /**
     * The password text box
     */
    ed2;


    //LoginButton loginButton;


    CallbackManager callbackManager;

    ProfileTracker profileTracker;
    protected static String mUsername;
    /**
     * Instantiates a new Login fragment.
     */
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context x = getActivity();
        FacebookSdk.sdkInitialize(x);
        callbackManager = CallbackManager.Factory.create();

        profileTracker = new ProfileTracker() {
            @Override
            public void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile
            ) {
                // App code
            }
        };

    }

    public void onBackPressed(){
        this.getActivity().onBackPressed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_login, container, false);
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(
                sharedPreferences);
        SharedPreferenceEntry entry = sharedPreferencesHelper.getLoginInfo();
        if (entry.isLoggedIn()){
            Log.i(TAG, "Shared Preferences found");
            mUsername = entry.getUsername();
            mListener.sharedPrefLogin(mUsername);
        }

        b1 = (Button) v.findViewById(R.id.login_button);
        ed1 = (EditText) v.findViewById(R.id.login_edit_text);
        ed2 = (EditText) v.findViewById(R.id.pass_edit_text);

        b2 = (Button) v.findViewById(R.id.register_button_initial);

        b3 = (Button) v.findViewById(R.id.share_button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = getActivity();
                String url = buildUserURL(v);
                if(url != null) {
                    mListener.addLogin(url, ed1.getText().toString());
                } else {
                    Toast.makeText(c, "Error building url", Toast.LENGTH_LONG).show();
                }

                }

            });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegisterFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        b3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment fragment = new MessageFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                /*
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "TO");
                emailIntent.putExtra(Intent.EXTRA_CC, "CC");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                    Log.i("Finished sending email", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }*/


        }});
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof LoginFragment.LoginAddListener) {
            mListener = (LoginFragment.LoginAddListener) context;

            //fListener = (LoginFragment.FacebookLoginListener) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LoginAddListener and FacebookLoginListener");
        }
    }

    private String buildUserURL(View v) {

        StringBuilder sb = new StringBuilder(USER_LOGIN_URL);

        try {

            String userName = ed1.getText().toString();
            sb.append("&Username=");
            sb.append(URLEncoder.encode(userName, "UTF-8"));

            MainActivity m = (MainActivity) getActivity();
            m.setmUsername(userName);

            String userPW = ed2.getText().toString();
            sb.append("&Passcode=");
            sb.append(URLEncoder.encode(userPW, "UTF-8"));



            Log.i("UserAdd", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }


}
