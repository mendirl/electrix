package io.mend.electrix.ingestion.task;

import io.mend.electrix.ingestion.config.IngestionDataProperties;
import io.mend.electrix.ingestion.domain.ConsommationRegionale;
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
public class ConsommationRegionaleIngestionTask implements CommandLineRunner {
  private static final Logger log = LoggerFactory.getLogger(ConsommationRegionaleIngestionTask.class);

  private final IngestionDataProperties ingestionDataProperties;
  private final ParquetParser parquetParser;
  private final ClickHouseRepository<ConsommationRegionale> regionaleRepository;
  private final FileIngestionRepository fileIngestionRepository;

  public ConsommationRegionaleIngestionTask(IngestionDataProperties ingestionDataProperties,
                                            ParquetParser parquetParser,
                                            ClickHouseRepository<ConsommationRegionale> regionaleRepository,
                                            FileIngestionRepository fileIngestionRepository) {
    this.ingestionDataProperties = ingestionDataProperties;
    this.parquetParser = parquetParser;
    this.regionaleRepository = regionaleRepository;
    this.fileIngestionRepository = fileIngestionRepository;
  }

  @Override
  public void run(String @NonNull ... args) throws Exception {
    var data = ingestionDataProperties.consommation();

    if (data != null && data.regionale() != null) {
      var resource = data.regionale();
      var nomFichier = resource.getFilename();
      if (fileIngestionRepository.alreadyProcessed(nomFichier)) {
        log.info("Fichier {} déjà ingéré, on passe", nomFichier);
        return;
      }
      log.info("Starting ingestion for regionale from {}", nomFichier);
      List<ConsommationRegionale> records = new ArrayList<>();
      parquetParser.parse(resource, ConsommationRegionale.class, records::add);
      regionaleRepository.insert(records);
      var record = new FileIngestionRecord();
      record.setFilename(nomFichier);
      record.setType("consommation_regionale");
      record.setRowCount(records.size());
      record.setStatus("SUCCESS");
      fileIngestionRepository.save(record);
      log.info("Fichier {} enregistré dans le suivi", nomFichier);
    }
  }
}
