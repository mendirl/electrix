package io.mend.electrix.ingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class IngestionApplication {
    static void main(String[] args) {
        SpringApplication.run(IngestionApplication.class, args);
    }
}
