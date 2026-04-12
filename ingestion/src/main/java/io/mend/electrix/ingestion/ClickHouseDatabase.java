package io.mend.electrix.ingestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class ClickHouseDatabase {
  private static final Logger log = LoggerFactory.getLogger(ClickHouseDatabase.class);

  private final IngestionClickhouseProperties properties;

  public ClickHouseDatabase(IngestionClickhouseProperties properties) {
    this.properties = properties;
  }

  public <T> void insert(List<T> records,
                          String tableName,
                          StatementPreparer<T> preparer,
                          TableEnsurer ensurer) throws SQLException {
    if (records.isEmpty()) {
      return;
    }

    try (Connection conn = DriverManager.getConnection(properties.url(), properties.user(), properties.password())) {
      ensurer.ensure(conn);

      String insertSql = preparer.getInsertSql();

      try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
        int count = 0;
        for (T record : records) {
          preparer.prepare(ps, record);
          ps.addBatch();
          count++;

          if (count % 1000 == 0) {
            ps.executeBatch();
          }
        }
        ps.executeBatch();
        log.info("Ingested {} rows into {}", count, tableName);
      }
    }
  }

  public interface StatementPreparer<T> {
    void prepare(PreparedStatement ps, T record) throws SQLException;

    String getInsertSql();
  }

  @FunctionalInterface
  public interface TableEnsurer {
    void ensure(Connection conn) throws SQLException;
  }
}
