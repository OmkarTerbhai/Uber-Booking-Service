package com.uber.booking.apispec;

import com.uber.booking.dto.NearbyDriversDto;
import com.uber.booking.dto.SaveDriverLocationDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LocationServiceAPISpec {

    @POST("/api/v1/location/nearby")
    Call<SaveDriverLocationDto[]> getNearByDrivers(@Body NearbyDriversDto dto);
}
