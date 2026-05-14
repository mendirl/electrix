package io.mend.electrix.ingestion.task;

import io.mend.electrix.ingestion.config.IngestionDataProperties;
import io.mend.electrix.ingestion.domain.ConsommationBruteRegionale;
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
public class ConsommationBruteRegionaleIngestionTask implements CommandLineRunner {
  private static final Logger log = LoggerFactory.getLogger(ConsommationBruteRegionaleIngestionTask.class);

  private final IngestionDataProperties ingestionDataProperties;
  private final ParquetParser parquetParser;
  private final ResourceDownloader resourceDownloader;
  private final ClickHouseRepository<ConsommationBruteRegionale> bruteRegionaleRepository;
  private final FileIngestionRepository fileIngestionRepository;

  public ConsommationBruteRegionaleIngestionTask(IngestionDataProperties ingestionDataProperties,
                                                 ParquetParser parquetParser,
                                                 ResourceDownloader resourceDownloader,
                                                 ClickHouseRepository<ConsommationBruteRegionale> bruteRegionaleRepository,
                                                 FileIngestionRepository fileIngestionRepository) {
    this.ingestionDataProperties = ingestionDataProperties;
    this.parquetParser = parquetParser;
    this.resourceDownloader = resourceDownloader;
    this.bruteRegionaleRepository = bruteRegionaleRepository;
    this.fileIngestionRepository = fileIngestionRepository;
  }

  @Override
  @Async
  public void run(String @NonNull ... args) throws Exception {
    var data = ingestionDataProperties.consommation();

    if (data != null && data.brut_regional() != null) {
      var resource = data.brut_regional();
      var filename = resource.getFilename();
      if (fileIngestionRepository.alreadyProcessed(filename)) {
        log.info("Fichier {} déjà ingéré, on passe", filename);
        return;
      }
      log.info("Starting ingestion for consommation brute régionale from {}", filename);
      var file = resourceDownloader.download(resource);
      List<ConsommationBruteRegionale> records = new ArrayList<>();
      parquetParser.parse(file, ConsommationBruteRegionale.class, records::add);
      bruteRegionaleRepository.insert(records);
      var record = new FileIngestionRecord();
      record.setFilename(filename);
      record.setType("consommation_brute_regionale");
      record.setRowCount(records.size());
      record.setStatus("SUCCESS");
      fileIngestionRepository.save(record);
      log.info("Fichier {} enregistré dans le suivi", filename);
    }
  }
}
