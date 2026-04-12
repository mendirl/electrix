package io.mend.electrix.ingestion;

import lombok.Data;
import org.apache.avro.reflect.AvroName;

@Data
public final class ConsommationRegionale {
  @AvroName("code_insee_region")
  private String codeInseeRegion;
  @AvroName("libelle_region")
  private String libelleRegion;
  private String nature;
  private String date;
  private String heure;
  @AvroName("date_heure")
  private Long dateHeure;
  private Long consommation;
  private Long thermique;
  private Long nucleaire;
  private String eolien;
  private Long solaire;
  private Long hydraulique;
  private Long pompage;
  private Long bioenergies;
  @AvroName("ech_physiques")
  private Long echPhysiques;
  @AvroName("stockage_batterie")
  private Long stockageBatterie;
  @AvroName("destockage_batterie")
  private Long destockageBatterie;
  @AvroName("eolien_terrestre")
  private Long eolienTerrestre;
  @AvroName("eolien_offshore")
  private Long eolienOffshore;
  @AvroName("tco_thermique")
  private Double tcoThermique;
  @AvroName("tch_thermique")
  private Double tchThermique;
  @AvroName("tco_nucleaire")
  private Double tcoNucleaire;
  @AvroName("tch_nucleaire")
  private Double tchNucleaire;
  @AvroName("tco_eolien")
  private Double tcoEolien;
  @AvroName("tch_eolien")
  private Double tchEolien;
  @AvroName("tco_solaire")
  private Double tcoSolaire;
  @AvroName("tch_solaire")
  private Double tchSolaire;
  @AvroName("tco_hydraulique")
  private Double tcoHydraulique;
  @AvroName("tch_hydraulique")
  private Double tchHydraulique;
  @AvroName("tco_bioenergies")
  private Double tcoBioenergies;
  @AvroName("tch_bioenergies")
  private Double tchBioenergies;
  @AvroName("column_30")
  private String column30;

  public String getCodeInseeRegion() {
    return codeInseeRegion;
  }

  public String getLibelleRegion() {
    return libelleRegion;
  }

  public String getNature() {
    return nature;
  }

  public String getDate() {
    return date;
  }

  public String getHeure() {
    return heure;
  }

  public Long getDateHeure() {
    return dateHeure;
  }

  public Long getConsommation() {
    return consommation;
  }

  public Long getThermique() {
    return thermique;
  }

  public Long getNucleaire() {
    return nucleaire;
  }

  public String getEolien() {
    return eolien;
  }

  public Long getSolaire() {
    return solaire;
  }

  public Long getHydraulique() {
    return hydraulique;
  }

  public Long getPompage() {
    return pompage;
  }

  public Long getBioenergies() {
    return bioenergies;
  }

  public Long getEchPhysiques() {
    return echPhysiques;
  }

  public Long getStockageBatterie() {
    return stockageBatterie;
  }

  public Long getDestockageBatterie() {
    return destockageBatterie;
  }

  public Long getEolienTerrestre() {
    return eolienTerrestre;
  }

  public Long getEolienOffshore() {
    return eolienOffshore;
  }

  public Double getTcoThermique() {
    return tcoThermique;
  }

  public Double getTchThermique() {
    return tchThermique;
  }

  public Double getTcoNucleaire() {
    return tcoNucleaire;
  }

  public Double getTchNucleaire() {
    return tchNucleaire;
  }

  public Double getTcoEolien() {
    return tcoEolien;
  }

  public Double getTchEolien() {
    return tchEolien;
  }

  public Double getTcoSolaire() {
    return tcoSolaire;
  }

  public Double getTchSolaire() {
    return tchSolaire;
  }

  public Double getTcoHydraulique() {
    return tcoHydraulique;
  }

  public Double getTchHydraulique() {
    return tchHydraulique;
  }

  public Double getTcoBioenergies() {
    return tcoBioenergies;
  }

  public Double getTchBioenergies() {
    return tchBioenergies;
  }

  public String getColumn30() {
    return column30;
  }
}
