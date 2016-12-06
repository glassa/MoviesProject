package glassa.tacoma.uw.edu.moviesproject;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    private messageListener mListener;
    EditText ed1;
    EditText ed2;
    public interface messageListener {
        public void message();
    }
    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_message, container, false);
        Button startBtn = (Button) v.findViewById(R.id.send_button);
        ed1 = (EditText) v.findViewById(R.id.To_ET);
        //ed2 = (EditText) v.findViewById(R.id.cc_ET);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendMessage();
            }
        });
        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof messageListener) {
            mListener = (messageListener) context;
        }
    }
    protected void sendMessage() {
        Log.i("Send message", "");
        String number =ed1.getText().toString();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }
}




