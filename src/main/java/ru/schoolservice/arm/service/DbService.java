package ru.schoolservice.arm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.CheckPassportEntity;
import ru.schoolservice.arm.model.CheckPassportProcessesEntity;
import ru.schoolservice.arm.repository.CheckPassportProcessesRepository;
import ru.schoolservice.arm.repository.CheckPassportRepository;

import java.time.LocalDate;

@Service
@Slf4j
@AllArgsConstructor
public class DbService {

    private CheckPassportProcessesRepository checkPassportProcessesRepository;
    private CheckPassportRepository checkPassportRepository;


    // Имитация проблемного метода из боевого проекта
    public CheckPassportEntity findCachedCheckedPassport(String fio, LocalDate birthDate,
                                                         String docSeries, String docNumber) {
        // Этот метод вызовет NonUniqueResultException, если есть дублирующиеся записи
        return checkPassportRepository.findByFioAndBirthDateAndDocSeriesAndDocNumber(
                fio, birthDate, docSeries, docNumber
        );
    }

    // Имитация updateCache из CheckPassportEprshbDelegate
    @Transactional
    public CheckPassportEntity updateCache(CheckPassportEntity entity) {
        log.info("Сохранение CheckPassportEntity в кэш");
        CheckPassportEntity saved = new CheckPassportEntity();
        saved.setFio(entity.getFio());
        saved.setBirthDate(entity.getBirthDate());
        saved.setDocSeries(entity.getDocSeries());
        saved.setDocNumber(entity.getDocNumber());
        saved.setCheckStatus(entity.getCheckStatus());
        saved.setActualizationDate(entity.getActualizationDate());
        saved.setLoadDate(entity.getLoadDate());

        return checkPassportRepository.save(saved);
    }

    // Имитация updateProcessors из CheckPassportEprshbDelegate
    @Transactional
    public void updateProcessors(CheckPassportEntity checkPassportCache, String processId) {
        log.info("Создание CheckPassportProcessesEntity для процесса {}", processId);

        CheckPassportProcessesEntity processEntity = new CheckPassportProcessesEntity();
        processEntity.setCheckPassport(checkPassportCache);
        processEntity.setProcessId(processId);

        // Здесь проблема: мы снова сохраняем CheckPassportEntity через каскад или отдельно
        checkPassportProcessesRepository.save(processEntity);
    }

    public void checkMukhinTheory() {

        log.warn("===================== Стартуем ================================================");

        checkPassportRepository.findAll().forEach(e -> log.warn(e.toString()));
        checkPassportProcessesRepository.findAll().forEach(e-> log.warn(e.toString()));

        log.warn("===================== Сохраняем Ваню  ================================================");

        String fio = "Иванов Иван Иванович";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String docSeries = "1234";
        String docNumber = "567890";
        String processId = "PROCESS-12345";

        CheckPassportEntity entity = new CheckPassportEntity();
        entity.setFio(fio);
        entity.setBirthDate(birthDate);
        entity.setDocSeries(docSeries);
        entity.setDocNumber(docNumber);
        entity.setCheckStatus("ACTUAL");
        entity.setActualizationDate(java.time.LocalDateTime.now());
        entity.setLoadDate(java.time.LocalDateTime.now());

        checkPassportRepository.save(entity);
        log.warn("************************************************** сохранили Ваню ***********************************");

        checkPassportRepository.findAll().forEach(e -> log.warn(e.toString()));
        checkPassportProcessesRepository.findAll().forEach(e-> log.warn(e.toString()));

        log.warn("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  Проверяем каскадный апдейт ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        updateProcessors(entity, "123124123");

        log.warn("+++++++++++++++++++++++++++++++++++++++++++++++++ Обновились каскадно +++++++++++++++++++++++++++++++++++++++++++++++++++++");
        checkPassportRepository.findAll().forEach(e -> log.warn(e.toString()));
        checkPassportProcessesRepository.findAll().forEach(e-> log.warn(e.toString()));

    }

//
//    @Transactional
//    public String checkPassportProcess(String fio, LocalDate birthDate,
//                                       String docSeries, String docNumber,
//                                       String processId) {
//        log.info("Начало процесса проверки паспорта для процесса {}", processId);
//
//        try {
//            CheckPassportEntity cached = findCachedCheckedPassport(fio, birthDate, docSeries, docNumber);
//            if (cached != null) {
//                log.info("Найдена запись в кэше: {}", cached.getId());
//                return "Found in cache: " + cached.getId();
//            }
//        } catch (Exception e) {
//            log.error("Ошибка при проверке кэша: {}", e.getMessage());
//            return "Cache check error: " + e.getMessage();
//        }
//
//        CheckPassportEntity externalData = new CheckPassportEntity();
//        externalData.setFio(fio);
//        externalData.setBirthDate(birthDate);
//        externalData.setDocSeries(docSeries);
//        externalData.setDocNumber(docNumber);
//        externalData.setCheckStatus("ACTUAL");
//        externalData.setActualizationDate(java.time.LocalDateTime.now());
//        externalData.setLoadDate(java.time.LocalDateTime.now());
//
//        CheckPassportEntity savedToCache = updateCache(externalData);
//        log.info("Сохранено в кэш с ID: {}", savedToCache.getId());
//
//        updateProcessors(savedToCache, processId);
//
//        try {
//            CheckPassportEntity found = findCachedCheckedPassport(fio, birthDate, docSeries, docNumber);
//            return "Process completed, found: " + found.getId();
//        } catch (Exception e) {
//            return "Process completed with error when trying to find: " + e.getMessage();
//        }
//    }
}
