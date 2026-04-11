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

  public void insertBrute(List<ConsommationBrute> records) throws SQLException {
    insert(records, "consommation_brute", prepareConsommationBrute, this::ensureConsommationBruteTableExists);
  }

  public void insertNational(List<ConsommationNationale> records) throws SQLException {
    insert(records, "consommation_nationale", prepareConsommationNationale, this::ensureNationalTableExists);
  }

  public void insertRegionale(List<ConsommationRegionale> records) throws SQLException {
    insert(records, "consommation_regionale", prepareConsommationRegionale, this::ensureRegionaleTableExists);
  }

  private <T> void insert(List<T> records,
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

  private interface StatementPreparer<T> {
    void prepare(PreparedStatement ps, T record) throws SQLException;

    String getInsertSql();
  }

  @FunctionalInterface
  private interface TableEnsurer {
    void ensure(Connection conn) throws SQLException;
  }

  private void prepareConsommationBrute(PreparedStatement ps, ConsommationBrute record) throws SQLException {
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

  private String prepareConsommationBruteInsertSql = """
    INSERT INTO consommation_brute (
      date_heure, date, heure, consommation_brute_gaz_grtgaz, statut_grtgaz,
      consommation_brute_gaz_terega, statut_terega, consommation_brute_gaz_totale,
      consommation_brute_electricite_rte, statut_rte, consommation_brute_totale, flag_ignore
    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

  private StatementPreparer<ConsommationBrute> prepareConsommationBrute = new StatementPreparer<>() {
    public void prepare(PreparedStatement ps, ConsommationBrute record) throws SQLException {
      prepareConsommationBrute(ps, record);
    }

    public String getInsertSql() {
      return prepareConsommationBruteInsertSql;
    }
  };

  private void ensureConsommationBruteTableExists(Connection conn) throws SQLException {
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

  private void prepareConsommationNationale(PreparedStatement ps, ConsommationNationale r) throws SQLException {
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

  private String prepareNationalInsertSql = """
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

  private StatementPreparer<ConsommationNationale> prepareConsommationNationale = new StatementPreparer<>() {
    public void prepare(PreparedStatement ps, ConsommationNationale r) throws SQLException {
      prepareConsommationNationale(ps, r);
    }

    public String getInsertSql() {
      return prepareNationalInsertSql;
    }
  };

  private void ensureNationalTableExists(Connection conn) throws SQLException {
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

  private void prepareConsommationRegionale(PreparedStatement ps, ConsommationRegionale r) throws SQLException {
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

  private String prepareRegionaleInsertSql = """
    INSERT INTO consommation_regionale (
      code_insee_region, libelle_region, nature, date, heure, date_heure, consommation,
      thermique, nucleaire, eolien, solaire, hydraulique, pompage, bioenergies,
      ech_physiques, stockage_batterie, destockage_batterie, eolien_terrestre,
      eolien_offshore, tco_thermique, tch_thermique, tco_nucleaire, tch_nucleaire,
      tco_eolien, tch_eolien, tco_solaire, tch_solaire, tco_hydraulique,
      tch_hydraulique, tco_bioenergies, tch_bioenergies, column_30
    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

  private StatementPreparer<ConsommationRegionale> prepareConsommationRegionale = new StatementPreparer<>() {
    public void prepare(PreparedStatement ps, ConsommationRegionale r) throws SQLException {
      prepareConsommationRegionale(ps, r);
    }

    public String getInsertSql() {
      return prepareRegionaleInsertSql;
    }
  };

  private void ensureRegionaleTableExists(Connection conn) throws SQLException {
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
