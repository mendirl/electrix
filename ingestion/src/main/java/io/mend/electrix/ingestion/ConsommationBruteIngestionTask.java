package io.mend.electrix.ingestion;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsommationBruteIngestionTask implements CommandLineRunner {
  private static final Logger log = LoggerFactory.getLogger(ConsommationBruteIngestionTask.class);

  private final IngestionDataProperties ingestionDataProperties;
  private final ParquetParser parquetParser;
  private final ClickHouseRepository<ConsommationBrute> bruteRepository;

  public ConsommationBruteIngestionTask(IngestionDataProperties ingestionDataProperties,
                                        ParquetParser parquetParser,
                                        ClickHouseRepository<ConsommationBrute> bruteRepository) {
    this.ingestionDataProperties = ingestionDataProperties;
    this.parquetParser = parquetParser;
    this.bruteRepository = bruteRepository;
  }

  @Override
  public void run(String @NonNull ... args) throws Exception {
    var data = ingestionDataProperties.consommation();

    if (data != null && data.brut() != null) {
      var resource = data.brut();
      log.info("Starting ingestion for consommation brute from {}", resource.getFilename());
      List<ConsommationBrute> records = new ArrayList<>();
      parquetParser.parse(resource, ConsommationBrute.class, records::add);
      bruteRepository.insert(records);
    }
  }
}
