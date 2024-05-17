package com.uber.booking.apispec;

import com.uber.booking.dto.RequestDriverDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DriverFinderServiceSpec {

    @POST("/api/v1/driver")
    Call<Boolean> requestDriver(@Body RequestDriverDTO dto);
}
