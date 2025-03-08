package com.example.lab_11.model.api;

import com.example.lab_11.APIClient;

public class TraineeRepository {
    public static TraineeService getTraineeService() {
        return APIClient.getClient().create(TraineeService.class);
    }
}
