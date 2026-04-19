package io.mend.electrix.ingestion.task;

import io.mend.electrix.ingestion.config.IngestionDataProperties;
import io.mend.electrix.ingestion.domain.ConsommationBrute;
import io.mend.electrix.ingestion.infrastructure.ParquetParser;
import io.mend.electrix.ingestion.jooq.tables.records.FileIngestionRecord;
import io.mend.electrix.ingestion.repository.ClickHouseRepository;
import io.mend.electrix.ingestion.repository.FileIngestionRepository;
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
  private final FileIngestionRepository fileIngestionRepository;

  public ConsommationBruteIngestionTask(IngestionDataProperties ingestionDataProperties,
                                        ParquetParser parquetParser,
                                        ClickHouseRepository<ConsommationBrute> bruteRepository,
                                        FileIngestionRepository fileIngestionRepository) {
    this.ingestionDataProperties = ingestionDataProperties;
    this.parquetParser = parquetParser;
    this.bruteRepository = bruteRepository;
    this.fileIngestionRepository = fileIngestionRepository;
  }

  @Override
  public void run(String @NonNull ... args) throws Exception {
    var data = ingestionDataProperties.consommation();

    if (data != null && data.brut() != null) {
      var resource = data.brut();
      var filename = resource.getFilename();
      if (fileIngestionRepository.alreadyProcessed(filename)) {
        log.info("Fichier {} déjà ingéré, on passe", filename);
        return;
      }
      log.info("Starting ingestion for consommation brute from {}", filename);
      List<ConsommationBrute> records = new ArrayList<>();
      parquetParser.parse(resource, ConsommationBrute.class, records::add);
      bruteRepository.insert(records);
      var record = new FileIngestionRecord();
      record.setFilename(filename);
      record.setType("consommation_brute");
      record.setRowCount(records.size());
      record.setStatus("SUCCESS");
      fileIngestionRepository.save(record);
      log.info("Fichier {} enregistré dans le suivi", filename);
    }
  }
}
