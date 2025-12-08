package ru.schoolservice.arm.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.schoolservice.arm.service.DbService;

import java.time.LocalDate;

@RestController
@Slf4j
@AllArgsConstructor
public class RestApiController {
    public final static String REQUEST = "/get";
    public final static String CHECK_PASSPORT = "/check-passport";
    public final static String CHECK_PASSPORT_DUPLICATE = "/check-passport-duplicate";

    private DbService dbService;

    @RequestMapping(value = CHECK_PASSPORT, method = RequestMethod.GET)
    public String checkPassport() {
        dbService.checkMukhinTheory();
        return "shit";
        //return reproduceCheckPassportError();
    }


    public String reproduceCheckPassportError() {
        log.info("=== ВОСПРОИЗВЕДЕНИЕ ОШИБКИ ИЗ БОЕВОГО ПРОЕКТА ===");
        log.info("Проблема: CheckPassportEprshbDelegate.updateProcessors вызывает двойное сохранение");

        String fio = "Иванов Иван Иванович";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String docSeries = "1234";
        String docNumber = "567890";
        String processId = "PROCESS-12345";

//        log.info("1. Запускаем процесс проверки паспорта...");
//        String result = dbService.checkPassportProcess(fio, birthDate, docSeries, docNumber, processId);
//
//        log.info("2. Результат первого запуска: {}", result);
//
//        log.info("3. Пытаемся запустить еще один процесс с теми же данными...");
//        String result2 = dbService.checkPassportProcess(fio, birthDate, docSeries, docNumber, "PROCESS-67890");
//
//        log.info("4. Результат второго запуска: {}", result2);

        return "Успех";
       // return String.format(" === РЕЗУЛЬТАТ ТЕСТА === Первый запуск: %s Второй запуск: %s ПРОБЛЕМА: При втором запуске метод findCachedCheckedPassport находит 2 записи вместо 1 и выбрасывает NonUniqueResultException ", result, result2);
    }

//    public String demonstrateDuplicateProblem() {
//        log.info("=== ДЕМОНСТРАЦИЯ ПРОБЛЕМЫ ДУБЛИРОВАНИЯ ===");
//
//        // Создаем тестовые данные
//        String fio = "Петров Петр Петрович";
//        LocalDate birthDate = LocalDate.of(1985, 5, 15);
//        String docSeries = "4321";
//        String docNumber = "987654";
//        String processId = "TEST-PROCESS";
//
//        log.info("Шаг 1: Имитируем вызов updateCache()...");
//
//        // Имитируем updateCache - первое сохранение
//        ru.schoolservice.arm.model.CheckPassportEntity entity = new ru.schoolservice.arm.model.CheckPassportEntity();
//        entity.setFio(fio);
//        entity.setBirthDate(birthDate);
//        entity.setDocSeries(docSeries);
//        entity.setDocNumber(docNumber);
//        entity.setCheckStatus("ACTUAL");
//        entity.setActualizationDate(java.time.LocalDateTime.now());
//        entity.setLoadDate(java.time.LocalDateTime.now());
//
//        ru.schoolservice.arm.model.CheckPassportEntity savedEntity = new ru.schoolservice.arm.model.CheckPassportEntity();
//        savedEntity.setFio(entity.getFio());
//        savedEntity.setBirthDate(entity.getBirthDate());
//        savedEntity.setDocSeries(entity.getDocSeries());
//        savedEntity.setDocNumber(entity.getDocNumber());
//        savedEntity.setCheckStatus(entity.getCheckStatus());
//        savedEntity.setActualizationDate(entity.getActualizationDate());
//        savedEntity.setLoadDate(entity.getLoadDate());
//
//        log.info("Сохранена запись с данными: {} {} {} {}",
//                savedEntity.getFio(), savedEntity.getBirthDate(),
//                savedEntity.getDocSeries(), savedEntity.getDocNumber());
//
//        log.info("Шаг 2: Имитируем вызов updateProcessors()...");
//        log.info("ВНИМАНИЕ: Здесь происходит проблема - двойное сохранение!");
//
//        ru.schoolservice.arm.model.CheckPassportProcessesEntity processEntity =
//                new ru.schoolservice.arm.model.CheckPassportProcessesEntity();
//        processEntity.setCheckPassport(savedEntity);
//        processEntity.setProcessId(processId);
//
//        ru.schoolservice.arm.model.CheckPassportEntity duplicate = new ru.schoolservice.arm.model.CheckPassportEntity();
//        duplicate.setFio(savedEntity.getFio());
//        duplicate.setBirthDate(savedEntity.getBirthDate());
//        duplicate.setDocSeries(savedEntity.getDocSeries());
//        duplicate.setDocNumber(savedEntity.getDocNumber());
//        duplicate.setCheckStatus(savedEntity.getCheckStatus());
//        duplicate.setActualizationDate(savedEntity.getActualizationDate());
//        duplicate.setLoadDate(savedEntity.getLoadDate());
//
//        log.info("Создана дублирующая запись с теми же данными!");
//
//        log.info("Шаг 3: Имитируем вызов findCachedCheckedPassport()...");
//
//        try {
//            // Пытаемся найти запись - получим ошибку
//            ru.schoolservice.arm.model.CheckPassportEntity found = null; // Метод вызовет исключение
//            return "Успешно найдена запись: " + (found != null ? found.getId() : "null");
//        } catch (Exception e) {
//            return " === ОШИБКА ВОСПРОИЗВЕДЕНА ===";
//        }
//    }
}
