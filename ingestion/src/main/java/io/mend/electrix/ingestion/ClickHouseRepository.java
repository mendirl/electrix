package io.mend.electrix.ingestion;

import java.sql.SQLException;
import java.util.List;

public interface ClickHouseRepository<T> {
  void insert(List<T> records) throws SQLException;
}
