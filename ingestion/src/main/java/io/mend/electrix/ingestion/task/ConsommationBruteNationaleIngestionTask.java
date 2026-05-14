package io.mend.electrix.ingestion.task;

import io.mend.electrix.ingestion.config.IngestionDataProperties;
import io.mend.electrix.ingestion.domain.ConsommationBruteNationale;
import io.mend.electrix.ingestion.infrastructure.ParquetParser;
import io.mend.electrix.ingestion.infrastructure.ResourceDownloader;
import io.mend.electrix.ingestion.jooq.tables.records.FileIngestionRecord;
import io.mend.electrix.ingestion.repository.ClickHouseRepository;
import io.mend.electrix.ingestion.repository.FileIngestionRepository;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsommationBruteNationaleIngestionTask implements CommandLineRunner {
  private static final Logger log = LoggerFactory.getLogger(ConsommationBruteNationaleIngestionTask.class);

  private final IngestionDataProperties ingestionDataProperties;
  private final ParquetParser parquetParser;
  private final ResourceDownloader resourceDownloader;
  private final ClickHouseRepository<ConsommationBruteNationale> bruteRepository;
  private final FileIngestionRepository fileIngestionRepository;

  public ConsommationBruteNationaleIngestionTask(IngestionDataProperties ingestionDataProperties,
                                                 ParquetParser parquetParser,
                                                 ResourceDownloader resourceDownloader,
                                                 ClickHouseRepository<ConsommationBruteNationale> bruteRepository,
                                                 FileIngestionRepository fileIngestionRepository) {
    this.ingestionDataProperties = ingestionDataProperties;
    this.parquetParser = parquetParser;
    this.resourceDownloader = resourceDownloader;
    this.bruteRepository = bruteRepository;
    this.fileIngestionRepository = fileIngestionRepository;
  }

  @Override
  @Async
  public void run(String @NonNull ... args) throws Exception {
    var data = ingestionDataProperties.consommation();

    if (data != null && data.brut_national() != null) {
      var resource = data.brut_national();
      var filename = resource.getFilename();
      if (fileIngestionRepository.alreadyProcessed(filename)) {
        log.info("Fichier {} déjà ingéré, on passe", filename);
        return;
      }
      log.info("Starting ingestion for consommation brute nationale from {}", filename);
      var file = resourceDownloader.download(resource);
      List<ConsommationBruteNationale> records = new ArrayList<>();
      parquetParser.parse(file, ConsommationBruteNationale.class, records::add);
      bruteRepository.insert(records);
      var record = new FileIngestionRecord();
      record.setFilename(filename);
      record.setType("consommation_brute_nationale");
      record.setRowCount(records.size());
      record.setStatus("SUCCESS");
      fileIngestionRepository.save(record);
      log.info("Fichier {} enregistré dans le suivi", filename);
    }
  }
}
