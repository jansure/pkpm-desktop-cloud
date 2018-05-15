package com.pkpmcloud.testdemo;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author xuhe
 * @date 2018/5/15
 */
public class Demo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);
        for(int i = 0;i<10;i++){
            final int seq = i;
            completionService.submit(() -> {
                Thread.sleep(new Random().nextInt(5000));
                return seq;
            });
        }
        for(int i = 0;i<10;i++){ //等待获取结果
            try {
                System.out.println(completionService.take().get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
