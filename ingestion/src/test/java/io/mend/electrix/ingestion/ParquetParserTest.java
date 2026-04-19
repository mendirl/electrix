package io.mend.electrix.ingestion;

import io.mend.electrix.ingestion.domain.ConsommationBrute;
import io.mend.electrix.ingestion.domain.ConsommationNationale;
import io.mend.electrix.ingestion.domain.ConsommationRegionale;
import io.mend.electrix.ingestion.infrastructure.ParquetParser;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class ParquetParserTest {

  private final ParquetParser parquetParser = new ParquetParser();

  @Test
  void testParse() throws IOException {
    var resource = new ClassPathResource("data/consommation-quotidienne-brute_small.parquet");
    var records = new ArrayList<ConsommationBrute>();
    parquetParser.parse(resource, ConsommationBrute.class, records::add);
    assertThat(records).hasSize(20);
  }

  @Test
  void testParseNational() throws IOException {
    var resource = new ClassPathResource("data/eco2mix-national-cons-def_small.parquet");
    var records = new ArrayList<ConsommationNationale>();
    parquetParser.parse(resource, ConsommationNationale.class, records::add);
    assertThat(records).hasSize(20);
  }

  @Test
  void testParseRegional() throws IOException {
    var resource = new ClassPathResource("data/eco2mix-regional-cons-def_small.parquet");
    var records = new ArrayList<ConsommationRegionale>();
    parquetParser.parse(resource, ConsommationRegionale.class, records::add);
    assertThat(records).hasSize(20);
  }
}
