package io.mend.electrix.ingestion.repository;

import io.mend.electrix.ingestion.domain.ConsommationBruteRegionale;
import io.mend.electrix.ingestion.infrastructure.ClickHouseDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConsommationBruteRegionaleRepository implements ClickHouseRepository<ConsommationBruteRegionale> {
  private static final Logger log = LoggerFactory.getLogger(ConsommationBruteRegionaleRepository.class);
  private final ClickHouseDatabase database;

  public ConsommationBruteRegionaleRepository(ClickHouseDatabase database) {
    this.database = database;
  }

  @Override
  public void insert(List<ConsommationBruteRegionale> records) throws SQLException {
    database.insert(records, "consommation_brute_regionale", preparer, this::ensureTableExists);
  }

  private final ClickHouseDatabase.StatementPreparer<ConsommationBruteRegionale> preparer = new ClickHouseDatabase.StatementPreparer<>() {
    @Override
    public void prepare(PreparedStatement ps, ConsommationBruteRegionale record) throws SQLException {
      ps.setObject(1, record.getDateHeure());
      ps.setObject(2, record.getDate());
      ps.setString(3, record.getHeure());
      ps.setString(4, record.getCodeInseeRegion());
      ps.setString(5, record.getRegion());
      ps.setObject(6, record.getConsommationBruteGazGrtgaz());
      ps.setString(7, record.getStatutGrtgaz());
      ps.setObject(8, record.getConsommationBruteGazTerega());
      ps.setString(9, record.getStatutTerega());
      ps.setObject(10, record.getConsommationBruteGazTotale());
      ps.setObject(11, record.getConsommationBruteElectriciteRte());
      ps.setString(12, record.getStatutRte());
      ps.setObject(13, record.getConsommationBruteTotale());
      ps.setString(14, record.getFlagIgnore());
    }

    @Override
    public String getInsertSql() {
      return """
        INSERT INTO consommation_brute_regionale (
          date_heure, date, heure, code_insee_region, region,
          consommation_brute_gaz_grtgaz, statut_grtgaz,
          consommation_brute_gaz_terega, statut_terega, consommation_brute_gaz_totale,
          consommation_brute_electricite_rte, statut_rte, consommation_brute_totale, flag_ignore
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
    }
  };

  private void ensureTableExists(Connection conn) throws SQLException {
    String createTableSql = """
      CREATE TABLE IF NOT EXISTS consommation_brute_regionale (
        date_heure DateTime64(3),
        date Nullable(Int32),
        heure String,
        code_insee_region String,
        region String,
        consommation_brute_gaz_grtgaz Nullable(Int64),
        statut_grtgaz Nullable(String),
        consommation_brute_gaz_terega Nullable(Int64),
        statut_terega Nullable(String),
        consommation_brute_gaz_totale Nullable(Int64),
        consommation_brute_electricite_rte Nullable(Int64),
        statut_rte Nullable(String),
        consommation_brute_totale Nullable(Int64),
        flag_ignore Nullable(String)
      ) ENGINE = MergeTree() ORDER BY (date_heure, code_insee_region)
      """;
    conn.createStatement().execute(createTableSql);
    log.info("Table 'consommation_brute_regionale' ready");
  }
}
