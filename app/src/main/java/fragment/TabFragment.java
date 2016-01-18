package fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.dev4life.baovietyet.MyNavigationDrawer;
import net.dev4life.baovietyet.R;

import java.util.ArrayList;
import java.util.List;

import api.ServiceApi;
import model.Item;
import model.NewsItem;
import model.ObjectNewspaper;
import model.RestAdapterUtil;
import model.Rss;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import ulti.ItemClickSupport;
import webview.WebViewActivity;

/**
 * Created by DEV4LIFE on 1/13/16.
 */
public class TabFragment extends Fragment {
    private final String ARG_POSITION = "position";

    private final String ARG_PACK_TRANFER_NEWS = "pack transfer news";
    public final String LINK = "link";

    private int position;

    private ObjectNewspaper objectNewspaper;

    private MyAdapter myAdapter;
    private List<NewsItem> newsItems = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;

    private ProgressDialog progressDialog;
    public TabFragment() {

    }

    public TabFragment newInstance(ObjectNewspaper objectNewspaper,
                                   int _position) {
        TabFragment f = new TabFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARG_PACK_TRANFER_NEWS, objectNewspaper);
        b.putInt(ARG_POSITION, _position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myAdapter = new MyAdapter(newsItems, getContext());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
        position = getArguments().getInt(ARG_POSITION);
        objectNewspaper = (ObjectNewspaper) getArguments().getSerializable(ARG_PACK_TRANFER_NEWS);
        Log.i("baseurl: ", objectNewspaper.getUrlPaper());
        //todo: get newItems from web by Retrofit
        loadData(objectNewspaper, position);
    }


    private void loadData(final ObjectNewspaper _objectNewspaper, int _position) {
        if (newsItems.size() > 0) {
            newsItems.clear();
        }
        progressDialog = ProgressDialog.show(getContext(), null, getString(R.string.progress_message), true);
        progressDialog.setCancelable(true);
        ServiceApi api = RestAdapterUtil.createXmlAdapterFor(ServiceApi.class, _objectNewspaper.getUrlPaper());
        Call<Rss> call = api.getChannel(_objectNewspaper.getTitleRssPaper()[_position]);
        call.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Response<Rss> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    List<Item> items = response.body().getChannel().getItems();
                    for (Item item :
                            items) {
                        String avatar = _objectNewspaper.getHandRss().getLinkImageFromOriginDes(item.getDescription());
                        String des = _objectNewspaper.getHandRss().getDescriptionFromOriginDes(item.getDescription());
                        newsItems.add(new NewsItem(
                                avatar,
                                item.getTitle(),
                                item.getLink(),
                                item.getPubDate(),
                                des
                        ));
                    }
                    progressDialog.dismiss();
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_restart:
                Intent mStartActivity = new Intent(getContext(), MyNavigationDrawer.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(getContext(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                System.exit(0);
                break;
        }
        return true;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newsItems.clear();
        myAdapter.notifyDataSetChanged();
    }



    private void removeAllNewsItem(List<NewsItem> newsItems) {
        for (int i = 0; i < newsItems.size(); i++) {
            newsItems.remove(i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(objectNewspaper, position);
                refreshLayout.setRefreshing(false);
                myAdapter.notifyDataSetChanged();
            }
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        myAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(myAdapter);

        //Handle Item Click in RecycleView
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // do it
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(LINK, newsItems.get(position).getLink());
                startActivity(intent);
            }
        });

        return view;
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<NewsItem> newsItems;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cardView;

        ImageView avatar;
        TextView title;
        TextView pubDate;
        TextView description;

        public ViewHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.cv);
            avatar = (ImageView) v.findViewById(R.id.avatar);
            title = (TextView) v.findViewById(R.id.title);
            pubDate = (TextView) v.findViewById(R.id.date);
            description = (TextView) v.findViewById(R.id.description);
        }
    }

    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<NewsItem> items, Context context) {
        newsItems = items;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        String url = newsItems.get(position).getAvatar();
        if(url.equals("")){
            url = "R.drawable.no_image_avatar_150px";
        }

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.no_image_avatar_150px)
                .error(R.drawable.no_image_avatar_150px)
                .into(holder.avatar);
        holder.title.setText(newsItems.get(position).getTitle());
        holder.pubDate.setText(newsItems.get(position).getPubDate());
        holder.description.setText(newsItems.get(position).getDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return newsItems.size();
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
