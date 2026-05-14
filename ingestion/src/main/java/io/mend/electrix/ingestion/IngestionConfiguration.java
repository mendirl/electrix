package io.mend.electrix.ingestion;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestClient;

@ConfigurationPropertiesScan
@EnableAsync
@Configuration
public class IngestionConfiguration {

  @Bean
  public RestClient restClient(RestClient.Builder builder) {
    return builder.build();
  }
}


