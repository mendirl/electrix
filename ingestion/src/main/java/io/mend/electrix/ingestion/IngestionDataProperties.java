package io.mend.electrix.ingestion;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "ingestion.data")
public record IngestionDataProperties(DataProperties consommation) {

  public record DataProperties(Resource brut, Resource nationale, Resource regionale) {
  }

}
