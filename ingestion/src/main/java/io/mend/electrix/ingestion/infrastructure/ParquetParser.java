package io.mend.electrix.ingestion.infrastructure;

import org.apache.avro.reflect.ReflectData;
import org.apache.hadoop.conf.Configuration;
import org.apache.parquet.avro.AvroReadSupport;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.function.Consumer;

@Component
public class ParquetParser {

  public <T> void parse(Resource resource, Class<T> clazz, Consumer<T> recordConsumer) throws IOException {
    var conf = new Configuration();
    var schema = ReflectData.get().getSchema(clazz);
    AvroReadSupport.setAvroReadSchema(conf, schema);

    File tempFile = null;
    String path;
    try {
      path = resource.getFile().getAbsolutePath();
    } catch (IOException e) {
      tempFile = File.createTempFile("ingestion-", ".parquet");
      try (var inputStream = resource.getInputStream()) {
        Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
      }
      path = tempFile.getAbsolutePath();
    }

    try (var reader = IngestionParquetReader.read(path, clazz)) {
      T record;
      while ((record = reader.read()) != null) {
        recordConsumer.accept(record);
      }
    } finally {
      if (tempFile != null && tempFile.exists()) {
        tempFile.delete();
      }
    }

  }

}
