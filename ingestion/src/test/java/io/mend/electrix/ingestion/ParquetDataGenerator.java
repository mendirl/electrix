package io.mend.electrix.ingestion;

import io.mend.electrix.ingestion.domain.ConsommationBrute;
import io.mend.electrix.ingestion.domain.ConsommationNationale;
import io.mend.electrix.ingestion.domain.ConsommationRegionale;
import io.mend.electrix.ingestion.infrastructure.IngestionParquetReader;
import org.apache.avro.reflect.ReflectData.AllowNull;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
public class ParquetDataGenerator {

  @Test
  void generateTestData() throws IOException {
    String targetDir = "src/test/resources/data/";

    Files.createDirectories(Paths.get(targetDir));

    var files = java.util.List.of(
      new ParquetFile(ResourceUtils.getFile("classpath:data/consommation-quotidienne-brute.parquet"),
        ConsommationBrute.class),
      new ParquetFile(ResourceUtils.getFile("classpath:data/eco2mix-national-cons-def.parquet"),
        ConsommationNationale.class),
      new ParquetFile(ResourceUtils.getFile("classpath:data/eco2mix-regional-cons-def.parquet"),
        ConsommationRegionale.class)
    );

    for (var file : files) {
      processParquet(targetDir, file);
    }
  }

  @SuppressWarnings("unchecked")
  private <T> void processParquet(String targetDir, ParquetFile file) throws IOException {
    shrinkParquet(file.name.getAbsolutePath(), targetDir + file.name.getName(), (Class<T>) file.clazz());
  }

  private record ParquetFile(File name, Class<?> clazz) {
  }

  private <T> void shrinkParquet(String sourcePath, String targetPath, Class<T> clazz) throws IOException {
    System.out.println("Shrinking " + sourcePath + " to " + targetPath);

    File targetFile = new File(targetPath);
    if (targetFile.exists()) {
      targetFile.delete();
    }

    var reflectData = new AllowNull();
    var schema = reflectData.getSchema(clazz);
    Configuration conf = new Configuration();

    try (var reader = IngestionParquetReader.read(sourcePath, clazz);
         ParquetWriter<T> writer = AvroParquetWriter.<T>builder(new Path(targetPath))
           .withSchema(schema)
           .withDataModel(reflectData)
           .withConf(conf)
           .withCompressionCodec(CompressionCodecName.SNAPPY)
           .withValidation(false)
           .build()) {

      T record;
      int count = 0;
      while ((record = reader.read()) != null && count < 100) {
        try {
          System.out.println("[DEBUG_LOG] Writing record " + count + ": " + record);
          writer.write(record);
          count++;
        } catch (Exception e) {
          System.err.println("[DEBUG_LOG] Error writing record " + count + ": " + e.getMessage());
          e.printStackTrace();
          throw e;
        }
      }
      System.out.println("Written " + count + " records.");
    }
  }
}
