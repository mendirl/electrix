package io.mend.electrix.ingestion;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ingestion.clickhouse")
public record IngestionClickhouseProperties(
    String url,
    String user,
    String password
) {
}
