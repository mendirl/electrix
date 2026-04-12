package io.mend.electrix.ingestion.repository;

import io.mend.electrix.ingestion.domain.ConsommationNationale;
import io.mend.electrix.ingestion.infrastructure.ClickHouseDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConsommationNationaleRepository implements ClickHouseRepository<ConsommationNationale> {
  private static final Logger log = LoggerFactory.getLogger(ConsommationNationaleRepository.class);
  private final ClickHouseDatabase database;

  public ConsommationNationaleRepository(ClickHouseDatabase database) {
    this.database = database;
  }

  @Override
  public void insert(List<ConsommationNationale> records) throws SQLException {
    database.insert(records, "consommation_nationale", preparer, this::ensureTableExists);
  }

  private final ClickHouseDatabase.StatementPreparer<ConsommationNationale> preparer = new ClickHouseDatabase.StatementPreparer<>() {
    @Override
    public void prepare(PreparedStatement ps, ConsommationNationale r) throws SQLException {
      ps.setString(1, r.getPerimetre());
      ps.setString(2, r.getNature());
      ps.setString(3, r.getDate());
      ps.setString(4, r.getHeure());
      ps.setObject(5, r.getDateHeure());
      ps.setObject(6, r.getConsommation());
      ps.setObject(7, r.getPrevisionJ1());
      ps.setObject(8, r.getPrevisionJ());
      ps.setObject(9, r.getFioul());
      ps.setObject(10, r.getCharbon());
      ps.setObject(11, r.getGaz());
      ps.setObject(12, r.getNucleaire());
      ps.setObject(13, r.getEolien());
      ps.setObject(14, r.getSolaire());
      ps.setObject(15, r.getHydraulique());
      ps.setObject(16, r.getPompage());
      ps.setObject(17, r.getBioenergies());
      ps.setObject(18, r.getEchPhysiques());
      ps.setObject(19, r.getTauxCo2());
      ps.setObject(20, r.getEchCommAngleterre());
      ps.setObject(21, r.getEchCommEspagne());
      ps.setObject(22, r.getEchCommItalie());
      ps.setObject(23, r.getEchCommSuisse());
      ps.setString(24, r.getEchCommAllemagneBelgique());
      ps.setObject(25, r.getFioulTac());
      ps.setObject(26, r.getFioulCogen());
      ps.setObject(27, r.getFioulAutres());
      ps.setObject(28, r.getGazTac());
      ps.setString(29, r.getGazCogen());
      ps.setObject(30, r.getGazCcg());
      ps.setObject(31, r.getGazAutres());
      ps.setObject(32, r.getHydrauliqueFilEauEclusee());
      ps.setObject(33, r.getHydrauliqueLacs());
      ps.setObject(34, r.getHydrauliqueStepTurbinage());
      ps.setObject(35, r.getBioenergiesDechets());
      ps.setObject(36, r.getBioenergiesBiomasse());
      ps.setObject(37, r.getBioenergiesBiogaz());
    }

    @Override
    public String getInsertSql() {
      return """
        INSERT INTO consommation_nationale (
          perimetre, nature, date, heure, date_heure, consommation, prevision_j1, prevision_j,
          fioul, charbon, gaz, nucleaire, eolien, solaire, hydraulique, pompage, bioenergies,
          ech_physiques, taux_co2, ech_comm_angleterre, ech_comm_espagne, ech_comm_italie,
          ech_comm_suisse, ech_comm_allemagne_belgique, fioul_tac, fioul_cogen, fioul_autres,
          gaz_tac, gaz_cogen, gaz_ccg, gaz_autres, hydraulique_fil_eau_eclusee,
          hydraulique_lacs, hydraulique_step_turbinage, bioenergies_dechets,
          bioenergies_biomasse, bioenergies_biogaz
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
    }
  };

  private void ensureTableExists(Connection conn) throws SQLException {
    String sql = """
      CREATE TABLE IF NOT EXISTS consommation_nationale (
        perimetre String, nature String, date String, heure String, date_heure DateTime64(3),
        consommation Nullable(Int64), prevision_j1 Nullable(Int64), prevision_j Nullable(Int64),
        fioul Nullable(Int64), charbon Nullable(Int64), gaz Nullable(Int64), nucleaire Nullable(Int64),
        eolien Nullable(Int64), solaire Nullable(Int64), hydraulique Nullable(Int64),
        pompage Nullable(Int64), bioenergies Nullable(Int64), ech_physiques Nullable(Int64),
        taux_co2 Nullable(Int64), ech_comm_angleterre Nullable(Int64), ech_comm_espagne Nullable(Int64),
        ech_comm_italie Nullable(Int64), ech_comm_suisse Nullable(Int64),
        ech_comm_allemagne_belgique Nullable(String), fioul_tac Nullable(Int64),
        fioul_cogen Nullable(Int64), fioul_autres Nullable(Int64), gaz_tac Nullable(Int64),
        gaz_cogen Nullable(String), gaz_ccg Nullable(Int64), gaz_autres Nullable(Int64),
        hydraulique_fil_eau_eclusee Nullable(Int64), hydraulique_lacs Nullable(Int64),
        hydraulique_step_turbinage Nullable(Int64), bioenergies_dechets Nullable(Int64),
        bioenergies_biomasse Nullable(Int64), bioenergies_biogaz Nullable(Int64)
      ) ENGINE = MergeTree() ORDER BY (date_heure)
      """;
    conn.createStatement().execute(sql);
    log.info("Table 'consommation_nationale' ready");
  }
}
