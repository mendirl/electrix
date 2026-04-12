package io.mend.electrix.ingestion;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsommationRegionaleIngestionTask implements CommandLineRunner {
  private static final Logger log = LoggerFactory.getLogger(ConsommationRegionaleIngestionTask.class);

  private final IngestionDataProperties ingestionDataProperties;
  private final ParquetParser parquetParser;
  private final ClickHouseRepository<ConsommationRegionale> regionaleRepository;

  public ConsommationRegionaleIngestionTask(IngestionDataProperties ingestionDataProperties,
                                            ParquetParser parquetParser,
                                            ClickHouseRepository<ConsommationRegionale> regionaleRepository) {
    this.ingestionDataProperties = ingestionDataProperties;
    this.parquetParser = parquetParser;
    this.regionaleRepository = regionaleRepository;
  }

  @Override
  public void run(String @NonNull ... args) throws Exception {
    var data = ingestionDataProperties.consommation();

    if (data != null && data.regionale() != null) {
      var resource = data.regionale();
      log.info("Starting ingestion for regionale from {}", resource.getFilename());
      List<ConsommationRegionale> records = new ArrayList<>();
      parquetParser.parse(resource, ConsommationRegionale.class, records::add);
      regionaleRepository.insert(records);
    }
  }
}
