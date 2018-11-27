package cs4540.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cs4540.newsapp.model.NewsItem;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder> {
    Context mContext;
    ArrayList<NewsItem> mNews;

    public NewsAdapter(Context context, ArrayList<NewsItem> mNews) {
        this.mContext = context;
        this.mNews = mNews;
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    void setNewsItems(List<NewsItem> news) {
        mNews = new ArrayList(news);
        notifyDataSetChanged();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView img;


        public NewsItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            img = (ImageView) itemView.findViewById(R.id.img);
        }

        void bind(final int listIndex) {
            title.setText(mNews.get(listIndex).getTitle());
            description.setText(mNews.get(listIndex).getPublishedAt() + ". " + (mNews.get(listIndex).getDescription()));
            String url = mNews.get(listIndex).getUrlToImage();
            if (url != null) {
                Picasso.get()
                        .load(url)
                        .into(img);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String urlString = mNews.get(listIndex).getUrl();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                    //Intent browserChooserIntent = Intent.createChooser(browserIntent, "Choose browser of your choice");
                    mContext.startActivity(browserIntent);
                }
            });
        }
    }
}
