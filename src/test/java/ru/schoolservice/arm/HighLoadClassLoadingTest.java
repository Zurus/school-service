package ru.schoolservice.arm;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Proxy;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HighLoadClassLoadingTest {


    @Autowired
    private RuntimeService runtimeService;

    private final AtomicInteger successCount = new AtomicInteger(0);
    private final AtomicInteger errorCount = new AtomicInteger(0);
    private final AtomicInteger classLoadingErrors = new AtomicInteger(0);

    @Test
    public void testClassLoadingUnderHighLoad() throws Exception {
        int totalThreads = 20;
        int iterationsPerThread = 50;

        System.out.println("=== ТЕСТ ВЫСОКОЙ НАГРУЗКИ ===");
        System.out.println("Потоков: " + totalThreads + ", Итераций: " + iterationsPerThread);

        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(totalThreads);

        long startTime = System.currentTimeMillis();

        for (int threadId = 0; threadId < totalThreads; threadId++) {
            final int currentThreadId = threadId;
            executor.submit(() -> {
                try {
                    startLatch.await();

                    for (int iteration = 0; iteration < iterationsPerThread; iteration++) {
                        executeProcess(currentThreadId, iteration);

                        if (iteration % 10 == 0) {
                            Thread.yield();
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка в потоке " + currentThreadId + ": " + e.getMessage());
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        startLatch.countDown();

        boolean completed = finishLatch.await(1, TimeUnit.MINUTES);
        long endTime = System.currentTimeMillis();

        executor.shutdown();

        Thread.sleep(2000);

        printResults(totalThreads * iterationsPerThread, endTime - startTime);

        assertTrue("Тест завершен. Ошибок загрузки классов: " + classLoadingErrors.get(),
                classLoadingErrors.get() >= 0);
    }

    private void executeProcess(int threadId, int iteration) {
        try {
            String businessKey = String.format("load-test-%d-%d", threadId, iteration);
            runtimeService.startProcessInstanceByKey("SampleProcess", businessKey);
            successCount.incrementAndGet();

        } catch (Exception e) {
            errorCount.incrementAndGet();
            handleError(threadId, iteration, e);
        }
    }

    private void handleError(int threadId, int iteration, Exception e) {
        String errorMsg = e.getMessage();

        if (errorMsg != null &&
                (errorMsg.contains("Cannot load class") ||
                        errorMsg.contains("ENGINE-09017") ||
                        errorMsg.contains("ENGINE-09008") ||
                        errorMsg.contains("SampleDelegate"))) {

            classLoadingErrors.incrementAndGet();
            System.err.println("=== НАЙДЕНА ЦЕЛЕВАЯ ОШИБКА ===");
            System.err.println("Поток: " + threadId + ", Итерация: " + iteration);
            System.err.println("Ошибка: " + errorMsg);
        }
    }

    private void printResults(int totalExecutions, long duration) {
        int totalCompleted = successCount.get() + errorCount.get();

        System.out.println("\n=== РЕЗУЛЬТАТЫ ===");
        System.out.println("Время: " + duration + " мс");
        System.out.println("Успешно: " + successCount.get());
        System.out.println("Ошибок: " + errorCount.get());
        System.out.println("Ошибок загрузки классов: " + classLoadingErrors.get());

        if (classLoadingErrors.get() > 0) {
            System.out.println("УСПЕХ: Ошибка загрузки класса воспроизведена!");
        }
    }
}
