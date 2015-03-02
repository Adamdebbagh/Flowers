package tekwin.org.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tekwin.org.model.Flower;

/**
 * Created by adamdebbagh on 3/1/15.
 */
public class FlowerJSONParser {


    public static List<Flower> parseFeed(String content) {
        List<Flower> flowerList = new ArrayList<>();

        try {
            JSONArray ar = new JSONArray(content);


            for (int i = 0; i <ar.length() ; i++) {
                JSONObject obj = ar.getJSONObject(i);
                Flower flower = new Flower();

                flower.setProductId(obj.getInt("productId"));
                flower.setName(obj.getString("name"));
                flower.setPrice(obj.getDouble("price"));
                flower.setPhoto(obj.getString("photo"));
                flower.setInstructions(obj.getString("instructions"));
                flower.setCategory(obj.getString("category"));

                flowerList.add(flower);
            }
            return flowerList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
