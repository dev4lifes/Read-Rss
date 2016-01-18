package ulti;

import android.app.ProgressDialog;
import android.content.Context;

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

/**
 * Created by DEV4LIFE on 1/16/16.
 */
public class HandleData {
    public List<NewsItem> load(Context context, final ProgressDialog progressDialog, String messProgress,
                               final ObjectNewspaper _objectNewspaper,
                               int _position) {
        final List<NewsItem> newsItems = new ArrayList<>();
        progressDialog.show(context, null, messProgress, true);
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
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        return newsItems;
    }

}
