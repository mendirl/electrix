package io.mend.electrix.ingestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConsommationRegionaleRepository implements ClickHouseRepository<ConsommationRegionale> {
  private static final Logger log = LoggerFactory.getLogger(ConsommationRegionaleRepository.class);
  private final ClickHouseDatabase database;

  public ConsommationRegionaleRepository(ClickHouseDatabase database) {
    this.database = database;
  }

  @Override
  public void insert(List<ConsommationRegionale> records) throws SQLException {
    database.insert(records, "consommation_regionale", preparer, this::ensureTableExists);
  }

  private final ClickHouseDatabase.StatementPreparer<ConsommationRegionale> preparer = new ClickHouseDatabase.StatementPreparer<>() {
    @Override
    public void prepare(PreparedStatement ps, ConsommationRegionale r) throws SQLException {
      ps.setString(1, r.getCodeInseeRegion());
      ps.setString(2, r.getLibelleRegion());
      ps.setString(3, r.getNature());
      ps.setString(4, r.getDate());
      ps.setString(5, r.getHeure());
      ps.setObject(6, r.getDateHeure());
      ps.setObject(7, r.getConsommation());
      ps.setObject(8, r.getThermique());
      ps.setObject(9, r.getNucleaire());
      ps.setString(10, r.getEolien());
      ps.setObject(11, r.getSolaire());
      ps.setObject(12, r.getHydraulique());
      ps.setObject(13, r.getPompage());
      ps.setObject(14, r.getBioenergies());
      ps.setObject(15, r.getEchPhysiques());
      ps.setObject(16, r.getStockageBatterie());
      ps.setObject(17, r.getDestockageBatterie());
      ps.setObject(18, r.getEolienTerrestre());
      ps.setObject(19, r.getEolienOffshore());
      ps.setObject(20, r.getTcoThermique());
      ps.setObject(21, r.getTchThermique());
      ps.setObject(22, r.getTcoNucleaire());
      ps.setObject(23, r.getTchNucleaire());
      ps.setObject(24, r.getTcoEolien());
      ps.setObject(25, r.getTchEolien());
      ps.setObject(26, r.getTcoSolaire());
      ps.setObject(27, r.getTchSolaire());
      ps.setObject(28, r.getTcoHydraulique());
      ps.setObject(29, r.getTchHydraulique());
      ps.setObject(30, r.getTcoBioenergies());
      ps.setObject(31, r.getTchBioenergies());
      ps.setString(32, r.getColumn30());
    }

    @Override
    public String getInsertSql() {
      return """
        INSERT INTO consommation_regionale (
          code_insee_region, libelle_region, nature, date, heure, date_heure, consommation,
          thermique, nucleaire, eolien, solaire, hydraulique, pompage, bioenergies,
          ech_physiques, stockage_batterie, destockage_batterie, eolien_terrestre,
          eolien_offshore, tco_thermique, tch_thermique, tco_nucleaire, tch_nucleaire,
          tco_eolien, tch_eolien, tco_solaire, tch_solaire, tco_hydraulique,
          tch_hydraulique, tco_bioenergies, tch_bioenergies, column_30
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
    }
  };

  private void ensureTableExists(Connection conn) throws SQLException {
    String sql = """
      CREATE TABLE IF NOT EXISTS consommation_regionale (
        code_insee_region String, libelle_region String, nature String, date String,
        heure String, date_heure DateTime64(3), consommation Nullable(Int64),
        thermique Nullable(Int64), nucleaire Nullable(Int64), eolien Nullable(String),
        solaire Nullable(Int64), hydraulique Nullable(Int64), pompage Nullable(Int64),
        bioenergies Nullable(Int64), ech_physiques Nullable(Int64),
        stockage_batterie Nullable(Int64), destockage_batterie Nullable(Int64),
        eolien_terrestre Nullable(Int64), eolien_offshore Nullable(Int64),
        tco_thermique Nullable(Float64), tch_thermique Nullable(Float64),
        tco_nucleaire Nullable(Float64), tch_nucleaire Nullable(Float64),
        tco_eolien Nullable(Float64), tch_eolien Nullable(Float64),
        tco_solaire Nullable(Float64), tch_solaire Nullable(Float64),
        tco_hydraulique Nullable(Float64), tch_hydraulique Nullable(Float64),
        tco_bioenergies Nullable(Float64), tch_bioenergies Nullable(Float64),
        column_30 Nullable(String)
      ) ENGINE = MergeTree() ORDER BY (date_heure)
      """;
    conn.createStatement().execute(sql);
    log.info("Table 'consommation_regionale' ready");
  }
}
