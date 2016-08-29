package com.zxsoft.toolkit.parquet;

import java.io.IOException;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;

public class ParquetFileWriter {

	private ParquetWriter<GenericRecord> parquetWriter;

	public ParquetFileWriter(Schema avroSchema, Path path) {
		try {
			parquetWriter = AvroParquetWriter.<GenericRecord> builder(path).withSchema(avroSchema).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(List<GenericRecord> records) throws IOException {

		for (GenericRecord record : records) {
			parquetWriter.write(record);
		}

	}

	public void close() {
		try {
			parquetWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
