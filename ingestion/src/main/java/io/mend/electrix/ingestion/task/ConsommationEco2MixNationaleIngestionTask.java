package io.mend.electrix.ingestion.task;

import io.mend.electrix.ingestion.config.IngestionDataProperties;
import io.mend.electrix.ingestion.domain.ConsommationEco2MixNationale;
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
public class ConsommationEco2MixNationaleIngestionTask implements CommandLineRunner {
  private static final Logger log = LoggerFactory.getLogger(ConsommationEco2MixNationaleIngestionTask.class);

  private final IngestionDataProperties ingestionDataProperties;
  private final ParquetParser parquetParser;
  private final ResourceDownloader resourceDownloader;
  private final ClickHouseRepository<ConsommationEco2MixNationale> nationalRepository;
  private final FileIngestionRepository fileIngestionRepository;

  public ConsommationEco2MixNationaleIngestionTask(IngestionDataProperties ingestionDataProperties,
                                                   ParquetParser parquetParser,
                                                   ResourceDownloader resourceDownloader,
                                                   ClickHouseRepository<ConsommationEco2MixNationale> nationalRepository,
                                                   FileIngestionRepository fileIngestionRepository) {
    this.ingestionDataProperties = ingestionDataProperties;
    this.parquetParser = parquetParser;
    this.resourceDownloader = resourceDownloader;
    this.nationalRepository = nationalRepository;
    this.fileIngestionRepository = fileIngestionRepository;
  }

  @Override
  @Async
  public void run(String @NonNull ... args) throws Exception {
    var data = ingestionDataProperties.consommation();

    if (data != null && data.eco2mix_nationale() != null) {
      var resource = data.eco2mix_nationale();
      var nomFichier = resource.getFilename();
      if (fileIngestionRepository.alreadyProcessed(nomFichier)) {
        log.info("Fichier {} déjà ingéré, on passe", nomFichier);
        return;
      }
      log.info("Starting ingestion for eco2mix nationale from {}", nomFichier);
      var file = resourceDownloader.download(resource);
      List<ConsommationEco2MixNationale> records = new ArrayList<>();
      parquetParser.parse(file, ConsommationEco2MixNationale.class, records::add);
      nationalRepository.insert(records);
      var record = new FileIngestionRecord();
      record.setFilename(nomFichier);
      record.setType("consommation_eco2mix_nationale");
      record.setRowCount(records.size());
      record.setStatus("SUCCESS");
      fileIngestionRepository.save(record);
      log.info("Fichier {} enregistré dans le suivi", nomFichier);
    }
  }
}
