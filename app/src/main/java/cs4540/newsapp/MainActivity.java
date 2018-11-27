package cs4540.newsapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cs4540.newsapp.model.NewsItem;
import cs4540.newsapp.model.NewsItemViewModel;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private NewsItemViewModel mNewsItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.news_recyclerview);
        mContext = this;
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNewsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        mNewsItemViewModel.getAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable final List<NewsItem> newsItems) {
                mAdapter = new NewsAdapter(mContext, new ArrayList<NewsItem>(newsItems));
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(layoutManager);
            }
        });
        ScheduleUtilities.scheduleRefresh(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            mNewsItemViewModel.syncNews();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.get_news, menu);
        return true;
    }
}
