package com.zxsoft.toolkit.guava.future;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class ListenableFutureDemo {

	private static Logger logger = LoggerFactory.getLogger(ListenableFutureDemo.class);
	private DataService dataService;

	//异步操作链
	public ListenableFuture<QueryResult> getQueryResult(Callable<RowKey> task, ListeningExecutorService service,
			Executor executor) {
		ListenableFuture<RowKey> rowKeyFuture = service.submit(task);
		AsyncFunction<RowKey, QueryResult> queryFunction = new AsyncFunction<RowKey, QueryResult>() {
			@Override
			public ListenableFuture<QueryResult> apply(RowKey rowKey) {
				return dataService.read(rowKey);
			}
		};
		ListenableFuture<QueryResult> queryFuture = Futures.transform(rowKeyFuture, queryFunction, executor);
		return queryFuture;
	}

	//将 ExecutorService 转换为 ListeningExecutorService
	public ListeningExecutorService getListeningExecutorServiceFromExecutorService(ExecutorService executorService) {
		ListeningExecutorService service = MoreExecutors.listeningDecorator(executorService);
		return service;
	}

	public void readFile(String file, ListeningExecutorService service, Executor executor) {
		ListenableFuture<String> future = service.submit(() -> new String(Files.readAllBytes(Paths.get(file))));
		//The basic operation added by ListenableFuture is addListener(Runnable, Executor),
		//which specifies that when the computation represented by this Future is done,
		//the specified Runnable will be run on the specified Executor.
		future.addListener(new Runnable() {
			@Override
			public void run() {
				logger.info("Done with {0}", file);
			}
		}, executor);
	}

	public void readFileV2(String file, ListeningExecutorService service) {
		ListenableFuture<String> future = service.submit(() -> new String(Files.readAllBytes(Paths.get(file))));
		//Most users will prefer to use Futures.addCallback(ListenableFuture<V>, FutureCallback<V>, Executor),
		//or the version which defaults to using MoreExecutors.sameThreadExecutor(),
		//for use when the callback is fast and lightweight
		Futures.addCallback(future, new FutureCallback<String>() {
			@Override
			public void onSuccess(String content) {
				logger.info(content);
			}

			@Override
			public void onFailure(Throwable thrown) {
				logger.error(thrown.getMessage());
			}
		});
	}

}
