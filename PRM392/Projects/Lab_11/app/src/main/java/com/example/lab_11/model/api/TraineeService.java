package com.example.lab_11.model.api;

import com.example.lab_11.model.Trainee;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TraineeService {
    String TRAINEES = "users";

    @GET(TRAINEES)
    Call<Trainee[]> getAll();

    @GET(TRAINEES + "/{id}")
    Call<Trainee> getById(@Path("id") Object id);

    @POST(TRAINEES)
    Call<Trainee> create(@Body Trainee trainee);

    @PUT(TRAINEES + "/{id}")
    Call<Trainee> updateTrainee(@Path("id") Object id, @Body Trainee trainee);

    @DELETE(TRAINEES + "/{id}")
    Call<Trainee> delete(@Path("id") Object id);
}
