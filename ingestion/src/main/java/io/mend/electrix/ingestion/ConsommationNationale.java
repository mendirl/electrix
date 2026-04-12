package io.mend.electrix.ingestion;

import lombok.Data;
import org.apache.avro.reflect.AvroName;

@Data
public final class ConsommationNationale {
  private String perimetre;
  private String nature;
  private String date;
  private String heure;
  @AvroName("date_heure")
  private Long dateHeure;
  private Long consommation;
  @AvroName("prevision_j1")
  private Long previsionJ1;
  @AvroName("prevision_j")
  private Long previsionJ;
  private Long fioul;
  private Long charbon;
  private Long gaz;
  private Long nucleaire;
  private Long eolien;
  private Long solaire;
  private Long hydraulique;
  private Long pompage;
  private Long bioenergies;
  @AvroName("ech_physiques")
  private Long echPhysiques;
  @AvroName("taux_co2")
  private Long tauxCo2;
  @AvroName("ech_comm_angleterre")
  private Long echCommAngleterre;
  @AvroName("ech_comm_espagne")
  private Long echCommEspagne;
  @AvroName("ech_comm_italie")
  private Long echCommItalie;
  @AvroName("ech_comm_suisse")
  private Long echCommSuisse;
  @AvroName("ech_comm_allemagne_belgique")
  private String echCommAllemagneBelgique;
  @AvroName("fioul_tac")
  private Long fioulTac;
  @AvroName("fioul_cogen")
  private Long fioulCogen;
  @AvroName("fioul_autres")
  private Long fioulAutres;
  @AvroName("gaz_tac")
  private Long gazTac;
  @AvroName("gaz_cogen")
  private String gazCogen;
  @AvroName("gaz_ccg")
  private Long gazCcg;
  @AvroName("gaz_autres")
  private Long gazAutres;
  @AvroName("hydraulique_fil_eau_eclusee")
  private Long hydrauliqueFilEauEclusee;
  @AvroName("hydraulique_lacs")
  private Long hydrauliqueLacs;
  @AvroName("hydraulique_step_turbinage")
  private Long hydrauliqueStepTurbinage;
  @AvroName("bioenergies_dechets")
  private Long bioenergiesDechets;
  @AvroName("bioenergies_biomasse")
  private Long bioenergiesBiomasse;
  @AvroName("bioenergies_biogaz")
  private Long bioenergiesBiogaz;

  public String getPerimetre() {
    return perimetre;
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

  public Long getPrevisionJ1() {
    return previsionJ1;
  }

  public Long getPrevisionJ() {
    return previsionJ;
  }

  public Long getFioul() {
    return fioul;
  }

  public Long getCharbon() {
    return charbon;
  }

  public Long getGaz() {
    return gaz;
  }

  public Long getNucleaire() {
    return nucleaire;
  }

  public Long getEolien() {
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

  public Long getTauxCo2() {
    return tauxCo2;
  }

  public Long getEchCommAngleterre() {
    return echCommAngleterre;
  }

  public Long getEchCommEspagne() {
    return echCommEspagne;
  }

  public Long getEchCommItalie() {
    return echCommItalie;
  }

  public Long getEchCommSuisse() {
    return echCommSuisse;
  }

  public String getEchCommAllemagneBelgique() {
    return echCommAllemagneBelgique;
  }

  public Long getFioulTac() {
    return fioulTac;
  }

  public Long getFioulCogen() {
    return fioulCogen;
  }

  public Long getFioulAutres() {
    return fioulAutres;
  }

  public Long getGazTac() {
    return gazTac;
  }

  public String getGazCogen() {
    return gazCogen;
  }

  public Long getGazCcg() {
    return gazCcg;
  }

  public Long getGazAutres() {
    return gazAutres;
  }

  public Long getHydrauliqueFilEauEclusee() {
    return hydrauliqueFilEauEclusee;
  }

  public Long getHydrauliqueLacs() {
    return hydrauliqueLacs;
  }

  public Long getHydrauliqueStepTurbinage() {
    return hydrauliqueStepTurbinage;
  }

  public Long getBioenergiesDechets() {
    return bioenergiesDechets;
  }

  public Long getBioenergiesBiomasse() {
    return bioenergiesBiomasse;
  }

  public Long getBioenergiesBiogaz() {
    return bioenergiesBiogaz;
  }
}
