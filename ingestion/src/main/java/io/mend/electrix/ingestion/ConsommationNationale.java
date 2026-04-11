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

}
