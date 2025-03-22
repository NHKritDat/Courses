package com.example.SE1719_SE172258_ducthse172258.api.Student;

import com.example.SE1719_SE172258_ducthse172258.api.ApiClient;

public class StudentRepository {
    public static StudentService getStudentService() {
        return ApiClient.getRetrofit().create(StudentService.class);
    }
}
