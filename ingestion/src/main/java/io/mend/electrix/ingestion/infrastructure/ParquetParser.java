package io.mend.electrix.ingestion.infrastructure;

import org.apache.avro.reflect.ReflectData;
import org.apache.hadoop.conf.Configuration;
import org.apache.parquet.avro.AvroReadSupport;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

@Component
public class ParquetParser {

  public <T> void parse(Resource resource, Class<T> clazz, Consumer<T> recordConsumer) throws IOException {
    var conf = new Configuration();
    var schema = ReflectData.get().getSchema(clazz);
    AvroReadSupport.setAvroReadSchema(conf, schema);

    try (var reader = IngestionParquetReader.read(resource.getFile().getAbsolutePath(), clazz)) {
      T record;
      while ((record = reader.read()) != null) {
        recordConsumer.accept(record);
      }
    }

  }

}
