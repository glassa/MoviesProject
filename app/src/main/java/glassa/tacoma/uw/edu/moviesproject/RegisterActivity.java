package glassa.tacoma.uw.edu.moviesproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    private final static String USER_ADD_URL
            = "http://cssgate.insttech.washington.edu/~glassa/Android/registerUser.php?";

    Button b1, b2;
    EditText ed1, ed2, ed3;
    private UserAddListener mListener;
    //TextView tx1;
    public interface UserAddListener {
        public void addUser(String url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        b1 = (Button) findViewById(R.id.buttonReg);
        b2 = (Button) findViewById(R.id.buttonRegCancel);
        ed1 = (EditText) findViewById(R.id.editRegisterText);
        ed2 = (EditText) findViewById(R.id.editRegisterText2);
        ed3 = (EditText) findViewById(R.id.editRegisterText3) ;




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed2.getText().toString().equals(ed3.getText().toString())) {
                    String url = buildCourseURL(v);
                    mListener.addUser(url);
                   Toast.makeText(getApplicationContext(), "Registering...", Toast.LENGTH_SHORT).show();
                    Intent Tonyintent = new Intent(RegisterActivity.this, TonyActivity.class);
                    startActivity(Tonyintent);

                } else {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();


                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private String buildCourseURL(View v) {

        StringBuilder sb = new StringBuilder(USER_ADD_URL);

        try {

            String userId = ed1.getText().toString();
            sb.append("id=");
            sb.append(userId);


            String userName = ed1.getText().toString();
            sb.append("&name=");
            sb.append(URLEncoder.encode(userName, "UTF-8"));


            String userPW = ed2.getText().toString();
            sb.append("&pw=");
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



