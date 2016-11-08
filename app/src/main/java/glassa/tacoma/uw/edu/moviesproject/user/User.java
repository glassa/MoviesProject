package glassa.tacoma.uw.edu.moviesproject.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aglas on 11/7/2016.
 */

public class User implements Serializable{
    String mUserID, mUserName, mUserPass;
    public static final String ID = "id", USER_NAME = "name", USER_PASS = "pass";

    public User(String mUserID, String mUserName, String mUserPass) {
        this.mUserID = mUserID;
        this.mUserName = mUserName;
        this.mUserPass = mUserPass;
    }

    public String getmUserID() {
        return mUserID;
    }

    public void setmUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserPass() {
        return mUserPass;
    }

    public void setmUserPass(String mUserPass) {
        this.mUserPass = mUserPass;
    }
    public static String parseUserJSON(String userJSON, List<User> userList){
        String reason = null;
        if (userJSON != null) {
            try {
                JSONArray arr = new JSONArray(userJSON);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    User user = new User(obj.getString(User.ID), obj.getString(User.USER_NAME), obj.getString(User.USER_PASS));
                    userList.add(user);
                }
            } catch (JSONException e) {
                reason = "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }
}
