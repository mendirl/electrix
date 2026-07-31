package io.mendirl.mcpserver.infrastructure;

import io.mendirl.mcpserver.config.ClickhouseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClickHouseDatabase {
  private static final Logger log = LoggerFactory.getLogger(ClickHouseDatabase.class);

  private final ClickhouseProperties properties;

  public ClickHouseDatabase(ClickhouseProperties properties) {
    this.properties = properties;
  }


  public List<String> listTables(String databaseName) {
    List<String> tables = new ArrayList<>();
    String query = "SHOW TABLES FROM " + databaseName;

    try (Connection conn = DriverManager.getConnection(properties.url(), properties.user(), properties.password());
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

      while (rs.next()) {
        tables.add(rs.getString(1));
      }

    } catch (SQLException e) {
      log.error("Error listing tables for database {}", databaseName, e);
    }

    return tables;
  }
}
