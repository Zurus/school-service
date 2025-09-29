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

    @Test
    public void testClassLoadingUnderExtremeConditions() throws Exception {
        int totalThreads = 1000;
        int iterationsPerThread = 500;
        int totalExecutions = totalThreads * iterationsPerThread;

        System.out.println("=== НАЧАЛО ТЕСТА ПРИ ВЫСОКОЙ НАГРУЗКЕ ===");
        System.out.println("Потоков: " + totalThreads + ", Итераций на поток: " + iterationsPerThread);
        System.out.println("Всего запланировано запусков: " + totalExecutions);

        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(totalThreads);

        long startTime = System.currentTimeMillis();

        for (int threadId = 0; threadId < totalThreads; threadId++) {
            final int currentThreadId = threadId;
            executor.submit(() -> {
                try {
                    startLatch.await(); // Все стартуют одновременно

                    for (int iteration = 0; iteration < iterationsPerThread; iteration++) {
                        executeProcessWithClassLoaderStress(currentThreadId, iteration);

                        // Создаем "волны" нагрузки
                        if (iteration % 10 == 0) {
                            Thread.yield();
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Критическая ошибка в потоке " + currentThreadId + ": " + e.getMessage());
                } finally {
                    finishLatch.countDown();
                }
            });
        }

        // Запускаем все потоки одновременно
        System.out.println("Запускаем все потоки...");
        startLatch.countDown();

        // Ждем завершения с таймаутом
        boolean completed = finishLatch.await(2, TimeUnit.MINUTES);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Даем время на обработку оставшихся асинхронных jobs
        Thread.sleep(5000);

        executor.shutdown();

        // Выводим итоговые результаты
        printFinalResults(totalExecutions, duration, completed);
    }

    private void executeProcessWithClassLoaderStress(int threadId, int iteration) {
        try {
            // Создаем нагрузку на ClassLoader перед запуском процесса
            if (iteration % 5 == 0) {
                stressClassLoader();
            }

            // Запускаем процесс
            String businessKey = String.format("load-test-%d-%d", threadId, iteration);
            runtimeService.startProcessInstanceByKey("SampleProcess", businessKey);
            successCount.incrementAndGet();

        } catch (Exception e) {
            errorCount.incrementAndGet();
            handleProcessError(threadId, iteration, e);
        }
    }

    private void stressClassLoader() {
        // Интенсивная работа с ClassLoader для создания условий ошибки
        for (int i = 0; i < 20; i++) {
            try {
                // Пытаемся загрузить класс разными способами
                Class.forName("ru.schoolservice.arm.delegate.SampleDelegate", false,
                        Thread.currentThread().getContextClassLoader());

                // Дополнительная нагрузка через системный ClassLoader
                if (i % 5 == 0) {
                    ClassLoader.getSystemClassLoader()
                            .loadClass("ru.schoolservice.arm.delegate.SampleDelegate");
                }
            } catch (ClassNotFoundException e) {
                // Игнорируем - это нормально в условиях стресса
            } catch (Exception e) {
                if (e.getMessage() != null && e.getMessage().contains("Cannot load class")) {
                    System.err.println("=== ОБНАРУЖЕНА ПРОБЛЕМА С CLASS LOADER ===");
                }
            }
        }
    }

    private void handleProcessError(int threadId, int iteration, Exception e) {
        String errorMsg = String.format("Ошибка в потоке %d, итерация %d: %s [%s]",
                threadId, iteration, e.getMessage(), e.getClass().getSimpleName());

        System.err.println(errorMsg);

        // Проверяем на целевую ошибку
        if (e.getMessage() != null &&
                (e.getMessage().contains("Cannot load class") ||
                        e.getMessage().contains("ENGINE-09017") ||
                        e.getMessage().contains("ENGINE-09008") ||
                        e.getMessage().contains("SampleDelegate"))) {

            System.err.println("=== ВОСПРОИЗВЕДЕНА ЦЕЛЕВАЯ ОШИБКА ЗАГРУЗКИ КЛАССА ===");
            e.printStackTrace();
        }
    }

    private void printFinalResults(int totalExecutions, long duration, boolean completed) {
        int totalCompleted = successCount.get() + errorCount.get();
        double successRate = (successCount.get() * 100.0) / totalCompleted;
        double errorRate = (errorCount.get() * 100.0) / totalCompleted;
        double executionsPerSecond = totalCompleted / (duration / 1000.0);

        System.out.println("\n=== ФИНАЛЬНЫЕ РЕЗУЛЬТАТЫ ТЕСТА ===");
        System.out.println("Общее время выполнения: " + duration + " мс");
        System.out.println("Скорость: " + String.format("%.2f", executionsPerSecond) + " запусков/сек");
        System.out.println("Запланировано запусков: " + totalExecutions);
        System.out.println("Фактически выполнено: " + totalCompleted);
        System.out.println("Успешных запусков: " + successCount.get() + " (" + String.format("%.2f", successRate) + "%)");
        System.out.println("Ошибок: " + errorCount.get() + " (" + String.format("%.2f", errorRate) + "%)");

        if (!completed) {
            System.err.println("ВНИМАНИЕ: Тест не завершился в установленное время!");
        }

        if (errorCount.get() > 0) {
            System.err.println("=== ТЕСТ ВЫЯВИЛ ПРОБЛЕМЫ ПРИ ВЫСОКОЙ НАГРУЗКЕ ===");
            System.err.println("Было воспроизведено " + errorCount.get() + " ошибок, включая проблемы с загрузкой классов");
        } else {
            System.out.println("=== ТЕСТ ПРОЙДЕН БЕЗ ОШИБОК ===");
        }
    }
}
