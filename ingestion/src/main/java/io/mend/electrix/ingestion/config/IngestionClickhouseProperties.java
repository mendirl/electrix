package io.mend.electrix.ingestion.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ingestion.clickhouse")
public record IngestionClickhouseProperties(
  String url,
  String user,
  String password
) {
}
