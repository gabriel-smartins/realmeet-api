package br.com.sw2you.realmeet.core;

import br.com.sw2you.realmeet.Application;
import br.com.sw2you.realmeet.api.ApiClient;
import br.com.sw2you.realmeet.config.TestClientConfiguration;
import java.net.MalformedURLException;
import java.net.URL;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import; //
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@Import(TestClientConfiguration.class)
public abstract class BaseIntegrationTest {

    @Autowired
    private Flyway flyway;

    @Autowired
    private ApiClient apiClient;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void setup() throws Exception {
        setupFlyway();
        setupClient();
        setupEach();
    }

    protected void setupEach() throws Exception {}

    private void setupClient() throws MalformedURLException {
        setLocalHostBasePath(apiClient, "/v1");
    }

    protected void setLocalHostBasePath(ApiClient apiClient, String path) throws MalformedURLException {
        apiClient.setBasePath(new URL("http", "localhost", serverPort, path).toString());
    }

    private void setupFlyway() {
        flyway.clean();
        flyway.migrate();
    }
}