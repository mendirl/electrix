package io.mend.electrix.ingestion.infrastructure;

import org.apache.avro.reflect.ReflectData;
import org.apache.hadoop.conf.Configuration;
import org.apache.parquet.avro.AvroReadSupport;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

@Component
public class ParquetParser {

  public <T> void parse(File file, Class<T> clazz, Consumer<T> recordConsumer) throws IOException {
    var conf = new Configuration();
    var schema = ReflectData.get().getSchema(clazz);
    AvroReadSupport.setAvroReadSchema(conf, schema);

    String path = file.getAbsolutePath();

    try (var reader = IngestionParquetReader.read(path, clazz)) {
      T record;
      while ((record = reader.read()) != null) {
        recordConsumer.accept(record);
      }
    }

  }

}
