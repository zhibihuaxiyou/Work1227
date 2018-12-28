package com.bwie.work1227.netWork;

import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author : 张腾
 * @date : 2018/12/27.
 * desc :
 */
public class RetrofitManager<T> {

    private final String BASE_URL = "http://www.zhaoapi.cn/";

    private static RetrofitManager manager;

    //单例模式
    public static synchronized RetrofitManager getManager() {
        if (manager == null) {
            manager = new RetrofitManager();
        }
        return manager;
    }

    private BaseApis mBaseApis;

    public RetrofitManager() {
        //拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        mBaseApis = retrofit.create(BaseApis.class);
    }

    /**
     * 可以这样生成Map<String, RequestBody> requestBodyMap
     * Map<String, String> requestDataMap这里面放置上传数据的键值对。
     */
    public Map<String, RequestBody> getRequestBody(Map<String, String> requestBodyMap) {
        Map<String, RequestBody> requestBody = new HashMap<>();
        for (String key : requestBodyMap.keySet()) {
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestBodyMap.get(key) == null ? "" : requestBodyMap.get(key));
            requestBody.put(key, body);
        }
        return requestBody;
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public RetrofitManager get(String url) {
        mBaseApis.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
        return manager;
    }


    /**
     * post请求
     *
     * @param url
     * @param map
     * @return
     */
    public RetrofitManager post(String url, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        mBaseApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
        return manager;
    }

    /**
     * 表单 post 请求
     *
     * @param url
     * @param map
     * @return
     */
    public RetrofitManager postFormBody(String url, Map<String, RequestBody> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        mBaseApis.postFormBody(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
        return manager;
    }

    private Observer mObserver = new Observer<ResponseBody>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (mHttpListener != null) {
                mHttpListener.onFailed(e.getMessage());
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String data = responseBody.string();
                if (mHttpListener != null) {
                    mHttpListener.onSuccess(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (mHttpListener != null) {
                    mHttpListener.onFailed(e.getMessage());
                }
            }
        }
    };


    //声明接口
    private HttpListener mHttpListener;

    //set方法
    public void setHttpListener(HttpListener httpListener) {
        this.mHttpListener = httpListener;
    }

    //自定义接口
    public interface HttpListener {
        void onSuccess(String data);

        void onFailed(String error);
    }
}
