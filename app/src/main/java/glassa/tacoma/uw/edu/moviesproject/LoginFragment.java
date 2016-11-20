package glassa.tacoma.uw.edu.moviesproject;


import android.content.Context;
import android.os.Bundle;
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

import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    public interface LoginAddListener{
        public void addLogin(String url);

    }
    private final static String USER_LOGIN_URL = "http://cssgate.insttech.washington.edu/~_450team2/login.php?";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private LoginFragment.LoginAddListener mListener;

    String mUsername;
    Button b1, b2;
    EditText ed1, ed2;
    TextView tx1;
    int counter = 3;

    public LoginFragment() {
        // Required empty public constructor
    }

    public String getmUsername() {
        return mUsername;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        b1 = (Button) v.findViewById(R.id.button);
        ed1 = (EditText) v.findViewById(R.id.editText);
        ed2 = (EditText) v.findViewById(R.id.editText2);

        b2 = (Button) v.findViewById(R.id.button2);
        tx1 = (TextView) v.findViewById(R.id.textView3);
        tx1.setVisibility(View.GONE);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = getActivity();
                String url = buildUserURL(v);
                if(url != null) {
                    mListener.addLogin(url);
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
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LoginAddListener");
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
