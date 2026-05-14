package io.mendirl.mcpserver.infrastructure;

import io.mendirl.mcpserver.config.ClickhouseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClickHouseDatabase {
  private static final Logger log = LoggerFactory.getLogger(ClickHouseDatabase.class);

  private final ClickhouseProperties properties;

  public ClickHouseDatabase(ClickhouseProperties properties) {
    this.properties = properties;
  }


  public List<String> listTables(String databaseName) {
    return null;
  }
}
