package io.mend.electrix.ingestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConsommationBruteRepository implements ClickHouseRepository<ConsommationBrute> {
  private static final Logger log = LoggerFactory.getLogger(ConsommationBruteRepository.class);
  private final ClickHouseDatabase database;

  public ConsommationBruteRepository(ClickHouseDatabase database) {
    this.database = database;
  }

  @Override
  public void insert(List<ConsommationBrute> records) throws SQLException {
    database.insert(records, "consommation_brute", preparer, this::ensureTableExists);
  }

  private final ClickHouseDatabase.StatementPreparer<ConsommationBrute> preparer = new ClickHouseDatabase.StatementPreparer<>() {
    @Override
    public void prepare(PreparedStatement ps, ConsommationBrute record) throws SQLException {
      ps.setObject(1, record.getDateHeure());
      ps.setString(2, record.getDate());
      ps.setString(3, record.getHeure());
      ps.setObject(4, record.getConsommationBruteGazGrtgaz());
      ps.setString(5, record.getStatutGrtgaz());
      ps.setObject(6, record.getConsommationBruteGazTerega());
      ps.setString(7, record.getStatutTerega());
      ps.setObject(8, record.getConsommationBruteGazTotale());
      ps.setObject(9, record.getConsommationBruteElectriciteRte());
      ps.setString(10, record.getStatutRte());
      ps.setObject(11, record.getConsommationBruteTotale());
      ps.setString(12, record.getFlagIgnore());
    }

    @Override
    public String getInsertSql() {
      return """
        INSERT INTO consommation_brute (
          date_heure, date, heure, consommation_brute_gaz_grtgaz, statut_grtgaz,
          consommation_brute_gaz_terega, statut_terega, consommation_brute_gaz_totale,
          consommation_brute_electricite_rte, statut_rte, consommation_brute_totale, flag_ignore
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
    }
  };

  private void ensureTableExists(Connection conn) throws SQLException {
    String createTableSql = """
      CREATE TABLE IF NOT EXISTS consommation_brute (
        date_heure DateTime64(3),
        date String,
        heure String,
        consommation_brute_gaz_grtgaz Nullable(Int64),
        statut_grtgaz Nullable(String),
        consommation_brute_gaz_terega Nullable(Int64),
        statut_terega Nullable(String),
        consommation_brute_gaz_totale Nullable(Int64),
        consommation_brute_electricite_rte Nullable(Int64),
        statut_rte Nullable(String),
        consommation_brute_totale Nullable(Int64),
        flag_ignore Nullable(String)
      ) ENGINE = MergeTree() ORDER BY (date_heure)
      """;
    conn.createStatement().execute(createTableSql);
    log.info("Table 'consommation_brute' ready");
  }
}
