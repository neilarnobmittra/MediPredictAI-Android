package com.medipredict.ai.api;

import com.medipredict.ai.models.AuthResponse;
import com.medipredict.ai.models.PredictionResult;
import com.medipredict.ai.models.SymptomResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/login")
    Call<AuthResponse> login(@Body Map<String, String> body);

    @POST("api/register")
    Call<AuthResponse> register(@Body Map<String, String> body);

    @POST("api/predict")
    Call<PredictionResult> predictVitals(
            @Header("Authorization") String token,
            @Body Map<String, Object> body
    );

    @POST("api/predict-symptoms")
    Call<SymptomResponse> predictSymptoms(
            @Header("Authorization") String token,
            @Body Map<String, Object> body
    );

    @GET("api/history")
    Call<List<PredictionResult>> getHistory(@Header("Authorization") String token);
}
