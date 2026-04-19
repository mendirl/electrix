package io.mend.electrix.ingestion.repository;

import io.mend.electrix.ingestion.jooq.tables.records.FileIngestionRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static io.mend.electrix.ingestion.jooq.Tables.FILE_INGESTION;

@Repository
public class FileIngestionRepository {

  private final DSLContext dsl;

  public FileIngestionRepository(DSLContext dsl) {
    this.dsl = dsl;
  }

  public void save(FileIngestionRecord record) {
    dsl.insertInto(FILE_INGESTION)
      .set(FILE_INGESTION.FILENAME, record.getFilename())
      .set(FILE_INGESTION.TYPE, record.getType())
      .set(FILE_INGESTION.ROW_COUNT, record.getRowCount())
      .set(FILE_INGESTION.STATUS, record.getStatus())
      .execute();
  }

  public boolean alreadyProcessed(String filename) {
    return dsl.fetchExists(
      dsl.selectOne()
        .from(FILE_INGESTION)
        .where(FILE_INGESTION.FILENAME.eq(filename))
        .and(FILE_INGESTION.STATUS.eq("SUCCESS"))
    );
  }

}
