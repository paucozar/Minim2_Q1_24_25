package edu.upc.projecte;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.List;


public interface ApiService {
    @POST("users")
    Call<Void> registerUser(@Body User user);

    @POST("users/login")
    Call<Void> loginUser(@Body User user);
}
