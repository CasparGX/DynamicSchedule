package cc.xaabb.dynamicschedule.network;

import cc.xaabb.dynamicschedule.config.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by caspar on 16-9-12.
 */
public class RetrofitUtils {

    private Retrofit mRetrofit;
    private static RetrofitUtils retrofitUtils;


    //private final String baseUrl = "https://api.douban.com/v2/movie/";
    private final String baseUrl = Constants.Api.URL    ;

    private RetrofitUtils(){
        if (mRetrofit==null) {
            init();
        }
    }

    private void init() {

        OkHttpClient httpClient = new OkHttpClient();
//        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
//            OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
//            mBuilder.networkInterceptors().add(logging);
//            httpClient = mBuilder.build();
//        }

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
    }

    public static RetrofitUtils getInstance() {
        if (retrofitUtils == null) {
            synchronized (RetrofitUtils.class) {
                if (retrofitUtils == null) {
                    retrofitUtils = new RetrofitUtils();
                }
            }
        }
        return retrofitUtils;
    }

    public Retrofit getmRetrofit() {
        return mRetrofit;
    }
}
