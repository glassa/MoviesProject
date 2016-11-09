package glassa.tacoma.uw.edu.moviesproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    public interface UserAddListener {
        public void addUser(String url);
    }

    private final static String USER_ADD_URL = "http://cssgate.insttech.washington.edu/~_450team2/addUser.php?";
    public RegisterFragment() {
        // Required empty public constructor
    }

    Button b1, b2;
    EditText ed1, ed2, ed3;
    private RegisterFragment.UserAddListener mListener;
    //TextView tx1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        b1 = (Button) v.findViewById(R.id.buttonReg);
        //b2 = (Button) v.findViewById(R.id.buttonRegCancel);
        ed1 = (EditText) v.findViewById(R.id.editRegisterText);
        ed2 = (EditText) v.findViewById(R.id.editRegisterText2);
        ed3 = (EditText) v.findViewById(R.id.editRegisterText3) ;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = getActivity();
                if (ed2.getText().toString().equals(ed3.getText().toString())) {
                    String url = buildUserURL(v);
                    mListener.addUser(url);

                    Toast.makeText(c, "Registering...", Toast.LENGTH_SHORT).show();
                    Intent Tonyintent = new Intent(c, TabHostActivity.class);
                    startActivity(Tonyintent);

                } else {
                    Toast.makeText(c, "Passwords do not match", Toast.LENGTH_SHORT).show();


                }

            }
        });

        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof UserAddListener) {
            mListener = (UserAddListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CourseAddListener");
        }
    }
    private String buildUserURL(View v) {

        StringBuilder sb = new StringBuilder(USER_ADD_URL);

        try {

            String userName = ed1.getText().toString();
            sb.append("&Username=");
            sb.append(URLEncoder.encode(userName, "UTF-8"));


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
