package glassa.tacoma.uw.edu.moviesproject;


import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

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
    private final static String USER_LOGIN_URL = "http://cssgate.insttech.washington.edu/~_450team2/login.php?";
    private final static String USER_ADD_URL = "http://cssgate.insttech.washington.edu/~_450team2/addUser.php?";
    private LoginFragment.LoginAddListener mListener;
    private LoginFragment.FacebookLoginListener fListener;
    private final static String TAG = "LoginFragment";
    /**
     * The B 1.
     */
    Button b1, /**
     * The B 2.
     */
    b2;
    /**
     * The Ed 1.
     */
    EditText ed1, /**
     * The Ed 2.
     */
    ed2;
    /**
     * The Tx 1.
     */
    TextView tx1, info;

    /**
     * The Counter.
     */
    int counter = 3;

    LoginButton loginButton;

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        loginButton = (LoginButton) v.findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                /*Context c = getActivity();
                StringBuilder sb = new StringBuilder(USER_ADD_URL);
                StringBuilder sb2 = new StringBuilder("");
                try {

                    String userFName = Profile.getCurrentProfile().getFirstName();
                    String userLName = Profile.getCurrentProfile().getLastName();
                    sb2.append(userFName + " ");
                    sb2.append(userLName);
                    String userName = sb2.toString();
                    sb.append("&Username=");
                    sb.append(URLEncoder.encode(userName, "UTF-8"));

                    MainActivity m = (MainActivity) getActivity();
                    m.setmUsername(userName);

                    String userPW = Profile.getCurrentProfile().getId();
                    sb.append("&Passcode=");
                    sb.append(URLEncoder.encode(userPW, "UTF-8"));



                    Log.i("UserAdd", sb.toString());

                }
                catch(Exception e) {
                    Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
                String url = sb.toString();
                mListener.addUser(url);
*/

                Log.i(TAG, "registerCallback");

                fListener.facebookLogin();
            }

            @Override
            public void onCancel() {
                Context c = getActivity();
                Toast.makeText(c, "Login attempt Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Context c = getActivity();
                Toast.makeText(c, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        b1 = (Button) v.findViewById(R.id.login_button);
        ed1 = (EditText) v.findViewById(R.id.login_edit_text);
        ed2 = (EditText) v.findViewById(R.id.pass_edit_text);

        b2 = (Button) v.findViewById(R.id.register_button_initial);
        tx1 = (TextView) v.findViewById(R.id.attempts_text_view_2);
        tx1.setVisibility(View.GONE);
        info = (TextView) v.findViewById(R.id.info);
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
                    /*
                    Toast.makeText(c, "Wrong Credentials", Toast.LENGTH_SHORT).show();

                    tx1.setVisibility(View.VISIBLE);
                    tx1.setBackgroundColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));


                    if (counter == 0) {
                        b1.setEnabled(false);
                        */

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

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof LoginFragment.LoginAddListener) {
            mListener = (LoginFragment.LoginAddListener) context;
            fListener = (LoginFragment.FacebookLoginListener) context;
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
