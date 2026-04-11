package io.mend.electrix.ingestion;

import lombok.Data;
import org.apache.avro.reflect.AvroName;

@Data
public final class ConsommationBrute {
  @AvroName("date_heure")
  private Long dateHeure;
  private String date;
  private String heure;
  @AvroName("consommation_brute_gaz_grtgaz")
  private Long consommationBruteGazGrtgaz;
  @AvroName("statut_grtgaz")
  private String statutGrtgaz;
  @AvroName("consommation_brute_gaz_terega")
  private Long consommationBruteGazTerega;
  @AvroName("statut_terega")
  private String statutTerega;
  @AvroName("consommation_brute_gaz_totale")
  private Long consommationBruteGazTotale;
  @AvroName("consommation_brute_electricite_rte")
  private Long consommationBruteElectriciteRte;
  @AvroName("statut_rte")
  private String statutRte;
  @AvroName("consommation_brute_totale")
  private Long consommationBruteTotale;
  @AvroName("flag_ignore")
  private String flagIgnore;

  public Long getDateHeure() {
    return dateHeure;
  }

  public String getDate() {
    return date;
  }

  public String getHeure() {
    return heure;
  }

  public Long getConsommationBruteGazGrtgaz() {
    return consommationBruteGazGrtgaz;
  }

  public String getStatutGrtgaz() {
    return statutGrtgaz;
  }

  public Long getConsommationBruteGazTerega() {
    return consommationBruteGazTerega;
  }

  public String getStatutTerega() {
    return statutTerega;
  }

  public Long getConsommationBruteGazTotale() {
    return consommationBruteGazTotale;
  }

  public Long getConsommationBruteElectriciteRte() {
    return consommationBruteElectriciteRte;
  }

  public String getStatutRte() {
    return statutRte;
  }

  public Long getConsommationBruteTotale() {
    return consommationBruteTotale;
  }

  public String getFlagIgnore() {
    return flagIgnore;
  }
}
