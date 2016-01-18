package model;

import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.SimpleXmlConverterFactory;

/**
 * Created by DEV4LIFE on 1/13/16.
 */
public class RestAdapterUtil {
//    public static <T> T createXmlAdapterFor(final Class<T> api, final String endpoint, final Client client) {
//        Preconditions.checkNotNull(client);
//        Preconditions.checkNotNull(endpoint);
//        //
//        final RestAdapter.LogLevel level = getLogLevel();
//        Log.d(LOG_TAG, "Log Level:" + level);
//        final RestAdapter adapter = new RestAdapter.Builder()//
//                .setEndpoint(endpoint)//
//                .setConverter(new SimpleXMLConverter())//
//                .setClient(client)//
//                .setLogLevel(level)//
//                .build();
//        //
//        return adapter.create(api);
//    }

    public static <T> T createXmlAdapterFor(final Class<T> api,final String url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        return retrofit.create(api);
    }
}



