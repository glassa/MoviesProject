package glassa.tacoma.uw.edu.moviesproject.follow_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Tony on 12/3/2016.
 */

public class MatchesItem implements Serializable{

    private static final String MATCHES = "Matches", DIFFERENCES = "Differences";
    private int matches, differences;

    public MatchesItem(int matches, int differences) {

        this.matches = matches;
        this.differences = differences;
    }


    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getDifferences() {
        return differences;
    }

    public void setDifferences(int differences) {
        this.differences = differences;
    }

    public static String parseMatchJSON(String matchJSON, int[] fItemList) {
        String reason = null;
        if (matchJSON != null) {
            try {
                JSONArray arr = new JSONArray(matchJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    MatchesItem fItem = new MatchesItem(obj.getInt(MatchesItem.MATCHES), obj.getInt(MatchesItem.DIFFERENCES));

                    fItemList[0] = fItem.getMatches();
                    fItemList[1] = fItem.getDifferences();
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }
}
