package io.mend.electrix.ingestion;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IngestionTask implements CommandLineRunner {
  private static final Logger log = LoggerFactory.getLogger(IngestionTask.class);

  private final IngestionDataProperties ingestionDataProperties;
  private final ParquetParser parquetParser;
  private final ClickHouseDatabase clickHouseDatabase;

  public IngestionTask(IngestionDataProperties ingestionDataProperties,
                       ParquetParser parquetParser,
                       ClickHouseDatabase clickHouseDatabase) {
    this.ingestionDataProperties = ingestionDataProperties;
    this.parquetParser = parquetParser;
    this.clickHouseDatabase = clickHouseDatabase;
  }

  @Override
  public void run(String @NonNull ... args) throws Exception {
    var data = ingestionDataProperties.consommationBrute();

    if (data != null && data.brut() != null) {
      var resource = data.brut();
      log.info("Starting ingestion for consommation from {}", resource.getFilename());
      List<ConsommationBrute> records = new ArrayList<>();
      parquetParser.parse(resource, ConsommationBrute.class, records::add);
      clickHouseDatabase.insertBrute(records);
    }

    if (data != null && data.nationale() != null) {
      var resource = data.nationale();
      log.info("Starting ingestion for nationale from {}", resource.getFilename());
      List<ConsommationNationale> records = new ArrayList<>();
      parquetParser.parse(resource, ConsommationNationale.class, records::add);
      clickHouseDatabase.insertNational(records);
    }

    if (data != null && data.regionale() != null) {
      var resource = data.regionale();
      log.info("Starting ingestion for regionale from {}", resource.getFilename());
      List<ConsommationRegionale> records = new ArrayList<>();
      parquetParser.parse(resource, ConsommationRegionale.class, records::add);
      clickHouseDatabase.insertRegionale(records);
    }
  }
}
