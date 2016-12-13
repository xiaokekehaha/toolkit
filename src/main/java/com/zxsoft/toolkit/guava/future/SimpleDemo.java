package com.zxsoft.toolkit.guava.future;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuwenjuan on 16/12/13.
 */

public class SimpleDemo {
    public static void main(String[] args) {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        final ListenableFuture<Integer> listenableFuture = executorService.submit(new Callable<Integer>() {
            public Integer call() throws Exception {
                System.out.println("call execute..");
                TimeUnit.SECONDS.sleep(1);
                return 7;
            }
        });
        //		listenableFuture.addListener(new Runnable() {
        //
        //			public void run() {
        //				// TODO Auto-generated method stub
        //				System.out.println("123123123123");
        //			}
        //		}, executorService);
        Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {
            public void onSuccess(Integer result) {
                System.out.println("get listenable future's result with callback " + result);
            }

            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        try {
            System.out.println(listenableFuture.get());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        executorService.shutdown();

    }
}


