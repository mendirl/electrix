package io.mend.electrix.ingestion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ParquetParserTest {

  @Autowired
  private ParquetParser parquetParser;

  @Test
  void testParse() throws IOException {
    var resource = new ClassPathResource("data/consommation-quotidienne-brute.parquet");
    var records = new ArrayList<ConsommationBrute>();
    parquetParser.parse(resource, ConsommationBrute.class, records::add);
    assertThat(records).isNotEmpty();
  }

  @Test
  void testParseNational() throws IOException {
    var resource = new ClassPathResource("data/eco2mix-national-cons-def.parquet");
    var records = new ArrayList<ConsommationNationale>();
    parquetParser.parse(resource, ConsommationNationale.class, records::add);
    assertThat(records).isNotEmpty();
  }

  @Test
  void testParseRegional() throws IOException {
    var resource = new ClassPathResource("data/eco2mix-regional-cons-def.parquet");
    var records = new ArrayList<ConsommationRegionale>();
    parquetParser.parse(resource, ConsommationRegionale.class, records::add);
    assertThat(records).isNotEmpty();
  }
}
