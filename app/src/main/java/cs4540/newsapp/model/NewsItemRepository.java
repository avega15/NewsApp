package cs4540.newsapp.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cs4540.newsapp.utilities.JsonUtils;
import cs4540.newsapp.utilities.NetworkUtils;

import static cs4540.newsapp.utilities.NetworkUtils.buildUrl;

public class NewsItemRepository {
    private static NewsItemDAO mNewsItemDAO;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemRepository(Application application) {
        NewsItemDatabase db = NewsItemDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDAO = db.newsItemDao();
        mAllNewsItems = mNewsItemDAO.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNewsItems() {
        return mAllNewsItems;
    }

    public static void syncNews() {
        new SyncNewsTask(mNewsItemDAO).execute(buildUrl());
    }

    public static class SyncNewsTask extends AsyncTask<URL, Void, String> {
        private NewsItemDAO mAsyncTaskDao;

        SyncNewsTask(NewsItemDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mAsyncTaskDao.clearAll();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String newsSearchResults = "";
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("mycode", s);
            super.onPostExecute(s);
            ArrayList<NewsItem> news = JsonUtils.parseNews(s);
            mAsyncTaskDao.insert(news);
        }
    }
}
