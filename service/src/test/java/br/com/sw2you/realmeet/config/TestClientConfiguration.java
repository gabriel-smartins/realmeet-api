package br.com.sw2you.realmeet.config;

import br.com.sw2you.realmeet.api.ApiClient;
import br.com.sw2you.realmeet.api.facade.AllocationApi;
import br.com.sw2you.realmeet.api.facade.RoomApi;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestClientConfiguration {

    @Bean
    public ApiClient apiClient() {
        return new ApiClient();
    }

    @Bean
    public RoomApi roomApi(ApiClient apiClient) {
        return new RoomApi(apiClient);
    }

    @Bean
    public AllocationApi allocationApi(ApiClient apiClient) {
        return new AllocationApi(apiClient);
    }
}