package com.zxsoft.toolkit.parquet;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.reflect.ReflectData;

public class AvroFileWriter {

	private Schema schema;

	public AvroFileWriter() {
		schema = ReflectData.get().getSchema(Student.class);
		System.out.println(schema.toString());
	}

	public void write() {
		Record avroRecord = new Record(schema);
		avroRecord.put("", "");
	}

	public static void main(String[] args) {
		AvroFileWriter writer = new AvroFileWriter();

	}

}
