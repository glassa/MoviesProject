package glassa.tacoma.uw.edu.moviesproject.search;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * this class is data package class
 * Created by Yunhang on 2016/11/5.
 */
public class DataPack {
    String myQuery;

    public DataPack(String query) {
        myQuery = query;
    }

    public String packageData() {
        JSONObject Obj = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            Obj.put("Query", myQuery);

            Boolean firstValue = true;
            Iterator it = Obj.keys();

            do {
                String key=it.next().toString();
                String value=Obj.get(key).toString();
                if (firstValue) {
                    firstValue = false;
                } else {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key, "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } while (it.hasNext());
            return sb.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}