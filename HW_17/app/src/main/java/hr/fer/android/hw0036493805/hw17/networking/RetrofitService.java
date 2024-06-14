package hr.fer.android.hw0036493805.hw17.networking;

import hr.fer.android.hw0036493805.hw17.models.UserProfile;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * REST API for this app.
 *
 * @author Karlo Bencic
 */
public interface RetrofitService {

    /**
     * Gets the user using the relative url path to json data
     *
     * @param url relative url path
     * @return UserProfile
     */
    @GET("ba3s/{url}")
    Call<UserProfile> getUser(@Path("url") String url);
}
