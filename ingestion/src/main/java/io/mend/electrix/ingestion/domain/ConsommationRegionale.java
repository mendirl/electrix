package io.mend.electrix.ingestion.domain;

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

}
