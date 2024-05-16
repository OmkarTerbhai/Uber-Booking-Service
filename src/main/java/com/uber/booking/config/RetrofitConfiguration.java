package com.uber.booking.config;

import com.netflix.discovery.EurekaClient;
import com.uber.booking.apispec.LocationServiceAPISpec;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfiguration {

    @Autowired
    private EurekaClient eurekaClient;

    public String getServiceUrl(String serviceName) {
        return eurekaClient.getNextServerFromEureka(serviceName, false).getHomePageUrl();
    }

    @Bean
    public LocationServiceAPISpec locationServiceAPISpec() {
        return new Retrofit.Builder()
                .baseUrl(getServiceUrl("LOCATION"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(LocationServiceAPISpec.class);
    }

}
