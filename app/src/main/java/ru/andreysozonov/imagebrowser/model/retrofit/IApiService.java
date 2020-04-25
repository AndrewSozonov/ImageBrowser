package ru.andreysozonov.imagebrowser.model.retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.andreysozonov.imagebrowser.model.entity.Photo;

public interface IApiService {

    @GET("api")
    Observable<Photo> getPhoto(@Query("key") String key, @Query("q") String theme );
}
