package io.mend.electrix.ingestion;

import io.mend.electrix.ingestion.domain.ConsommationBruteNationale;
import io.mend.electrix.ingestion.domain.ConsommationBruteRegionale;
import io.mend.electrix.ingestion.domain.ConsommationEco2MixNationale;
import io.mend.electrix.ingestion.domain.ConsommationEco2MixRegionale;
import io.mend.electrix.ingestion.infrastructure.ParquetParser;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class ParquetParserTest {

  private final ParquetParser parquetParser = new ParquetParser();

  @Test
  void testParseBruteNationale() throws IOException {
    var resource = new ClassPathResource("data/consommation-quotidienne-brute_small.parquet");
    var records = new ArrayList<ConsommationBruteNationale>();
    parquetParser.parse(resource, ConsommationBruteNationale.class, records::add);
    assertThat(records).hasSize(20);
  }

  @Test
  void testParseBruteRegionale() throws IOException {
    var resource = new ClassPathResource("data/consommation-quotidienne-brute-regionale_small.parquet");
    var records = new ArrayList<ConsommationBruteRegionale>();
    parquetParser.parse(resource, ConsommationBruteRegionale.class, records::add);
    assertThat(records).hasSize(20);
  }

  @Test
  void testParseEco2MixNationale() throws IOException {
    var resource = new ClassPathResource("data/eco2mix-national-cons-def_small.parquet");
    var records = new ArrayList<ConsommationEco2MixNationale>();
    parquetParser.parse(resource, ConsommationEco2MixNationale.class, records::add);
    assertThat(records).hasSize(20);
  }

  @Test
  void testParseEco2MixRegionale() throws IOException {
    var resource = new ClassPathResource("data/eco2mix-regional-cons-def_small.parquet");
    var records = new ArrayList<ConsommationEco2MixRegionale>();
    parquetParser.parse(resource, ConsommationEco2MixRegionale.class, records::add);
    assertThat(records).hasSize(20);
  }
}
