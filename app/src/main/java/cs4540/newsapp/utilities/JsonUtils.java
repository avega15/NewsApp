package cs4540.newsapp.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cs4540.newsapp.model.NewsItem;

public class JsonUtils {

    public static ArrayList<NewsItem> parseNews(String JSONString) {
        ArrayList<NewsItem> repoList = new ArrayList<>();
        try {
            JSONObject jObject = new JSONObject(JSONString);
            JSONArray items = jObject.getJSONArray("articles");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                repoList.add(new NewsItem(item.getString("author"),
                        item.getString("title"),
                        item.getString("description"),
                        item.getString("url"),
                        item.getString("urlToImage"),
                        item.getString("publishedAt")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return repoList;
    }
}