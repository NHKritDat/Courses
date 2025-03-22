package com.example.SE1719_SE172258_ducthse172258.api.Major;

import com.example.SE1719_SE172258_ducthse172258.api.ApiClient;

public class MajorRepository {
    public static MajorService getMajorService() {
        return ApiClient.getRetrofit().create(MajorService.class);
    }
}
