package com.example.SE1719_SE172258_ducthse172258.api.Student;

import com.example.SE1719_SE172258_ducthse172258.models.Student;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentService {
    String END_POINT = "Student";

    @GET(END_POINT)
    Call<Student[]> getAllStudent();

    @GET(END_POINT + "/{id}")
    Call<Student> getOneStudent(@Path("id") Object id);

    @POST(END_POINT)
    Call<Student> createStudent(@Body Student student);

    @PUT(END_POINT + "/{id}")
    Call<Student> updateStudent(@Path("id") Object id, @Body Student student);

    @DELETE(END_POINT + "/{id}")
    Call<Student> deleteStudent(@Path("id") Object id);

}
