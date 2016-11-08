package glassa.tacoma.uw.edu.moviesproject;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    public interface LoginAddListener{

    }
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    //private  mListener;

    Button b1, b2, b3;
    EditText ed1, ed2;

    TextView tx1;
    int counter = 3;

    public LoginFragment() {
        // Required empty public constructor
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
        b3 = (Button) v.findViewById(R.id.button3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = getActivity();
                if (ed1.getText().toString().equals("admin") &&

                        ed2.getText().toString().equals("admin")) {
                    Toast.makeText(c, "Redirecting...", Toast.LENGTH_SHORT).show();
                    Intent Tonyintent = new Intent(c, TabHostActivity.class);
                    startActivity(Tonyintent);

                } else {
                    Toast.makeText(c, "Wrong Credentials", Toast.LENGTH_SHORT).show();

                    tx1.setVisibility(View.VISIBLE);
                    tx1.setBackgroundColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        b1.setEnabled(false);
                    }
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = getActivity();
                c.finish();
            }
        });

    }

}
