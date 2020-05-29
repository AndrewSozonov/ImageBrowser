package ru.andreysozonov.imagebrowser.model.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.andreysozonov.imagebrowser.model.entity.Photo;

public class ApiHelper {

    public static final String URL = "https://pixabay.com";

    public Observable<Photo> requestServer(String theme, int page) {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson);
        IApiService api = new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build()
                .create(IApiService.class);

        return api.getPhoto("16206109-f16a40afa7abb625dc8d7d2ae", theme, page).subscribeOn(Schedulers.io());
    }
}
