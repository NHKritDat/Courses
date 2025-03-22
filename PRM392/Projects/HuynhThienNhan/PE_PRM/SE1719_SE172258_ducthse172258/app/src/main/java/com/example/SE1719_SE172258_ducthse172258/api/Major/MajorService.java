package com.example.SE1719_SE172258_ducthse172258.api.Major;

import com.example.SE1719_SE172258_ducthse172258.models.Major;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MajorService {
    String END_POINT = "Major";

    @GET(END_POINT)
    Call<Major[]> getAllMajor();

    @POST(END_POINT)
    Call<Major> createMajor(@Body Major major);

    @GET(END_POINT + "/{id}")
    Call<Major> getOneMajor(@Path("id") Object id);

    @PUT(END_POINT + "/{id}")
    Call<Major> updateMajor(@Path("id") Object id, @Body Major major);

    @DELETE(END_POINT + "/{id}")
    Call<Major> deleteMajor(@Path("id") Object id);
}
