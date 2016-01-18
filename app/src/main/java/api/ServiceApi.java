package api;

import model.Rss;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by DEV4LIFE on 1/12/16.
 */
public interface ServiceApi{

    @GET("{content}")
    Call<Rss> getChannel(@Path("content") CharSequence content);
}
