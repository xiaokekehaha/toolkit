package com.zxsoft.tookit.guava.future;

import com.google.common.util.concurrent.ListenableFuture;

public interface DataService {

	public ListenableFuture<QueryResult> read(RowKey rowkey);
}
