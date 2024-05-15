package com.uber.booking.utils;

public class BookingConstants {
    public static final String BASE_URL = "http://localhost";

    public static final String BASE_API_URL = "/api/v1";

    public static final String LOCATION_SERVICE_URL = BASE_URL + ":9000" + BASE_API_URL + "/location";

    public static final String NEARBY_DRIVER_API_URL = LOCATION_SERVICE_URL + "/nearby";
}
