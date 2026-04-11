package io.mend.electrix.ingestion;

import org.apache.avro.reflect.ReflectData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroReadSupport;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;

import java.io.IOException;

public class IngestionParquetReader {

  public static <T> ParquetReader<T> read(String path, Class<T> clazz) throws IOException {
    var conf = new Configuration();
    var schema = ReflectData.get().getSchema(clazz);
    AvroReadSupport.setAvroReadSchema(conf, schema);

    var hadoopPath = new Path(path);
    var hadoopInputPath = HadoopInputFile.fromPath(hadoopPath, conf);

    return AvroParquetReader.<T>builder(hadoopInputPath)
      .withDataModel(ReflectData.get())
      .withConf(conf)
      .build();
  }

}
